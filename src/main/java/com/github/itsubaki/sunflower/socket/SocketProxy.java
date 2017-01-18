package com.github.itsubaki.sunflower.socket;

import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketProxy {
	private static final transient Logger LOG = LoggerFactory.getLogger(SocketProxy.class);
	private int port;
	private Acceptor acceptor;
	private ServerSocket socket;

	public SocketProxy(int port) {
		this.port = port;
	}

	public void open() throws Exception {
		socket = new ServerSocket(port);
		acceptor = new Acceptor(socket);
		new Thread(null, acceptor, "SocketProxy:" + socket.getLocalPort()).start();
		LOG.info("opend. SocketProxy:" + socket.getLocalPort());
	}

	public void close() {
		acceptor.close();
		LOG.info("closed.");
	}

}
