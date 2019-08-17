package com.zx.bean;

import org.jboss.netty.buffer.TruncatedChannelBuffer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 活动中奖纪录
 * @author lishenbo
 *
 */
@Document(indexName = "redpacket", type = "prize_record")
public class PrizeRecord {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String id;                          //uuid 记录id
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String prizeId;                    //uuid 奖品id
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String actid;                       //uuid 活动id
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String phone;                       //中奖手机号
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long date;                          //1525251708667  中奖日期
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String name;                        //":"奖品1",奖品名称
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String type;                        //0和微币，1流量，2优惠券

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}

	public String getActid() {
		return actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PrizeRecord [id=" + id + ", prizeId=" + prizeId + ", actid=" + actid + ", phone=" + phone + ", date="
				+ date + ", name=" + name + ", type=" + type + "]";
	}

}
