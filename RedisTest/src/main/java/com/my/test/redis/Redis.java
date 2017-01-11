package com.my.test.redis;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

public class Redis {
	public Jedis redis;

    {

            redis = new Jedis("172.28.176.158", 6379);
            
            //redis.auth("root");
            

    }


	
	public String get(String key) {
		
        return redis.get(key);

}



public String set(String key, String value) {

        return redis.set(key, value);

}



public Long del(String... keys) {

        return redis.del(keys);

}



// ¼üÖµÔö¼Ó×Ö·û

public Long append(String key, String str) {

        return redis.append(key, str);

}



public Boolean exists(String key) {

        return redis.exists(key);

}



// Need research

public Long setnx(String key, String value) {

        return redis.setnx(key, value);

}



public String setex(String key, String value, int seconds) {

        return redis.setex(key, seconds, value);

}



public Long setrange(String key, String str, int offset) {

        return redis.setrange(key, offset, str);

}



public List<String> mget(String... keys) {

        return redis.mget(keys);

}



public String mset(String... keys) {

        return redis.mset(keys);

}


public Long msetnx(String... keysvalues) {

        return redis.msetnx(keysvalues);

}



public String getset(String key, String value) {

        return redis.getSet(key, value);

}



public String hmset(String key, Map<String, String> hash) {

        return redis.hmset(key, hash);

}



public Map<String, String> hgetall(String key) {

        return redis.hgetAll(key);

}



public String hget(final String key, final String field) {

        return redis.hget(key, field);

}



public Long hset(final String key, final String field, final String value) {

        return redis.hset(key, field, value);

}



public Long expire(final String key, final int seconds) {

        return redis.expire(key, seconds);

}



public Boolean hexists(final String key, final String field) {

        return redis.hexists(key, field);

}

public List<String> lrange(final String key, final long start,final long end) {

    return redis.lrange(key, start, end);

}

public Long lpush(final String key, String... value) {

    return redis.lpush(key, value);

}

public Long rpush(final String key, String... value) {

    return redis.rpush(key, value);

}





}
