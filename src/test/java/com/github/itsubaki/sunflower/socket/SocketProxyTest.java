package com.github.itsubaki.sunflower.socket;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class SocketProxyTest {

	@Test
	public void test() {
		setup();
		SocketProxy p = new SocketProxy(8080);
		try {
			p.open();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.close();
		}
	}

	private void setup() {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		context.reset();
		JoranConfigurator configurator = new JoranConfigurator();
		configurator.setContext(context);

		try {
			configurator.doConfigure("./env/logback.xml");
		} catch (JoranException e) {
			e.printStackTrace();
		}
	}

}
