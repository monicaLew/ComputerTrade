package com.pctrade.price.utils;

import java.util.HashSet;
import java.util.Set;

public class ThreadSetUtils {

	private static ThreadSetUtils instance;
	private Set<Long> ids = new HashSet<Long>();

	private ThreadSetUtils() {
		super();
	}

	public static ThreadSetUtils getInst() {
		if (instance == null) {
			instance = new ThreadSetUtils();
		}
		return instance;
	}

	public void addId(Long id) {
		ids.add(id);
	}

	public void remove(Long id) {
		ids.remove(id);
	}

	public boolean isEmpty() {
		return ids.isEmpty();
	}

	public Integer size() {
		return ids.size();
	}
}
