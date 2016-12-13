package com.github.itsubaki.sunflower;

public class Bootstrap {

	public static void main(String[] arg) {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.start(arg);
	}

	public void start(String[] arg) {
		Sunflower sunflower = new Sunflower();
		ShutdownHook hook = new ShutdownHook(sunflower);
		Runtime.getRuntime().addShutdownHook(new Thread(hook));

		try {
			sunflower.start();
		} catch (Exception e) {
			sunflower.shutdown();
			e.printStackTrace();
		}
	}

}
