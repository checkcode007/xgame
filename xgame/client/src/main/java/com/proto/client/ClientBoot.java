package com.proto.client;

import com.proto.bean.MessageBean;
import com.proto.config.MyNettyProperties;
import com.proto.BaseProtos;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientBoot {
    @Autowired
    Bootstrap bootstrap;
    @Autowired
    MyNettyProperties myNettyProperties;



    @PostConstruct
    public void start() throws InterruptedException {
//        sendMsg(new MessageBean("123"));
//        connect().writeAndFlush(MsgProtos.Msg.newBuilder().setId(222).setContent("test222").build());
//        connect().writeAndFlush(MsgProtos.TestMsg.newBuilder().setId(333).setB(false).setDes("des3233").setContent("test333").build());
        connect().writeAndFlush(BaseProtos.BaseMsg.newBuilder().setType(1999).setMsg(BaseProtos.Msg1.newBuilder().setId(123).setB(false).setDes("des123").setContent("test1233").build()).build());

    }

    /**
     * 主端口连接
     * @return
     * @throws InterruptedException
     */
    public Channel connect() throws InterruptedException {
        // 连接服务器
        ChannelFuture channelFuture = bootstrap.connect(myNettyProperties.getHost(), myNettyProperties.getPort()).sync();
        // 监听关闭
        Channel channel = channelFuture.channel();
        return channel;
    }
    /**
     * 备用端口连接
     * @return
     * @throws InterruptedException
     */
    public Channel connectSlave() throws InterruptedException {
        // 连接服务器
        ChannelFuture channelFuture = bootstrap.connect(myNettyProperties.getHost(), myNettyProperties.getPort()).sync();
        // 监听关闭
        Channel channel = channelFuture.channel();
        channel.closeFuture().sync();
        return channel;
    }

    /**
     * 发送消息到服务器端
     * @return
     */
    public void sendMsg(MessageBean messageBean) throws InterruptedException {
        connect().writeAndFlush(messageBean);
    }
}
