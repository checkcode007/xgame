package com.proto.redis.lettuce;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.codec.StringCodec;

public enum LettuceEnum implements ILettuce{
    SINGLE{
        @Override
        public void init(AbstractRedisClient client, StatefulConnection<String,String> connection) {
            this.setLo(new LettuceObjects(client,connection));
        }
        @Override
        public  StatefulConnection<String, String> connection() {
            LettuceObjects lettuceObj = this.getLo();
            AbstractRedisClient client = lettuceObj.getClient();
            if(lettuceObj.getConnection()!=null){
                return lettuceObj.getConnection();
            }
            StatefulRedisConnection<String, String> connection = ((RedisClient)client).connect(StringCodec.UTF8);
            lettuceObj.setConnection(connection);
            return  connection;
        }

        @Override
        public Object commands() {
            connection();
            LettuceObjects lettuceObj = this.getLo();
            if(lettuceObj.getCmd()!=null) return lettuceObj.getCmd();
            Object cmd = ((StatefulRedisConnection)lettuceObj.getConnection()).sync();
            lettuceObj.setCmd(cmd);
            return cmd;
        }

        @Override
        public Object asyncCommands() {
            connection();
            LettuceObjects lettuceObj = this.getLo();
            if(lettuceObj.getAsyncCmd()!=null) return lettuceObj.getAsyncCmd();
            Object cmd = ((StatefulRedisConnection)lettuceObj.getConnection()).async();
            lettuceObj.setAsyncCmd(cmd);
            return cmd;
        }

        @Override
        public Object reactiveCommands() {
            connection();
            LettuceObjects lettuceObj = this.getLo();
            if(lettuceObj.getReactiveCmd()!=null) return lettuceObj.getReactiveCmd();
            Object cmd = ((StatefulRedisConnection)lettuceObj.getConnection()).reactive();
            lettuceObj.setReactiveCmd(cmd);
            return cmd;
        }

        @Override
        public void close() {
            if(this.getLo().getConnection()!=null) this.getLo().getConnection().close();
            this.getLo().getClient().shutdown();
        }
    }
    ,
    CLUSTER{
        @Override
        public void init(AbstractRedisClient client, StatefulConnection<String,String> connection) {
            this.setLo(new LettuceObjects(client,connection));
        }
        @Override
        public  StatefulConnection<String, String> connection() {
            LettuceObjects lettuceObj = this.getLo();
            AbstractRedisClient client = lettuceObj.getClient();
            if(lettuceObj.getConnection()!=null){
                return lettuceObj.getConnection();
            }
            StatefulRedisClusterConnection<String, String> connection = ((RedisClusterClient)client).connect(StringCodec.UTF8);
            lettuceObj.setConnection(connection);
            return  connection;
        }

        @Override
        public Object commands() {
            connection();
            LettuceObjects lettuceObj = this.getLo();
            if(lettuceObj.getCmd()!=null) return lettuceObj.getCmd();
            Object cmd = ((StatefulRedisClusterConnection)lettuceObj.getConnection()).sync();
            lettuceObj.setCmd(cmd);
            return cmd;
        }

        @Override
        public Object asyncCommands() {
            connection();
            LettuceObjects lettuceObj = this.getLo();
            if(lettuceObj.getAsyncCmd()!=null) return lettuceObj.getAsyncCmd();
            Object cmd = ((StatefulRedisClusterConnection)lettuceObj.getConnection()).async();
            lettuceObj.setAsyncCmd(cmd);
            return cmd;
        }

        @Override
        public Object reactiveCommands() {
            connection();
            LettuceObjects lettuceObj = this.getLo();
            if(lettuceObj.getReactiveCmd()!=null) return lettuceObj.getReactiveCmd();
            Object cmd = ((StatefulRedisClusterConnection)lettuceObj.getConnection()).reactive();
            lettuceObj.setReactiveCmd(cmd);
            return cmd;
        }

        @Override
        public void close() {
            if(this.getLo().getConnection()!=null) this.getLo().getConnection().close();
            this.getLo().getClient().shutdown();
        }
    }
    ;
    private LettuceObjects lo;

    public LettuceObjects getLo() {
        return lo;
    }

    public void setLo(LettuceObjects lo) {
        this.lo = lo;
    }
}
