package com.xirui.mongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * mongo
 * @author zcj
 */
public abstract class AbstractMongo {
    @Autowired
    private MongoTemplate mongoTemp;
    /**
     * 获取集合名称支持分片
     *
     * @param shardingId 分片ID
     * @return 返回结合名
     */
    protected abstract String getCollectionName(long shardingId);

    protected Document executeCommand(String jsonCommand) {
        return mongoTemp.executeCommand(jsonCommand);
    }

    /**
     * 统计数量
     *
     * @param userId   用户id
     * @param criteria 统计条件
     * @return 返回统计结果
     */
    protected long count(long userId, Criteria criteria) {
        return mongoTemp.count(new Query(criteria), getCollectionName(userId));
    }

    protected <T> T findOne(long userId, Object id, Class<T> entityClass) {
        return mongoTemp.findById(id, entityClass, getCollectionName(userId));
    }

    protected <T> T findOne(long userId, Query query, Class<T> entityClass) {
        return mongoTemp.findOne(query, entityClass, getCollectionName(userId));
    }

    protected <T> List<T> find(long userId, Criteria criteria, Class<T> entityClass) {
        return mongoTemp.find(new Query(criteria), entityClass, getCollectionName(userId));
    }

    protected <T> List<T> find(long userId, Query query, Class<T> entityClass) {
        return mongoTemp.find(query, entityClass, getCollectionName(userId));
    }


    protected UpdateResult updateFirst(Object id, Update update, Class c, long userId) {
        return mongoTemp.updateFirst(Query.query(Criteria.where("_id").is(id)), update, c, getCollectionName(userId));
    }

    protected UpdateResult updateFirst(Query query, Update update, Class c, long userId) {
        return mongoTemp.updateFirst(query, update, c, getCollectionName(userId));
    }

    protected UpdateResult updateMulti(Query query, Update update, Class c, long userId) {
        return mongoTemp.updateMulti(query, update, c, getCollectionName(userId));
    }

    protected DeleteResult remove(long userId, Query query) {
        return mongoTemp.remove(query, getCollectionName(userId));
    }

    /**
     * 保存实体
     *
     * @param userId       用户id
     * @param objectToSave 保存对象
     * @param <T>          保存对象类型
     * @return 返回保存的对象
     */
    public <T> T save(long userId, T objectToSave) {
        return mongoTemp.save(objectToSave, getCollectionName(userId));
    }

    /**
     * 更新数据，如果数据不存在就新增
     *
     * @param userId   用户id
     * @param criteria 查询条件
     * @param update   更新值
     * @return 返回操作结果
     */
    protected UpdateResult updateOrSave(long userId, Criteria criteria, Update update) {
        return mongoTemp.upsert(Query.query(criteria), update, getCollectionName(userId));
    }

    /**
     * 创建联合索引
     * @param indexkeys 索引的名称
     * @return
     */
    public boolean createIndex( long userId,String ...indexkeys) {
        boolean scuess = true;
        try {
            Index index = new Index();
            for (String indexKey : indexkeys) {
                index.on(indexKey, Sort.Direction.DESC);
            }
            mongoTemp.indexOps(getCollectionName(userId)).ensureIndex(index);

        } catch (Exception ex) {
            scuess = false;
        }
        return scuess;
    }
    /**
     * 获取索引
     * @return
     */
    public List<IndexInfo> getInboxIndex(long userId, Class c) {
        List<IndexInfo> indexInfoList = mongoTemp.indexOps(c).getIndexInfo();

        for (IndexInfo indexInfo : indexInfoList) {
            String name = indexInfo.getName();
            // 不需要的就删除
            if(name.equals("delete")){
                // deleteInboxIndex(indexInfo.getName()); // 原
                deleteInboxIndex(userId,indexInfo.getName()); // 修改
            }
        }
        return indexInfoList;
    }

    /**
     * 删除索引
     *
     * @param indexName 索引的名称
     * @return
     */
    public boolean deleteInboxIndex(long userId,String indexName) {
        boolean scuess = true;
        try {
            mongoTemp.indexOps(getCollectionName(userId)).dropIndex(indexName);
        } catch (Exception ex) {
            scuess = false;
        }
        return scuess;
    }

    public static void main(String[] args) {

    }
}
