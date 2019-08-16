package com.zx.bean;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 红包记录类
 * @author admin
 *
 */
@Document(indexName = "redpacket", type = "packet")
public class Packet {
	private String id;

	private String phoneNum;
	
	private String actId;
	
	private String msg;
	
	private Long dataTime;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getDataTime() {
		return dataTime;
	}

	public void setDataTime(Long dataTime) {
		this.dataTime = dataTime;
	}

	public String getActId() {
		return actId;
	}

	public void setActId(String actId) {
		this.actId = actId;
	}

	@Override
	public String toString() {
		return "Packet [phoneNum=" + phoneNum + ", actId=" + actId + ", msg=" + msg + ", dataTime=" + dataTime + "]";
	}
	
}
