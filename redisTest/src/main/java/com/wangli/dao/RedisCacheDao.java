package com.wangli.dao;

public interface RedisCacheDao {

    /**
     * 根据key存储Object
     * @param key
     * @param value
     * @return
     */
    boolean saveObject(String key, Object value);

    /**
     * 根据key获取Object
     * @param key
     * @return
     */
    Object getObject(String key);

    /**
     * 根据key删除数据
     * @param key
     * @return
     */
    boolean deleteKey(String key);
}
