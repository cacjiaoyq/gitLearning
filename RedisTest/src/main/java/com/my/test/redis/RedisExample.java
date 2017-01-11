package com.my.test.redis;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;
import org.redisson.Config;
import org.redisson.Redisson;

import java.sql.ResultSet;
import java.sql.SQLException;
//sample 2
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;


public class RedisExample {

	private static String strAddress  = "172.28.176.158:6379";
	private static String id  = "1111333";
	Mysql mysql=new Mysql();

    Redis redis=new Redis();

    ResultSet rs=null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("start redis");
		RedisExample t = new RedisExample();
		try {
			//t.redisLogin2();
			t.testRedis();
			//test3();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ģ���½����
    @Test
    public void redisLogin() throws SQLException{
    	//����ҵ���ID��ͨ��UI��request.getParamenter()��ȡ

    	String id="9028935b527d22cc01527d235aea0142";
    	String sql="select * from user where id_='"+id+"'";
    	String username;

    	if(redis.hexists("user_"+id, "username_")){
    		username=redis.hget("user_"+id, "username_");
    		System.out.println("Welcome Redis! User "+username+" login success");
    	}else{
    		rs=mysql.conn.createStatement().executeQuery(sql);
    		if(rs.next()==false){
    			System.out.println("Mysql no register, Please register first");
    		}else{
    			username=rs.getString("username_");
    			System.out.println("Welcome Mysql ! User "+username+" login success");
    			redis.hset("user_"+id, "username_", username);
    			//30����δ�����͹���
    			redis.expire("user_"+id, 1800);

    		}

    	}

    }
    
    @Test
    public void redisLogin2() throws SQLException{
    	//����ҵ���ID��ͨ��UI��request.getParamenter()��ȡ
    	try{

    		String sql="select * from wxshop.user_tbl where userid='"+id+"'";
    		String username;
    		

    		
    		if(redis.hexists("user_"+id, "name")){
    			username=redis.hget("user_"+id, "name");
    			System.out.println("Welcome Redis! User "+username+" login success");
    		}else{
    			rs=mysql.conn.createStatement().executeQuery(sql);
    			if(rs.next()==false){
    				System.out.println("Mysql no register, Please register first");
    			}else{
    				username="name" + rs.getString("name");
    				System.out.println("Welcome Mysql ! User "+username+" login success");
    				redis.hset("user_"+id, "name", username);
    				//30����δ�����͹���
    				redis.expire("user_"+id, 1800);
    			}
    		}
    		}catch(Exception ex){
    			System.out.println(ex.getMessage());
    		}
    }

    public void testRedis(){
    	System.out.println("testRedis");
    	//redis����string
    	String username;
    	String key1 = "user_test3"+id;
    	String key2 = "user_test4"+id;
    	if (!redis.exists(key1)){
    		redis.set(key1, "test");
    		System.out.println("redis set key "+redis.get(key1)+"  success");
    	}else{
    		System.out.println("redis get key "+redis.get(key1)+"  success");
    		redis.append(key1, "test");
    		System.out.println("redis get key "+redis.get(key1)+"  success");
    		//redis.del(key1);
    	}
    	
    	long res = redis.setnx(key2, "test333");
    	System.out.println(res);
    	
    	//redis����List 
    	 //��ʼǰ�����Ƴ����е�����  
    	redis.del("java framework");  
    	System.out.println(redis.lrange("java framework",0,-1));  
    	//����key java framework�д����������  
    	redis.lpush("java framework","spring");  
    	redis.lpush("java framework","struts");  
    	redis.lpush("java framework","hibernate");  
    	System.out.println(redis.lrange("java framework",0,-1));  
    	redis.del("java framework");
    	redis.rpush("java framework","spring");  
    	redis.rpush("java framework","struts");  
    	redis.rpush("java framework","hibernate"); 
    	System.out.println(redis.lrange("java framework",0,-1));
    	
    	//redis����map
    	//-----�������----------  
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("name", "xinxin");
    	map.put("age", "22");
    	map.put("qq", "123456");
    	redis.hmset("user",map);
    	
    }

	public static void test1(){
		// 1.��ʼ��
		Config config = new Config();
		
				config.setConnectionPoolSize(10);
				
				config.addAddress(strAddress);
				
			
				Redisson redisson = Redisson.create(config);
				System.out.println("reids���ӳɹ�...");

				// 2.����concurrentMap,put������ʱ��ͻ�ͬ����redis��
				ConcurrentMap<String, Object> map = redisson.getMap("FirstMap");
				map.put("wuguowei", "��");
				map.put("zhangsan", "nan");
				map.put("lisi", "Ů");

				ConcurrentMap resultMap = redisson.getMap("FirstMap");
				System.out.println("resultMap==" + resultMap.keySet());

				// 2.����Set����
				Set mySet = redisson.getSet("MySet");
				mySet.add("wuguowei");
				mySet.add("lisi");

				Set resultSet = redisson.getSet("MySet");
				System.out.println("resultSet===" + resultSet.size());
				
				//3.����Queue����
				Queue myQueue = redisson.getQueue("FirstQueue");
				myQueue.add("wuguowei");
				myQueue.add("lili");
				myQueue.add("zhangsan");
				myQueue.peek();
				myQueue.poll();

				Queue resultQueue=redisson.getQueue("FirstQueue");
				System.out.println("resultQueue==="+resultQueue);
				
				// �ر�����
				redisson.shutdown();		
	}
	
	public static void test2(){
		//���ӱ��ص� Redis ����
		Jedis jedis = new Jedis(strAddress);

		
	    //  Jedis jedis = new Jedis("localhost");
	    System.out.println("Connection to server sucessfully");
	    //�鿴�����Ƿ�����
	    System.out.println("Server is running: "+jedis.ping());
	    
	    //���� redis �ַ�������
	    jedis.set("runoobkey", "Redis tutorial");
	    // ��ȡ�洢�����ݲ����
	    System.out.println("Stored string in redis:: "+ jedis.get("runoobkey"));


	    //�洢���ݵ��б���
	    jedis.lpush("tutorial-list", "Redis");
	    jedis.lpush("tutorial-list", "Mongodb");
	    jedis.lpush("tutorial-list", "Mysql");
	    // ��ȡ�洢�����ݲ����
	    List<String> list = jedis.lrange("tutorial-list", 0 ,5);
	    for(int i=0; i<list.size(); i++) {
	       System.out.println("Stored string in redis:: "+list.get(i));
	     }

	}
	
	public static void test3(){
		RedisUtil redisUtil = new RedisUtil();
		Jedis jedis = redisUtil.getJedis();
		jedis.set("test", "aaa");
		System.out.println("Stored string in redis:: "+jedis.get("test"));
	}
	
	
}
