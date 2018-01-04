package com.wangli.service;

/**
 * @author wangli
 */
public interface RedisCacheService {

    /**
     * 根据id存储数据。参数说明：id和sessionObject不允许为null或空值，数据库若已存在同名键
     * @return
     * @param id
     * @param sessionObject
     * */
    public boolean putSessionObject(String id, Object sessionObject);


    /**
     * 根据id删除对象
     * @param id
     * @return
     */
    public boolean clearSessionObject(String id);

    /**
     * 根据id获取session缓存对象，id不允许为空
     * @param id
     * @return
     */
    public Object getSessionObject(String id);
}
