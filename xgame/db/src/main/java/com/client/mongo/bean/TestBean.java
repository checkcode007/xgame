package com.client.mongo.bean;


public class TestBean {
    String _id;
    String name;

    public TestBean(String _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
