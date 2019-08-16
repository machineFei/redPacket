package com.zx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rocketmq")
@Configuration
public class RocketMQConfig {
	private String producerGroup;
	
	private String conumerGroup;

	private String namesrvAddr;
	
	private String packetTopic;
	
	private String packetTag;
	
	
	
	public String getPacketTopic() {
		return packetTopic;
	}

	public void setPacketTopic(String packetTopic) {
		this.packetTopic = packetTopic;
	}

	public String getPacketTag() {
		return packetTag;
	}

	public void setPacketTag(String packetTag) {
		this.packetTag = packetTag;
	}

	public String getProducerGroup() {
		return producerGroup;
	}

	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

	public String getConumerGroup() {
		return conumerGroup;
	}

	public void setConumerGroup(String conumerGroup) {
		this.conumerGroup = conumerGroup;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	@Override
	public String toString() {
		return "RocketMQConfig [producerGroup=" + producerGroup + ", conumerGroup=" + conumerGroup + ", namesrvAddr="
				+ namesrvAddr + "]";
	}
	
	
}
