package com.github.itsubaki.sunflower.socket;

import java.net.Socket;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection {
	private static final transient Logger LOG = LoggerFactory.getLogger(Connection.class);
	private List<Connection> connections = new LinkedList<>();
	private Socket src;
	private Socket dest;
	private Transfer req;
	private Transfer res;

	public Connection(Socket socket, URI target) throws Exception {
		this.src = socket;
		this.dest = new Socket(target.getHost(), target.getPort());

		req = new Transfer(src, dest, this);
		res = new Transfer(dest, src, this);
	}

	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}

	public void pause() {
		req.pause();
		res.pause();
	}

	public void resume() {
		req.resume2();
		req.resume2();
	}

	public void open() {
		req.start();
		res.start();
	}

	public void close() {
		synchronized (connections) {
			connections.remove(this);
		}

		try {
			src.close();
			dest.close();
		} catch (Exception e) {
			LOG.debug("unexpected error. connection: " + this, e);
		}
	}

}
