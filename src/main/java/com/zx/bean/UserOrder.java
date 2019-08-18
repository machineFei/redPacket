package com.zx.bean;

import org.apache.lucene.document.Field.Index;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 集够红包的用户排名类
 * @author lishenbo
 *
 */
@Document(indexName = "redpacket", type = "user_order")
public class UserOrder {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String phoneNum;
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String actid;
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long sort;

	public String getActid() {
		return actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	

}
