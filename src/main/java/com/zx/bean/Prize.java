package com.zx.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 奖品配置类
 * @author lishenbo
 *
 */
@Document(indexName = "redpacket", type = "prize")
public class Prize {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String id;                    //uuid,奖品id
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String actid;                 //uuid,活动id
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String name;                  //奖品名称
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
	private Integer level;                 //等级   注：同一活动中等级具有唯一性
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String type;                  //0和微币，1流量，2优惠券
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
	private Integer value;                    //奖品金额 用于和微币充值
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
	private Integer amount;                   //奖品总量
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String imgurl;                //"http://1.png",奖品图
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String tips;                  //"中奖啦",中奖文案,
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String status;                //0关闭 1开启 2软删除
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long created;               //1525251708667,创建时间
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String creator;               //:"admin" 创建人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActid() {
		return actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
