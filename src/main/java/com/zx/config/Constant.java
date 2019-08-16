package com.zx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "packet.constant")
@Configuration
public class Constant {
	private String prizeList;

	public String getPrizeList() {
		return prizeList;
	}

	public void setPrizeList(String prizeList) {
		this.prizeList = prizeList;
	}
	
}
