package com.github.itsubaki.sunflower.dispatch;

import java.io.IOException;

import org.fusesource.hawtdispatch.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportListenerImpl implements TransportListener {
	private static final transient Logger LOG = LoggerFactory.getLogger(TransportListenerImpl.class);

	@Override
	public void onTransportCommand(Object command) {
		LOG.debug(command.getClass() + ": " + command);
	}

	@Override
	public void onRefill() {
		LOG.debug("refilled");
	}

	@Override
	public void onTransportFailure(IOException error) {
		LOG.warn(error.getMessage(), error);
	}

	@Override
	public void onTransportConnected() {
		LOG.debug("connected.");
	}

	@Override
	public void onTransportDisconnected() {
		LOG.debug("disconnected.");
	}

}
