package com.proto.redis.lettuce;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.api.StatefulConnection;


public class LettuceObjects {
    private AbstractRedisClient client;
    private StatefulConnection<String,String> connection;
    private Object cmd;
    private Object asyncCmd;
    private Object reactiveCmd;
    public LettuceObjects() {

    }
    public LettuceObjects(AbstractRedisClient client, StatefulConnection connection) {
        this.client = client;
        this.connection = connection;
    }

    public AbstractRedisClient getClient() {
        return client;
    }

    public void setClient(AbstractRedisClient client) {
        this.client = client;
    }

    public StatefulConnection<String,String> getConnection() {
        return connection;
    }

    public void setConnection(StatefulConnection connection) {
        this.connection = connection;
    }

    public Object getCmd() {
        return cmd;
    }

    public void setCmd(Object cmd) {
        this.cmd = cmd;
    }

    public Object getAsyncCmd() {
        return asyncCmd;
    }

    public void setAsyncCmd(Object asyncCmd) {
        this.asyncCmd = asyncCmd;
    }

    public Object getReactiveCmd() {
        return reactiveCmd;
    }

    public void setReactiveCmd(Object reactiveCmd) {
        this.reactiveCmd = reactiveCmd;
    }
}
