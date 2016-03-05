package com.zhitian.mybole.api;

/**
 * Created by chenxiaosong on 16/3/5.
 */
public class ApiResult {
    /**
     * ret : 0
     * msg :
     */

    private int ret;
    private String msg;
    /**
     * captcha : 9999
     */

    public void setRet(int ret) {
        this.ret = ret;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRet() {
        return ret;
    }

    public String getMsg() {
        return msg;
    }

}
