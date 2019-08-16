package com.zx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "packet.constant")
@Configuration
public class Constant {
	private String prizeList;
	
	private String packetTopic;
	

	public String getPacketTopic() {
		return packetTopic;
	}

	public void setPacketTopic(String packetTopic) {
		this.packetTopic = packetTopic;
	}

	public String getPrizeList() {
		return prizeList;
	}

	public void setPrizeList(String prizeList) {
		this.prizeList = prizeList;
	}
	
}
