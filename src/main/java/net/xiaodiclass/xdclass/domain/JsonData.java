package net.xiaodiclass.xdclass.domain;

import java.io.Serializable;

public class JsonData implements Serializable {
    private static final long seriaVersionUID=1L;
    private Integer code;//状态码 0表示成功 1表示处理中 -1表示失败
    private Object data;//数据
    private String msg;//描述

    public JsonData() {
    }

    public JsonData(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    //成功，无参
    public static JsonData buildSuccess(){
        return new JsonData(0,null,null);
    }
    //成功，传入数据
    public static JsonData buildSuccess(Object data){
        return new JsonData(0,data,null);
    }

    //成功，传入状态码和描述信息
    public static JsonData buildSuccess(Integer code,String msg){
        return new JsonData(code,null,msg);
    }

    //成功，传入数据和描述信息
    public static JsonData buildSuccess(Object data,String msg){
        return new JsonData(0,data,msg);
    }

    //失败，传入描述信息和状态码
    public static JsonData buildError(Integer code,String msg){
        return new JsonData(code,null,msg);
    }

    //失败，传入描述信息
    public static JsonData buildError(String msg){
        return new JsonData(-1,null,msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JsonData{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
