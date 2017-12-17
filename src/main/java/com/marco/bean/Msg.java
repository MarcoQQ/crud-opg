package com.marco.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marco sun on 2017/12/9.
 * 通用的返回的类
 */
public class Msg {
    //自定义 100-成功 200-失败
    private int code;
    //提示信息
    private String msg;
    //用户需要的返回信息
    private Map<String, Object> extend = new HashMap<String, Object>();

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

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public Msg add(String key, Object value){
        getExtend().put(key, value);
        return this;
    }

    public static Msg success(){
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("处理成功");
        return result;
    }

    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("处理失败");
        return result;
    }
}
