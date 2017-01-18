package com.github.itsubaki.sunflower.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.itsubaki.sunflower.util.Util;

public class Acceptor implements Runnable {
	private static final transient Logger LOG = LoggerFactory.getLogger(Acceptor.class);

	private ServerSocket socket;
	private URI target;
	private AtomicReference<CountDownLatch> pause = new AtomicReference<>();
	private List<Connection> connections = new LinkedList<>();

	public Acceptor(ServerSocket socket, URI target) {
		this.socket = socket;
		this.target = target;
		this.pause.set(new CountDownLatch(0));

		try {
			socket.setSoTimeout(1000);
		} catch (SocketException e) {
			e.printStackTrace();
		}

	}

	public List<Connection> getConnection() {
		return connections;
	}

	public void pause() {
		pause.set(new CountDownLatch(1));
	}

	public void resume() {
		pause.get().countDown();
	}

	public void close() {
		Util.close(socket);
		resume();
	}

	public void accept() throws Exception {
		try {
			Socket sock = socket.accept();
			LOG.info("accepted. socket: " + sock);
			synchronized (connections) {
				Connection con = new Connection(sock, target);
				con.setConnections(connections);
				con.open();
				connections.add(con);
			}
		} catch (SocketTimeoutException e) {
			// noop
		}
	}

	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {
				pause.get().await();
				accept();
			}
		} catch (Exception e) {
			LOG.debug("finihesd. reason: " + e.getMessage());
		}
	}

}
