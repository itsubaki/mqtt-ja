package com.github.itsubaki.sunflower.sub;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Subscriber {
	private ArrayBlockingQueue<String> queue;
	private boolean fair = true;

	public Subscriber(Socket socket, int capacity) {
		queue = new ArrayBlockingQueue<>(capacity, fair);
	}

	public void push(String message) {
		queue.offer(message);
	}

}
