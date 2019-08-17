package com.zx.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 活动配置类
 * @author lishenbo
 *
 */
@Document(indexName = "redpacket", type = "activity")
public class Activity {
	@Id
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String id;                            //"uuid",活动id
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String name;                          //大转盘1,活动名称
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long begin_time;                      //1525251708667,开始时间
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long end_time;                        //1525251708668,结束时间
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long prizeTime;                       //每日开奖时间
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long dutation;                        //红包持续时间
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Long)
	private Long created;                       //1525251708667,创建时间
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.String)
	private String creator;                       //创建人
	
	@Field(index = FieldIndex.not_analyzed, store = true, type = FieldType.Integer)
	private Integer status;                        //0关闭 1开启 2软删除

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

	public Long getDutation() {
		return dutation;
	}

	public void setDutation(Long dutation) {
		this.dutation = dutation;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
