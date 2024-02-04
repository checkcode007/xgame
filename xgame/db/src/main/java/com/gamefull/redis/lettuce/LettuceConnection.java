package com.gamefull.redis.lettuce;

import io.lettuce.core.AbstractRedisClient;

import java.util.HashMap;
import java.util.Map;

public class LettuceConnection {
    private final Map<AbstractRedisClient,LettuceObjects> map = new HashMap<>(2);


}
