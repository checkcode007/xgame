package com.gamefull.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.gamefull.mongo.bean.TestBean;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

public enum MongoDBUtil {
    ins;
    private static com.mongodb.client.MongoClient mongoClient;
    private static final String DB_HOST = "dev-docdb.cl65l50la7qe.ap-southeast-1.docdb.amazonaws.com";
    private static final int DB_PORT = 27017;
    private static final String DB_NAME = "pocket_test";
    private static final String USERNAME = "mongouser";
    private static final String PASSWORD = "7pfLynSie5L7B!UU$#Elu";

    private MongoDBUtil() {
        init1();
    }

    // 初始化连接
    public void init() {
//        mongodb://mongouser:7pfLynSie5L7B!UU$#Elu@dev-docdb.cl65l50la7qe.ap-southeast-1.docdb.amazonaws.com:27017/pocket_code?retryWrites=false
        if (mongoClient == null) {
            MongoCredential credential = MongoCredential.createCredential(USERNAME, DB_NAME, PASSWORD.toCharArray());
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(DB_HOST, DB_PORT))))
                    .credential(credential)
                    .build();

            mongoClient = MongoClients.create(settings);
        }
    }
    private MongoTemplate  mongoTemp;
    public void init1(){
       MongoCredential credential = MongoCredential.createCredential(USERNAME, DB_NAME, PASSWORD.toCharArray());
       MongoClientSettings settings = MongoClientSettings.builder()
               .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(DB_HOST, DB_PORT))))
               .credential(credential)
               .build();
       mongoClient = MongoClients.create(settings);
       mongoTemp=new MongoTemplate(mongoClient, settings.getCredential().getSource());
   }

    // 获取MongoClient对象
    public com.mongodb.client.MongoClient getMongoClient() {
        return mongoClient;
    }

    // 获取指定数据库
    public MongoDatabase getDatabase(String dbName) {
        return mongoClient.getDatabase(dbName);
    }

    // 获取默认数据库
    public MongoDatabase getDefaultDatabase() {
        return mongoClient.getDatabase(DB_NAME);
    }

    // 获取指定集合
    public MongoCollection<Document> getCollection(String dbName, String collectionName) {
        return getDatabase(dbName).getCollection(collectionName);
    }

    // 获取默认集合
    public MongoCollection<Document> getDefaultCollection(String collectionName) {
        return getDefaultDatabase().getCollection(collectionName);
    }

    // 关闭连接
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    public Document findFirst(MongoCollection<Document> collection) {
        return collection.find().first();
    }

    // 查询文档
    public Document findOne(MongoCollection<Document> collection, Document query) {
        return collection.find(query).first();
    }

    // 插入文档
    public  void insertOne(MongoCollection<Document> collection, Document document) {
        collection.insertOne(document);
    }
    public ObjectId insert(MongoCollection<Document> collection, Document document) {
        collection.insertOne(document);
        return document.getObjectId("_id");
    }
    // 更新文档
    public void updateOne(MongoCollection<Document> collection, Document query, Document update) {
        collection.updateOne(query, update);
    }

    // 删除文档
    public void deleteOne(MongoCollection<Document> collection, Document query) {
        collection.deleteOne(query);
    }

    // 更多数据库操作方法可以根据实际需求进行扩展
    public static void main1(String[] args) {
        MongoCollection<Document> collection = MongoDBUtil.ins.getCollection("pocket_test","test1");

        Document query = new Document("nubmer1",1);
        query = new Document("description1","nhao");
//        Document query = new Document("_id", "64a12d4843d78601223f5356");
        Document d = MongoDBUtil.ins.findOne(collection,query);
//        Document d = MongoDBUtil.ins.findFirst(collection);
        System.err.println(d);
        // 插入文档
        collection = MongoDBUtil.ins.getCollection("pocket_test","test3");
        Document document = new Document("name", "John Doe")
                .append("age", 30)
                .append("city", "New York");
        collection.insertOne(document);
        System.err.println("_id :"+document.get("_id"));
        System.err.println("objId :"+document.getObjectId("_id"));
        MongoDBUtil.ins.close();
    }
    public static void main(String[] args) {
        List<TestBean>  list =  MongoDBUtil.ins.mongoTemp.findAll(TestBean.class,"test_t1");
        System.err.println("list :"+Arrays.toString(list.toArray()));
        MongoDBUtil.ins.close();
    }
}