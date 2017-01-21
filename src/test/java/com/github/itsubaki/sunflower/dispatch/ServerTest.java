package com.github.itsubaki.sunflower.dispatch;

import java.net.URI;

import org.junit.Test;

import com.github.itsubaki.sunflower.dispatch.Server;
import com.github.itsubaki.sunflower.util.UtilTest;

public class ServerTest {

	@Test
	public void test() {
		UtilTest.log();

		Server serv = null;
		try {
			URI location = new URI("tcp://localhost:8080");
			serv = new Server();
			serv.open(location);

			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			serv.close();
		}

	}
}
