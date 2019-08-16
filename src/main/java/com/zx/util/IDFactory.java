package com.zx.util;

import java.util.UUID;

public class IDFactory {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
