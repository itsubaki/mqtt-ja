package com.github.itsubaki.sunflower;

public class ShutdownHook implements Runnable {
	private Sunflower sunflower;

	public ShutdownHook(Sunflower sunflower) {
		this.sunflower = sunflower;
	}

	@Override
	public void run() {
		this.sunflower.shutdown();
	}

}
