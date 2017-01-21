
package com.github.itsubaki.sunflower.dispatch;

import java.net.URI;
import java.util.concurrent.Executors;

import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.hawtdispatch.transport.TcpTransportServer;

public class Server {
	private TcpTransportServer server;

	public void open(URI location) throws Exception {
		server = new TcpTransportServer(location);
		server.setBlockingExecutor(Executors.newCachedThreadPool());
		server.setDispatchQueue(Dispatch.getGlobalQueue());
		server.setTransportServerListener(new TransportServerListenerImpl());
		server.start(new TaskImpl("start"));
	}

	public void close() {
		if (server != null) {
			try {
				server.stop(new TaskImpl("stop"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
