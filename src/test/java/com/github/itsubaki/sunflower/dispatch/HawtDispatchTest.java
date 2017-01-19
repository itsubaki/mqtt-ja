package com.github.itsubaki.sunflower.dispatch;

import java.net.URI;
import java.util.concurrent.Executors;

import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.hawtdispatch.DispatchQueue;
import org.fusesource.hawtdispatch.Task;
import org.fusesource.hawtdispatch.transport.TcpTransportServer;
import org.junit.Test;

public class HawtDispatchTest {

	@Test
	public void test() {
		DispatchQueue queue = Dispatch.getGlobalQueue();
		queue.execute(new Task() {
			@Override
			public void run() {
				System.out.println("run");
			}
		});
	}

	@Test
	public void server() {
		try {
			URI location = new URI("tcp://localhost:8080");
			TcpTransportServer server = new TcpTransportServer(location);
			server.setBlockingExecutor(Executors.newCachedThreadPool());
			server.setDispatchQueue(Dispatch.getGlobalQueue());
			server.start(new Task() {
				@Override
				public void run() {
					System.out.println("start");
				}
			});
			Thread.sleep(1000);
			server.stop(new Task() {
				@Override
				public void run() {
					System.out.println("stop");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
