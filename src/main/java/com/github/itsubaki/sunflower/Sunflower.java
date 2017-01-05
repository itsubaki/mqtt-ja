package com.github.itsubaki.sunflower;

import com.github.itsubaki.sunflower.broker.Broker;

public class Sunflower {
	private Broker broker = new Broker();

	public void start() throws Exception {
		broker.start();
	}

	public void shutdown() {
		broker.shutdown();
	}
}
