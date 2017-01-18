package com.github.itsubaki.sunflower.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;

public class Util {

	public static void close(Closeable obj) {
		try {
			obj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static URI urlFormSocket(URI uri, ServerSocket socket) throws Exception {
		URI proxy = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), socket.getLocalPort(), uri.getPath(),
				uri.getQuery(), uri.getFragment());
		return proxy;
	}
}
