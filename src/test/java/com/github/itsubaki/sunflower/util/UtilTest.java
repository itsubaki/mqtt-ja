package com.github.itsubaki.sunflower.util;

import org.junit.Ignore;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class UtilTest {

	@Ignore
	public static void log() {
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
