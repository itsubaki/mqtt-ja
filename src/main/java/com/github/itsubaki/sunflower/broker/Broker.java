package com.github.itsubaki.sunflower.broker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Broker {
	private ServerSocket serv;
	private AtomicBoolean closed = new AtomicBoolean(false);
	private ExecutorService exec = Executors.newCachedThreadPool();

	public void start() throws Exception {
		serv = new ServerSocket(1883, 1000);
		while (!isClosed()) {
			Socket socket = serv.accept();
			exec.submit(new Connection(socket));
		}
	}

	public boolean isClosed() {
		return closed.get();
	}

	public void shutdown() {
		closed.set(true);
	}
}
