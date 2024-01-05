package com.proto.mongo;

import org.springframework.stereotype.Service;

@Service
public class TestMgo extends AbstractMongo{
    @Override
    protected String getCollectionName(long shardingId) {
        return "test_t1";
    }


}
