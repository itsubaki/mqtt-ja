package com.github.itsubaki.sunflower.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SocketServer {
	private AtomicBoolean closed = new AtomicBoolean(false);

	public void start(int port, int backlog) {
		ServerSocket serv = null;
		try {
			serv = new ServerSocket(port, backlog);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ExecutorService exec = Executors.newCachedThreadPool();
		while (!isClosed()) {
			try {
				Socket socket = serv.accept();
				Connection con = new Connection(socket);
				exec.submit(con);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			if (serv != null) {
				serv.close();
			}
			exec.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isClosed() {
		return closed.get();
	}

	public void close() {
		closed.set(true);
	}

}
