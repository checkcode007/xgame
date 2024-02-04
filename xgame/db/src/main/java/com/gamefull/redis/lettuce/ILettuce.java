package com.gamefull.redis.lettuce;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.api.StatefulConnection;

/**
 * lettuce 接口类
 * @author zcj
 */
public interface ILettuce {

    void init(AbstractRedisClient client, StatefulConnection<String,String> connection);
    StatefulConnection<String, String> connection();

    Object commands();

    Object asyncCommands();

    Object reactiveCommands();
    void close();

}
