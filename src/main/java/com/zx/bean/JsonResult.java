package com.zx.bean;

public class JsonResult {

    private int ret = 0;

    private String msg="OK";

    private String content="";
    
    /**
     * 中奖信息
     */
    private Prize prize;
    

    public Prize getPrize() {
		return prize;
	}

	public void setPrize(Prize prize) {
		this.prize = prize;
	}

	public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
