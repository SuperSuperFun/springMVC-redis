package com.wangli.dao.impl;

import com.wangli.dao.RedisCacheDao;
import com.wangli.utils.SerializeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author wangli
 */
@Repository
public class RedisCacheDaoImpl implements RedisCacheDao {
    private static final Logger logger = Logger.getLogger(RedisCacheDaoImpl.class);

    static final byte[] ngHisByte = SerializeUtil.serialize("WANDA_NGHIS");

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;

    @Override
    public boolean saveObject(final String key, final Object value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keyByte = SerializeUtil.serialize(key);
                byte[] valueByte = SerializeUtil.serialize(value);
                Boolean flag = false;
                if (redisConnection.hExists(ngHisByte, keyByte)) {
                    logger.info("数据已存在，先删除旧数据");
                    redisConnection.hDel(ngHisByte, keyByte);
                }
                flag = redisConnection.hSet(ngHisByte, keyByte, valueByte);
                return flag;
            }
        });
        return result;
    }

    @Override
    public Object getObject(final String key) {
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Object returnObject = null;
                byte[] keyByte = SerializeUtil.serialize(key);
                if (redisConnection.hExists(ngHisByte, keyByte)) {
                    logger.info("数据存在--开始读取");
                    returnObject = redisConnection.hGet(ngHisByte, keyByte);
                } else {
                    logger.info("数据不存在");
                }
                return returnObject;
            }
        });
        return result;
    }

    @Override
    public boolean deleteKey(final String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] keyByte = SerializeUtil.serialize(key);
                Boolean flag = false;
                if (redisConnection.hExists(ngHisByte, keyByte)) {
                    logger.info("存在该键，执行删除");
                    flag = redisConnection.hDel(ngHisByte, keyByte);
                }
                return flag;
            }
        });
        return result;
    }
}
