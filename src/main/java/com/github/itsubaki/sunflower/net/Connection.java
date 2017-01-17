package com.github.itsubaki.sunflower.net;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection implements Runnable {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private Socket socket;

	public Connection(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		log.info("connected: " + socket);
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
