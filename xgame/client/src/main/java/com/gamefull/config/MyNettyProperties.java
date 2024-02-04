package com.gamefull.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "netty")
@Configuration
public class MyNettyProperties {
    /**
     * boss线程数量 默认为cpu线程数*2
     */
    private Integer boss;
    /**
     * worker线程数量 默认为cpu线程数*2
     */
    private Integer worker;
    /**
     * 连接超时时间 默认为30s
     */
    private Integer timeout = 30000;
    /**
     * 服务器主端口 默认7000
     */
    private Integer port = 7000;
    /**
     * 服务器备用端口 默认70001
     */
    private Integer portSalve = 7001;
    /**
     * 服务器地址 默认为本地
     */
    private String host = "127.0.0.1";

    public Integer getBoss() {
        return boss;
    }

    public void setBoss(Integer boss) {
        this.boss = boss;
    }

    public Integer getWorker() {
        return worker;
    }

    public void setWorker(Integer worker) {
        this.worker = worker;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getPortSalve() {
        return portSalve;
    }

    public void setPortSalve(Integer portSalve) {
        this.portSalve = portSalve;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
