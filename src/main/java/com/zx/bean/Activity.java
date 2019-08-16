package com.zx.bean;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 活动配置类
 * @author admin
 *
 */
@Document(indexName = "redpacket", type = "activity")
public class Activity {
	private String id;                            //"uuid",活动id
	private String name;                          //大转盘1,活动名称
	private Long begin_time;                    //1525251708667,开始时间
	private Long end_time;                      //1525251708668,结束时间
	private Long prizeTime;                     //每日开奖时间
	private String created;                       //1525251708667,创建时间
	private String creator;                       //创建人
	private String status;                        //0关闭 1开启 2软删除
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(Long begin_time) {
		this.begin_time = begin_time;
	}
	public Long getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}
	public Long getPrizeTime() {
		return prizeTime;
	}
	public void setPrizeTime(Long prizeTime) {
		this.prizeTime = prizeTime;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
