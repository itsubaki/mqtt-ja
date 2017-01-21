package com.github.itsubaki.sunflower.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transfer extends Thread {
	private static final transient Logger LOG = LoggerFactory.getLogger(Transfer.class);

	private AtomicReference<CountDownLatch> pause = new AtomicReference<>();
	private Socket src;
	private Socket dest;
	private Connection con;

	public Transfer(Socket src, Socket dest, Connection con) {
		super.setName("Transfer[" + hashCode() + "]: " + src.getPort() + "-" + dest.getPort());

		this.src = src;
		this.dest = dest;
		this.con = con;
		pause.set(new CountDownLatch(0));
	}

	public void pause() {
		pause.set(new CountDownLatch(1));
	}

	public void resume2() {
		pause.get().countDown();
	}

	@Override
	public void run() {
		byte[] buf = new byte[128];
		try {
			InputStream in = src.getInputStream();
			OutputStream out = dest.getOutputStream();
			while (true) {
				int len = in.read(buf);
				if (len == -1) {
					break;
				}

				if (LOG.isTraceEnabled()) {
					LOG.trace("recv: " + Arrays.toString(buf));
				}

				pause.get().await();
				out.write(buf, 0, len);

				if (LOG.isTraceEnabled()) {
					LOG.trace("writ: " + Arrays.toString(buf));
				}
			}
		} catch (Exception e) {
			LOG.debug("read/write failed, reason: " + e.getMessage());
			con.close();
		}
	}

}
