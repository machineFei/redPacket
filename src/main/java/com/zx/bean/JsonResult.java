package com.zx.bean;

import java.util.List;

public class JsonResult {

    private int code;

    private String msg;
    
    private List<String> packetIdList;
    /**
     * 中奖信息
     */
    private Prize prize;
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<String> getPacketIdList() {
		return packetIdList;
	}
	public void setPacketIdList(List<String> packetIdList) {
		this.packetIdList = packetIdList;
	}
	public Prize getPrize() {
		return prize;
	}
	public void setPrize(Prize prize) {
		this.prize = prize;
	}
    
}
