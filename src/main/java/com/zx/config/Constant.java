package com.zx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 通用常量配置
 * @author lishenbo
 *
 */
@ConfigurationProperties(prefix = "packet.constant")
@Configuration
public class Constant {
	private String prizeList;
	
	private String packetTopic;
	
	private String prizeSetInfo;
	
	private String prizeTotal;
	
	private String userSort;
	
	
	

	public String getUserSort() {
		return userSort;
	}

	public void setUserSort(String userSort) {
		this.userSort = userSort;
	}

	public String getPrizeTotal() {
		return prizeTotal;
	}

	public void setPrizeTotal(String prizeTotal) {
		this.prizeTotal = prizeTotal;
	}

	public String getPrizeSetInfo() {
		return prizeSetInfo;
	}

	public void setPrizeSetInfo(String prizeSetInfo) {
		this.prizeSetInfo = prizeSetInfo;
	}

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
