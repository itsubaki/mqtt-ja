package com.github.itsubaki.sunflower.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.itsubaki.sunflower.util.Util;

public class Acceptor implements Runnable {
	private static final transient Logger LOG = LoggerFactory.getLogger(Acceptor.class);

	private ServerSocket socket;
	private AtomicReference<CountDownLatch> pause = new AtomicReference<>();

	public Acceptor(ServerSocket socket) {
		this.socket = socket;
		this.pause.set(new CountDownLatch(0));

		try {
			socket.setSoTimeout(1000);
		} catch (SocketException e) {
			e.printStackTrace();
		}

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

	public void accept() throws IOException {
		try {
			Socket sock = socket.accept();
			LOG.info("accepted. socket: " + sock);
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
