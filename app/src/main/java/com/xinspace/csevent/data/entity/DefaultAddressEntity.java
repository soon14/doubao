package com.xinspace.csevent.data.entity;

/**
 * Created by Administrator on 2015/11/28.
 * 默认地址实体类
 */
public class DefaultAddressEntity {
    private String result;
    private String msg;

    public DefaultAddressEntity() {
    }

    public DefaultAddressEntity(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "DefaultAddressEntity{" +
                "result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
