package com.zx.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 红包记录类
 * @author lishenbo
 *
 */
@Document(indexName = "redpacket", type = "packet")
public class Packet {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String id;
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String phoneNum;
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String actId;
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String msg;
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long dataTime;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
