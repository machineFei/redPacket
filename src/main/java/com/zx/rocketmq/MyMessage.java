package com.zx.rocketmq;

public class MyMessage {

    private String phoneNum;

    private String msg;

    public MyMessage(String phoneNum, String msg) {
        this.phoneNum = phoneNum;
        this.msg = msg;
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

    @Override
    public String toString() {
        return "MyMessage{" +
                "phoneNum='" + phoneNum + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
