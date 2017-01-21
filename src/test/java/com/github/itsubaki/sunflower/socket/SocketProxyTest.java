package com.github.itsubaki.sunflower.socket;

import org.junit.Test;

import com.github.itsubaki.sunflower.socket.SocketProxy;
import com.github.itsubaki.sunflower.util.UtilTest;

public class SocketProxyTest {

	@Test
	public void test() {
		UtilTest.log();
		SocketProxy p = new SocketProxy(8080);
		try {
			p.open();

			Thread.sleep(100000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			p.close();
		}
	}

}
