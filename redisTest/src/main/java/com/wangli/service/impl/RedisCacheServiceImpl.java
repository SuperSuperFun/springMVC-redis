package com.wangli.service.impl;

import com.wangli.dao.RedisCacheDao;
import com.wangli.service.RedisCacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangli
 */
@Service
public class RedisCacheServiceImpl implements RedisCacheService {
    private static final Logger logger = Logger.getLogger(RedisCacheServiceImpl.class);

    @Autowired
    private RedisCacheDao redisCacheDao;

    @Override
    public boolean putSessionObject(String id, Object sessionObject) {
        boolean flag = false;
        if (sessionObject != null && id != null && id.trim().length()>0) {
            flag = redisCacheDao.saveObject(id, sessionObject);
            logger.info("数据缓存标志：" + flag);
        } else {
            logger.info("参数含有空值或为Null，缓存失败" + flag);
        }
        return flag;
    }

    @Override
    public boolean clearSessionObject(String id) {
        boolean flag = false;
        if (id != null && id.trim().length() > 0) {
            flag = redisCacheDao.deleteKey(id);
            logger.info("缓存清空标志" + flag);
        } else {
            logger.info("参数含有空值或为Null，清空缓存失败");
        }
        return flag;
    }

    @Override
    public Object getSessionObject(String id) {
        Object sessionObject = null;
        if (id != null && id.trim().length() > 0) {
            sessionObject = redisCacheDao.getObject(id);
        } else {
            logger.info("参数含有空值或为Null，获取缓存失败");
        }
        return sessionObject;
    }
}
