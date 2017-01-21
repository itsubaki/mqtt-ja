package com.github.itsubaki.sunflower.dispatch;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executors;

import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.hawtdispatch.DispatchQueue;
import org.fusesource.hawtdispatch.Task;
import org.fusesource.hawtdispatch.transport.TcpTransportServer;
import org.fusesource.hawtdispatch.transport.Transport;
import org.fusesource.hawtdispatch.transport.TransportListener;
import org.fusesource.hawtdispatch.transport.TransportServerListener;
import org.fusesource.mqtt.codec.MQTTProtocolCodec;
import org.junit.Test;

import com.github.itsubaki.sunflower.util.UtilTest;

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
		UtilTest.log();

		try {
			URI location = new URI("tcp://localhost:8080");
			TcpTransportServer server = new TcpTransportServer(location);
			server.setBlockingExecutor(Executors.newCachedThreadPool());
			server.setDispatchQueue(Dispatch.getGlobalQueue());
			server.setTransportServerListener(new TransportServerListener() {

				@Override
				public void onAccept(Transport transport) throws Exception {
					transport.setProtocolCodec(new MQTTProtocolCodec());
					transport.setDispatchQueue(Dispatch.createQueue());
					transport.setTransportListener(new TransportListener() {

						@Override
						public void onTransportFailure(IOException error) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTransportDisconnected() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTransportConnected() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onTransportCommand(Object command) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onRefill() {
							// TODO Auto-generated method stub

						}
					});
					transport.start(new Task() {

						@Override
						public void run() {
							System.out.println(this.getClass());
						}

					});
				}

				@Override
				public void onAcceptError(Exception error) {
					System.out.println(error.getMessage());
				}

			});
			server.start(new Task() {
				@Override
				public void run() {
					System.out.println("start");
				}
			});
			Thread.sleep(10000);
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
