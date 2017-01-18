package com.github.itsubaki.sunflower.socket;

import java.net.ServerSocket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.itsubaki.sunflower.util.Util;

public class SocketProxy {
	private static final transient Logger LOG = LoggerFactory.getLogger(SocketProxy.class);
	private int port;
	private URI target;
	private URI proxyURI;
	private Acceptor acceptor;
	private ServerSocket socket;

	public SocketProxy(int port, URI target) {
		this.port = port;
		this.target = target;
	}

	public void open() throws Exception {
		if (proxyURI == null) {
			socket = new ServerSocket(port);
			proxyURI = Util.urlFormSocket(target, socket);
		} else {
			socket = new ServerSocket(proxyURI.getPort());
		}

		acceptor = new Acceptor(socket, target);
		new Thread(null, acceptor, "SocketProxy: " + socket.getLocalPort()).start();

		LOG.info("opend. SocketProxy:" + socket.getLocalPort());
	}

	public void close() {
		List<Connection> connections;
		synchronized (acceptor.getConnection()) {
			connections = new ArrayList<>(acceptor.getConnection());
		}
		connections.forEach(c -> c.close());
		acceptor.close();

		LOG.info("closed. connection.size: " + connections.size());
	}

	public void pause() {
		List<Connection> connections = acceptor.getConnection();
		synchronized (connections) {
			acceptor.pause();
			connections.forEach(c -> c.pause());
		}

		LOG.info("paused. connections.size: " + connections.size());
	}

	public void resume() {
		List<Connection> connections = acceptor.getConnection();
		synchronized (connections) {
			connections.forEach(c -> c.resume());
		}
		acceptor.resume();

		LOG.info("resumed. connections.size: " + connections.size());
	}

}
