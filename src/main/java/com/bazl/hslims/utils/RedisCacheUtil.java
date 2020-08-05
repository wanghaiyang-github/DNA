package com.bazl.hslims.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component("redisCache")
public class RedisCacheUtil {

    @Resource
    private StringRedisTemplate  redisTemplate;

    /**
     * 向Hash中添加值
     * @param key      可以对应数据库中的表名
     * @param field    可以对应数据库表中的唯一索引
     * @param value    存入redis中的值
     */
    public void hset(String key, String field, String value) {
        if(key == null || "".equals(key)){
            return ;
        }
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 从redis中取出值
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field){
        if(key == null || "".equals(key)){
            return null;
        }
        return (String) redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 从redis中取出值
     * @param key
     * @return
     */
    public Map<Object, Object> hgetAll(String key) {
        if (key == null || "".equals(key)) {
            return null;
        }
        Map<Object, Object> hashCache = redisTemplate.opsForHash().entries(key);
        return hashCache;
    }
}
