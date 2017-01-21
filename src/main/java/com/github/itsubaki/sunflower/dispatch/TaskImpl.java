package com.github.itsubaki.sunflower.dispatch;

import org.fusesource.hawtdispatch.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskImpl extends Task {
	private static final transient Logger LOG = LoggerFactory.getLogger(TaskImpl.class);
	private String name;

	public TaskImpl(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		LOG.debug("name: " + name);
	}

}
