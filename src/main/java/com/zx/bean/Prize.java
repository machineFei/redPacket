package com.zx.bean;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 奖品配置类
 * @author admin
 *
 */
@Document(indexName = "redpacket", type = "prize")
public class Prize {
	private String id;                    //uuid,奖品id
	
	private String actid;                 //uuid,活动id
	
	private String name;                  //奖品名称
	
	private String level;                 //等级   注：同一活动中等级具有唯一性
	
	private String type;                  //0和微币，1流量，2优惠券
	
	private int value;                    //奖品金额 用于和微币充值
	
	private int amount;                   //奖品总量
	
	private String imgurl;                //"http://1.png",奖品图
	
	private String tips;                  //"中奖啦",中奖文案,
	
	private String status;                //0关闭 1开启 2软删除
	
	private String created;               //1525251708667,创建时间
	
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
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
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
