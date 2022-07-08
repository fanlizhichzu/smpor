package com.spring.smpor.common.util;

import com.spring.smpor.common.exception.BizException;
import com.spring.smpor.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RedisTemplate工具类
 *
 * @author fanlz
 * @date 2022/05/25 09:24
 **/
@Component
@Slf4j
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @param key
     * @param time
     * @return boolean
     * @Description: 指定缓存失效时间
     * @Auther: fanlz
     * @date 2022/05/25 09:37
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            throw new BizException(ResultCode.SYSTEM_REDIS_SETEXPIRE_ERROR, e);
        }
    }

    /**
     * @param key
     * @return long
     * @Description: 根据key 获取过期时间
     * @Auther: fanlz
     * @date 2022/05/25 09:48
     */
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (Objects.isNull(expire)) {
            expire = 0L;
        }
        return expire;
    }

    /**
     * @param key
     * @return boolean
     * @Description: 判断key是否存在
     * @Auther: fanlz
     * @date 2022/05/25 09:53
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            throw new BizException(ResultCode.SYSTEM_REDIS_HASKEY_ERROR);
        }
    }

    /**
     * @param key 可以传多个
     * @return void
     * @Description: 删除缓存
     * @Auther: fanlz
     * @date 2022/05/25 09:59
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * @param key
     * @return java.lang.Object
     * @Description: 读取缓存
     * @Auther: fanlz
     * @date 2022/05/25 10:07
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key
     * @param value
     * @return boolean
     * @Description: 普通缓存放入
     * @Auther: fanlz
     * @date 2022/05/25 10:59
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            throw new BizException(ResultCode.SYSTEM_REDIS_SETVALUE_ERROR);
        }
    }

    /**
     * @param key
     * @param value
     * @param time
     * @return boolean
     * @Description: 普通缓存放入并设置时间
     * @Auther: fanlz
     * @date 2022/05/25 13:29
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            throw new BizException(ResultCode.SYSTEM_REDIS_SETVALUE_ERROR);
        }

    }

    /**
     * @param key
     * @param delta
     * @return long
     * @Description: 递增
     * @Auther: fanlz
     * @date 2022/05/25 13:40
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new BizException(ResultCode.SYSTEM_REDIS_FACTORLESS0_ERROR);
        }
        Long incr = redisTemplate.opsForValue().increment(key, delta);
        if (Objects.isNull(incr)) {
            incr = 0L;
        }
        return incr;
    }

    /**
     * @param key
     * @param delta
     * @return long
     * @Description: 递减
     * @Auther: fanlz
     * @date 2022/05/25 13:44
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new BizException(ResultCode.SYSTEM_REDIS_FACTORLESS0_ERROR);
        }
        Long decr = redisTemplate.opsForValue().decrement(key, -delta);
        if (Objects.isNull(decr)) {
            decr = 0L;
        }
        return decr;
    }

    /**
     * @param key
     * @param item
     * @return java.lang.Object
     * @Description: HashGet
     * @Auther: fanlz
     * @date 2022/05/25 13:45
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * @param key 键
     * @return java.util.Map<java.lang.Object, java.lang.Object>
     * @Description:
     * @Auther: fanlz
     * @date 2022/06/06 14:19
     */
    public Map<Object, Object> hmGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @param key
     * @param map
     * @return boolean
     * @Description: 哈希插入
     * @Auther: fanlz
     * @date 2022/06/06 14:55
     */
    public boolean hmSet(String key, Map<Object, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            throw new BizException("hmSet失败");
        }

    }

    /**
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间
     * @return boolean
     * @Description: hashset并设置时间
     * @Auther: fanlz
     * @date 2022/06/06 15:28
     */
    public boolean hmSet(String key, Map<Object, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            throw new BizException("hmSet失败");
        }
    }

    /**
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return boolean true 成功 false失败
     * @Description: 向一张hash表中放入数据, 如果不存在将创建
     * @Auther: fanlz
     * @date 2022/06/06 16:06
     */
    public boolean hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            throw new BizException("hSet失败");
        }
    }

    /**
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     * @return void
     * @Description: 删除hash表中的值
     * @Auther: fanlz
     * @date 2022/06/06 16:16
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return boolean
     * @Description: 判断hash表中是否有该项的值
     * @Auther: fanlz
     * @date 2022/06/06 16:35
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @param by   by 要增加几(大于0)
     * @return double
     * @Description: hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @Auther: fanlz
     * @date 2022/06/06 16:38
     */
    public double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * @param key  键
     * @param item 项
     * @param by   by 要减少记(小于0)
     * @return double
     * @Description: hash递减
     * @Auther: fanlz
     * @date 2022/06/06 16:43
     */
    public double Hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * @param key 键
     * @return java.util.Set<java.lang.Object>
     * @Description: 根据key获取Set中的所有值
     * @Auther: fanlz
     * @date 2022/06/06 17:11
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            throw new BizException("获取set中的值失败");
        }
    }

    /**
     * @param key
     * @param value
     * @return boolean
     * @Description: 根据value从一个set中查询, 是否存在
     * @Auther: fanlz
     * @date 2022/06/06 17:17
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("根据value从set查询结果失败，原因是：{}", e.getMessage());
            return false;
        }
    }

    /**
     * @param key    键
     * @param values 值 可以是多个
     * @return long 成功个数
     * @Description: 将数据放入set缓存
     * @Auther: fanlz
     * @date 2022/06/07 08:59
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error("set错误，原因是:{}", e.getMessage());
            return 0;
        }
    }

    /**
     * @param key    键
     * @param time   时间
     * @param values 值 可以是多个
     * @return long
     * @Description: 将数据放入set缓存
     * @Auther: fanlz
     * @date 2022/06/07 09:12
     */
    public long sSet(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            expire(key, time);
            return count;
        } catch (Exception e) {
            log.error("set错误，原因是:{}", e.getMessage());
            return 0;
        }
    }

    /**
     * @param key 键
     * @return long
     * @Description: 获取set缓存的长度
     * @Auther: fanlz
     * @date 2022/06/07 10:08
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("获取set缓存长度失败，原因是:{}", e.getMessage());
            return 0;
        }
    }

    /**
     * @param key
     * @param values
     * @return long
     * @Description: 移除值为value的
     * @Auther: fanlz
     * @date 2022/06/07 10:21
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("获取set缓存长度失败，原因是:{}", e.getMessage());
            return 0;
        }
    }

    /**
     * @param key   键
     * @param start start 开始
     * @param end   end 结束  0 到 -1代表所有值
     * @return java.util.List<java.lang.Object>
     * @Description: 获取list缓存的内容
     * @Auther: fanlz
     * @date 2022/06/07 10:35
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("获取list缓存的内容失败，原因是:{}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * @param key 键
     * @return long
     * @Description: 获取list缓存的长度
     * @Auther: fanlz
     * @date 2022/06/08 09:27
     */
    public long lGetListSize(String key) {
        long lSize = 0L;
        try {
            Optional<Long> size = Optional.ofNullable(redisTemplate.opsForList().size(key));
            if (size.isPresent()) {
                lSize = size.get();
            }
            return lSize;
        } catch (Exception e) {
            log.error("获取list缓存的长度失败，原因是:{}", e.getMessage());
            return 0L;
        }
    }

    /**
     * @param key
     * @param index
     * @return java.lang.Object
     * @Description: 根据索引获取list的值
     * @Auther: fanlz
     * @date 2022/06/08 09:33
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("根据索引获取list的值失败，原因是:{}", e.getMessage());
            return null;
        }
    }

    /**
     * @param key   键
     * @param value 值
     * @return boolean
     * @Description: 插入list
     * @Auther: fanlz
     * @date 2022/06/08 09:36
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("插入list失败，原因是:{}", e.getMessage());
            return false;
        }
    }

    /**
     * @param key   键
     * @param value 值
     * @return boolean
     * @Description: 插入list
     * @Auther: fanlz
     * @date 2022/06/08 09:36
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("插入list失败，原因是:{}", e.getMessage());
            return false;
        }
    }

    /**
     * @param key    键
     * @param values 集合
     * @return boolean
     * @Description: 插入list
     * @Auther: fanlz
     * @date 2022/06/08 09:42
     */
    public boolean lSet(String key, List<Object> values) {
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            return true;
        } catch (Exception e) {
            log.error("插入list失败，原因是:{}", e.getMessage());
            return false;
        }
    }

    /**
     * @param key    键
     * @param values 集合
     * @return boolean
     * @Description: 插入list
     * @Auther: fanlz
     * @date 2022/06/08 09:42
     */
    public boolean lSet(String key, List<Object> values, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, values);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error("插入list失败，原因是:{}", e.getMessage());
            return false;
        }
    }

    /**
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return boolean
     * @Description: 根据索引修改list中的某条数据
     * @Auther: fanlz
     * @date 2022/06/08 09:59
     */
    public boolean lUpdateByIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("根据索引修改list中的某条数据失败，原因是:{}", e.toString());
            return false;
        }
    }

    /**
     * @Description: 移除N个值为value
     * @Auther: fanlz
     * @date 2022/06/08 10:03
     * 
     * @param key 键
     * @param count 移除个数
     * @param value 值
     * @return long
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            if (Objects.isNull(remove)) {
                remove = 0L;
            }
            return remove;
        } catch (Exception e) {
            log.error("移除n个值为value失败，原因是:{}", e.getMessage());
            return 0L;
        }
    }


}
