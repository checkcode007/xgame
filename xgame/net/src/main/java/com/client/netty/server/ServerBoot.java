package com.client.netty.server;

import com.client.netty.config.MyNettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerBoot {
    private final Logger log = LoggerFactory.getLogger(ServerBoot.class);
    @Autowired
    ServerBootstrap serverBootstrap;
    @Resource
    NioEventLoopGroup boosGroup;
    @Resource
    NioEventLoopGroup workerGroup;
    @Autowired
    MyNettyProperties nettyProperties;

    /**
     * 开机启动
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException {
        // 绑定端口启动
        serverBootstrap.bind(nettyProperties.getPort()).sync();
        serverBootstrap.bind(nettyProperties.getPortSalve()).sync();
        log.info("启动Netty多端口服务器: {},{}",nettyProperties.getPort(),nettyProperties.getPortSalve());
    }

    /**
     * 关闭线程池
     */
    @PreDestroy
    public void close() throws InterruptedException {
        log.info("优雅得关闭Netty服务器");
        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
