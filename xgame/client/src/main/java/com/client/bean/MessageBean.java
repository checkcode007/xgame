package com.client.bean;


import cn.hutool.json.JSONUtil;

import java.nio.charset.StandardCharsets;

public class MessageBean {
    /**
     * 数据长度
     */
    private Integer len;
    /**
     * 通讯数据
     */
    private byte[] content;

    public MessageBean() {
    }

    public MessageBean(Object object){
        content =  JSONUtil.toJsonStr(object).getBytes(StandardCharsets.UTF_8);
        len = content.length;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
