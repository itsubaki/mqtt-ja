package com.github.itsubaki.sunflower.sub;

import java.util.concurrent.ArrayBlockingQueue;

import com.github.itsubaki.sunflower.message.Message;

public class Subscriber {
	private ArrayBlockingQueue<Message> queue;
	private boolean fair = true;

	public Subscriber(int capacity) {
		queue = new ArrayBlockingQueue<>(capacity, fair);
	}

	public void push(Message message) {
		queue.offer(message);
	}

}
