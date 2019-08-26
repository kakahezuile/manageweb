package com.xj.jodis;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.wandoulabs.jodis.JedisResourcePool;
import com.wandoulabs.jodis.RoundRobinJedisPool;
import com.xj.utils.SerializeUtil;
import com.xj.utils.SystemProperties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class JodisUtils {

	private static JedisResourcePool proxyPool;
	
	public static Jedis getResource() {
		if (null != proxyPool) {
			return proxyPool.getResource();
		}
		
		synchronized (JodisUtils.class) {
			if (null != proxyPool) {
				return proxyPool.getResource();
			}
			
			SystemProperties properties = SystemProperties.getInstance();
			
			String serverConfig = properties.get("codis.server");
			String pathConfig = properties.get("codis.path");
			if (StringUtils.isBlank(serverConfig) || StringUtils.isBlank(pathConfig)) {
				throw new RuntimeException("主机地址或路径不能为空");
			}
			
			String timeoutConfig = properties.get("codis.timeout");
			Integer timeout = StringUtils.isBlank(timeoutConfig) ? 30000 : Integer.valueOf(timeoutConfig);
			
			proxyPool = new RoundRobinJedisPool(serverConfig, timeout, pathConfig, new JedisPoolConfig());
		}
		
		return proxyPool.getResource();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	private static JedisPool jedisPool;																				//
//																									//测				//
//	public static Jedis getResource() {																//试				//
//		if (null != jedisPool) {																	//环				//
//			return jedisPool.getResource();															//境				//
//		}																							//Redis			//
//																									//服				//
//		synchronized (JodisUtils.class) {															//务				//
//			if (null != jedisPool) {																//器				//
//				return jedisPool.getResource();																		//
//			}																										//
//																													//
//			JedisPoolConfig config = new JedisPoolConfig();															//
//			config.setMaxTotal(1000);																				//
//			config.setMaxIdle(1000);																				//
//			config.setMaxWaitMillis(60000);																			//
//																													//
//			jedisPool = new JedisPool(config, "192.168.1.50", 6380, 60000);										//
//		}																											//
//																													//
//		return jedisPool.getResource();																				//
//	}																												//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void set(final String key, final String value) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = JodisUtils.getResource()) {
					jedis.set(key, value);
				} catch (Exception e) {
					throw new RuntimeException("同步数据到redis出错", e);
				}
			}
		}).start();
	}
	
	public static void setex(final String key, final String value, final int seconds) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = JodisUtils.getResource()) {
					jedis.setex(key, seconds, value);
				} catch (Exception e) {
					throw new RuntimeException("同步数据到redis出错", e);
				}
			}
		}).start();
	}
	
	public static void setnx(final String key, final String value) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = JodisUtils.getResource()) {
					jedis.setnx(key, value);
				} catch (Exception e) {
					throw new RuntimeException("同步数据到redis出错", e);
				}
			}
		}).start();
	}
	
	public static void del(final String key) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = JodisUtils.getResource()) {
					jedis.del(key);
				} catch (Exception e) {
					throw new RuntimeException("删除redis数据出错", e);
				}
			}
		}).start();
	}
	
	public static void serialize(final String key, final Serializable value) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = JodisUtils.getResource()) {
					jedis.set(SerializeUtil.serialize(key), SerializeUtil.serialize(value));
				} catch (Exception e) {
					throw new RuntimeException("同步数据到redis出错", e);
				}
			}
		}).start();
	}
	
	public static void serializeList(final String key, final List<Serializable> values) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try (Jedis jedis = JodisUtils.getResource()) {
					jedis.set(SerializeUtil.serialize(key), SerializeUtil.serializeList(values));
				} catch (Exception e) {
					throw new RuntimeException("同步数据到redis出错", e);
				}
			}
		}).start();
	}
	
//	public static void main(String[] args) {
//		try (Jedis jedis = getResource()) {
//			System.out.println(jedis.exists("CrazySalesCommunityViewHuman_10_1"));
//		}
//	}
//	
//	public static void main(String[] args) {
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(1000);
//		config.setMaxIdle(1000);
//		config.setMaxWaitMillis(60000);
//		config.setTestOnBorrow(true);
//		JedisPool jedisPool = new JedisPool(config, "114.215.153.192", 6379, 60000);
//		try (Jedis jedis = jedisPool.getResource()) {
//			Set<String> keys = jedis.keys("*");
//			
//			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
//				System.out.println(iterator.next());
//			}
//			
//			//jedis.flushAll();
//		}
//	}
//	
//	public static void main(String[] args) {
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//		
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(1000);
//		config.setMaxIdle(1000);
//		config.setMaxWaitMillis(60000);
//		config.setTestOnBorrow(true);
//		
//		JedisPool jedisPool = new JedisPool(config, "114.215.153.192", 6379, 60000);
//		try (Jedis jedis = jedisPool.getResource()) {
//			RedisUser user = (RedisUser)SerializeUtil.unserialize(jedis.get("15810304090".getBytes()));
//			
//			System.out.println("key: " + user.getKey());//18191386056
//			System.out.println("emobId: " + user.getEmobId());//e58b922b4a10b80b024915e258dde48e
//			System.out.println("Equipment: " + user.getEquipment());//null
//			System.out.println("CommunityId: " + user.getCommunityId());//5
//			System.out.println("LoginTime: " + dateFormat.format(new Date(user.getLoginTime() * 1000L)));//2015-08-27 15:31:32 000
//		}
//	}
//	
//	public static void main(String[] args) {
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(1000);
//		config.setMaxIdle(1000);
//		config.setMaxWaitMillis(60000);
//		config.setTestOnBorrow(true);
//		JedisPool jedisPool = new JedisPool(config, "114.215.153.192", 6379, 60000);
//		try (Jedis jedis = jedisPool.getResource()) {
//			Set<String> keys = jedis.keys("*");
//			
//			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
//				String key = iterator.next();
//				if (key.indexOf("CommunityCrazySalesPriceRank_") == 0 || key.indexOf("CrazySalesCommunityViewCount_") == 0 || key.indexOf("CrazySalesCommunityViewHuman_") == 0) {
//					jedis.del(key);
//					
//					System.out.println("Deleted: " + key);
//				}
//			}
//		}
//	}
}