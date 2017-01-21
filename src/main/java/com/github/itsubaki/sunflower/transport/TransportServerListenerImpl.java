package com.github.itsubaki.sunflower.transport;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.fusesource.hawtdispatch.transport.Transport;
import org.fusesource.hawtdispatch.transport.TransportServerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportServerListenerImpl implements TransportServerListener {
	private static final transient Logger LOG = LoggerFactory.getLogger(TransportServerListenerImpl.class);

	@Override
	public void onAccept(Transport transport) throws Exception {
		transport.setTransportListener(new TransportListenerImpl());
		transport.start(new TaskImpl("transport.start"));

		ByteBuffer src = ByteBuffer.wrap("Hello".getBytes(Charset.forName("UTF-8")));
		transport.getWriteChannel().write(src);

		transport.stop(new TaskImpl("transport.stop"));
	}

	@Override
	public void onAcceptError(Exception error) {
		LOG.warn(error.getMessage(), error);
	}

}
