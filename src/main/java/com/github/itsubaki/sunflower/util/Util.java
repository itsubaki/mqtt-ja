package com.github.itsubaki.sunflower.util;

import java.io.Closeable;
import java.io.IOException;

public class Util {

	public static void close(Closeable obj) {
		try {
			obj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
