package com.redis;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 单机客户端
 * 
 * @author ZhenyuJin
 *
 */
public class RedisUtil {
	private static JedisPool pool = null;
	private static ResourceBundle bundle = ResourceBundle.getBundle("redis_config");
	// private static int objectTimeOut =
	// Integer.valueOf(bundle.getString("redis.objecttimeout"));
	// private static int redisPictureTimeout =
	// Integer.parseInt(bundle.getString("redis.picturetimeout"));
	private static boolean switchFlag = false; // 主从服务器状态需要检测 默认需要
	static {
		// whenExhaustedAction：表示当pool中的jedis实例都被allocated完时，pool要采取的操作；默认有三种。
		// WHEN_EXHAUSTED_FAIL --> 表示无jedis实例时，直接抛出NoSuchElementException；
		// WHEN_EXHAUSTED_BLOCK -->
		// 则表示阻塞住，或者达到maxWait时抛出JedisConnectionException；
		// WHEN_EXHAUSTED_GROW --> 则表示新建一个jedis实例，也就说设置的maxActive无用；
		// maxWait：表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		// testOnBorrow：在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
		// testOnReturn：在return给pool时，是否提前进行validate操作；
		// testWhileIdle：如果为true，表示有一个idle object evitor线程对idle
		// object进行扫描，如果validate失败，此object会被从pool中drop掉；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
		// timeBetweenEvictionRunsMillis：表示idle object evitor两次扫描之间要sleep的毫秒数；
		// numTestsPerEvictionRun：表示idle object evitor每次扫描的最多的对象数；
		// minEvictableIdleTimeMillis：表示一个对象至少停留在idle状态的最短时间，然后才能被idle object
		// evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义；
		// softMinEvictableIdleTimeMillis：在minEvictableIdleTimeMillis基础上，加入了至少minIdle个对象已经在pool里面了。如果为-1，evicted不会根据idle
		// time驱逐任何对象。如果minEvictableIdleTimeMillis>0，则此项设置无意义，且只有在timeBetweenEvictionRunsMillis大于0时才有意义；
		// 构造连接池
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 属性文件读取参数信息
		String[] hostArray = bundle.getString("redis.host").split(",");
		String host = hostArray[0];
		int port = Integer.valueOf(bundle.getString("redis.port"));
		int timeout = Integer.valueOf(bundle.getString("redis.timeout"));
		int maxClient = Integer.valueOf(bundle.getString("redis.maxclient"));
		String password = bundle.getString("redis.password");
		// 控制一个pool最多有多少个状态为idle的jedis实例
		jedisPoolConfig.setMaxTotal(maxClient);
		// // 最大能够保持空闲状态的对象数
		// jedisPoolConfig.setMaxIdle(1);
		// //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		// jedisPoolConfig.setNumTestsPerEvictionRun(3);
		// //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
		// 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
		// jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(-1);
		// // jedisPoolConfig.setNumTestsPerEvictionRun(5);
		// //在空闲时检查有效性, 默认false
		// jedisPoolConfig.setTestWhileIdle(true);
		// //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		// jedisPoolConfig.setTimeBetweenEvictionRunsMillis(-1);
		jedisPoolConfig.setMaxWaitMillis(10000);
		// 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
		jedisPoolConfig.setTestOnBorrow(true);
		// 在还会给pool时，是否提前进行validate操作
		jedisPoolConfig.setTestOnReturn(true);
		if (StringUtils.isNotEmpty(password)) {
			pool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
		} else {
			pool = new JedisPool(jedisPoolConfig, host, port, timeout);
		}
	}

	/**
	 * 通过key获取储存在redis中的value
	 * 
	 * @param key
	 * @return 成功返回value 失败返回null
	 */
	public static String get(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.get(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取储存在redis中的value
	 * 
	 * @param key
	 * @return 成功返回value 失败返回null
	 */
	public static byte[] get(byte[] key) {
		if (key != null && key.length > 0) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.get(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 向redis存入key和value,并释放连接资源
	 * 
	 * @param key
	 * @param value
	 */
	public static String set(String key, String value) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.set(key, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 根据KEY获取对象值
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object getObject(String key) {
		if (StringUtils.isNotEmpty(key)) {
			byte[] returnBytes = get(key.getBytes());
			return SerializeUtil.unserialize(returnBytes);
		}
		return null;
	}

	/**
	 * 设置KEY,Object
	 * 
	 * @param key
	 * @param o
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String setObject(String key, Object o) {
		if (StringUtils.isNotEmpty(key) && o != null) {
			return set(key.getBytes(), SerializeUtil.serialize(o));
		}
		return null;
	}

	/**
	 * 根据KEY设置对象值
	 * 
	 * @author ZhenyuJin
	 * @param key
	 * @param value
	 * @return
	 * @since 2015年3月11日 上午10:18:59
	 */
	public static String set(byte[] key, byte[] value) {
		if (key != null && value != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.set(key, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 设置key value并制定这个键值的有效期
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return 成功返回OK 失败和异常返回null
	 */
	public static String set(byte[] key, byte[] value, int seconds) {
		if (key != null && value != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.setex(key, seconds, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 根据KEY获取对象值
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static byte[] getBytes(String key) {
		if (StringUtils.isNotEmpty(key)) {
			return get(key.getBytes());
		}
		return null;
	}

	public static byte[] getBytes(byte[] key, int seconds) {
		if (key != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.get(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key向指定的value值追加值
	 * 
	 * @param key
	 * @param str
	 * @return 成功返回 添加后value的长度 失败 返回 添加的 value 的长度 异常返回0L
	 */
	public static Long append(String key, String str) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(str)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.append(key, str);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return true OR false
	 */
	public static Boolean exists(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.exists(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
			return false;
		}
		return null;
	}

	/**
	 * 若key不存在则设置，否则不设置。
	 * 
	 * @param key
	 * @param value
	 * @return 成功返回1 如果存在 和 发生异常 返回 0
	 */
	public static Long setnx(String key, String value) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.setnx(key, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 设置key value并制定这个键值的有效期
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            单位:秒
	 * @return 成功返回OK 失败和异常返回null
	 */
	public static String setex(String key, String value, int seconds) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.setex(key, seconds, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 用指定的字符串覆盖给定 key 所储存的字符串值,覆盖的位置从偏移量 offset 开始。
	 * 
	 * @param key
	 * @param str
	 * @param offset
	 *            下标位置
	 * @return 返回替换后 value 的长度
	 */
	public static Long setrange(String key, String str, int offset) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(str)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.setrange(key, offset, str);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过批量的key获取批量的value
	 * 
	 * @param keys
	 *            string数组 也可以是一个key
	 * @return 成功返回value的集合, 失败返回null的集合 ,异常返回空
	 */
	public static List<String> mget(String... keys) {
		if (keys != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.mget(keys);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 批量的设置key:value。 obj.mset(new String[]{"key2","value1","key2","value2"})
	 * 
	 * @param keysvalues
	 * @return 成功返回OK 失败 异常 返回 null
	 * 
	 */
	public static String mset(String... keysvalues) {
		if (keysvalues != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.mset(keysvalues);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 批量的设置key:value,可以一个,如果key已经存在则会失败,操作会回滚 example: obj.msetnx(new
	 * String[]{"key2","value1","key2","value2"})
	 * 
	 * @param keysvalues
	 * @return 成功返回1 失败返回0
	 */
	public static Long msetnx(String... keysvalues) {
		if (keysvalues != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.msetnx(keysvalues);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 设置key value并制定这个键值的有效期
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            单位:秒
	 * @return 成功返回OK 失败和异常返回null
	 */
	public static String set(String key, String value, int seconds) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.setex(key, seconds, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 设置key的值,并返回一个旧值
	 * 
	 * @param key
	 * @param value
	 * @return 旧值 如果key不存在 则返回null
	 */
	public static String getset(String key, String value) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.getSet(key, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过下标 和key 获取指定下标位置的 value
	 * 
	 * @param key
	 * @param startOffset
	 *            开始位置 从0 开始 负数表示从右边开始截取
	 * @param endOffset
	 * @return 如果没有返回null
	 */
	public static String getrange(String key, int startOffset, int endOffset) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.getrange(key, startOffset, endOffset);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
	 * 
	 * @param key
	 * @return 加值后的结果
	 */
	public static Long incr(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.incr(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key给指定的value加值,如果key不存在,则这是value为该值
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public static Long incrBy(String key, Long integer) {
		if (StringUtils.isNotEmpty(key) && null != integer) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.incrBy(key, integer);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 对key的值做减减操作,如果key不存在,则设置key为-1
	 * 
	 * @param key
	 * @return
	 */
	public static Long decr(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.decr(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 减去指定的值
	 * 
	 * @param key
	 * @param integer
	 * @return
	 */
	public static Long decrBy(String key, Long integer) {
		if (StringUtils.isNotEmpty(key) && null != integer) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.decrBy(key, integer);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取value值的长度
	 * 
	 * @param key
	 * @return 失败返回null
	 */
	public static Long strlen(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.strlen(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key给field设置指定的值,如果key不存在,则先创建
	 * 
	 * @param key
	 * @param field
	 *            字段
	 * @param value
	 * @return 如果存在返回0 异常返回null
	 */
	public static Long hset(String key, String field, String value) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hset(key, field, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key给field设置指定的值,如果key不存在则先创建,如果field已经存在,返回0
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static Long hsetnx(String key, String field, String value) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hsetnx(key, field, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key同时设置 hash的多个field
	 * 
	 * @param key
	 * @param hash
	 * @return 返回OK 异常返回null
	 */
	public static String hmset(String key, Map<String, String> hash) {
		if (StringUtils.isNotEmpty(key) && hash != null) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hmset(key, hash);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key 和 field 获取指定的 value
	 * 
	 * @param key
	 * @param field
	 * @return 没有返回null
	 */
	public static String hget(String key, String field) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(field)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hget(key, field);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key 和 fields 获取指定的value 如果没有对应的value则返回null
	 * 
	 * 1 如果字段是哈希值和一个新字段被设置。 0 如果字段已经存在于哈希并且值被更新。
	 * 
	 * @param key
	 * @param fields
	 *            可以使 一个String 也可以是 String数组
	 * @return
	 */
	public static List<String> hmget(String key, String... fields) {
		if (StringUtils.isNotEmpty(key) && null != fields) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hmget(key, fields);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key给指定的field的value加上给定的值
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static Long hincrby(String key, String field, Long value) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(field) && null != value) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hincrBy(key, field, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key和field判断是否有指定的value存在
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static Boolean hexists(String key, String field) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(field)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hexists(key, field);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回field的数量
	 * 
	 * @param key
	 * @return
	 */
	public static Long hlen(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hlen(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回所有的field
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> hkeys(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hkeys(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回所有和key有关的value
	 * 
	 * @param key
	 * @return
	 */
	public static List<String> hvals(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hvals(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取所有的field和value
	 * 
	 * @param key
	 * @return
	 */
	public static Map<String, String> hgetall(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.hgetAll(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key向list头部添加字符串
	 * 
	 * @param key
	 * @param strs
	 *            可以使一个string 也可以使string数组
	 * @return 返回list的value个数
	 */
	public static Long lpush(String key, String... strs) {
		if (StringUtils.isNotEmpty(key) && null != strs) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lpush(key, strs);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key向list尾部添加字符串
	 * 
	 * @param key
	 * @param strs
	 *            可以使一个string 也可以使string数组
	 * @return 返回list的value个数
	 */
	public static Long rpush(String key, String... strs) {
		if (StringUtils.isNotEmpty(key) && null != strs) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.rpush(key, strs);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key在list指定的位置之前或者之后 添加字符串元素
	 * 
	 * @param key
	 * @param where
	 *            LIST_POSITION枚举类型
	 * @param pivot
	 *            list里面的value
	 * @param value
	 *            添加的value
	 * @return
	 */
	public static Long linsert(String key, LIST_POSITION where, String pivot, String value) {
		if (StringUtils.isNotEmpty(key) && null != where && StringUtils.isNotEmpty(pivot)
				&& StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.linsert(key, where, pivot, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key设置list指定下标位置的value 如果下标超过list里面value的个数则报错
	 * 
	 * @param key
	 * @param index
	 *            从0开始
	 * @param value
	 * @return 成功返回OK
	 */
	public static String lset(String key, Long index, String value) {
		if (StringUtils.isNotEmpty(key) && null != index && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lset(key, index, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key从对应的list中删除指定的count个 和 value相同的元素
	 * 
	 * @param key
	 * @param count
	 *            当count为0时删除全部
	 * @param value
	 * @return 返回被删除的个数
	 */
	public static Long lrem(String key, Long count, String value) {
		if (StringUtils.isNotEmpty(key) && null != count && StringUtils.isNotEmpty(value)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lrem(key, count, value);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key保留list中从strat下标开始到end下标结束的value值
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return 成功返回OK
	 */
	public static String ltrim(String key, Long start, Long end) {
		if (StringUtils.isNotEmpty(key) && null != start && null != end) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.ltrim(key, start, end);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key从list的头部删除一个value,并返回该value
	 * 
	 * @param key
	 * @return
	 */
	public static String lpop(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lpop(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key从list尾部删除一个value,并返回该元素
	 * 
	 * @param key
	 * @return
	 */
	public static String rpop(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.rpop(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key从一个list的尾部删除一个value并添加到另一个list的头部,并返回该value 如果第一个list为空或者不存在则返回null
	 * 
	 * @param srckey
	 * @param dstkey
	 * @return
	 */
	public static String rpoplpush(String srckey, String dstkey) {
		if (StringUtils.isNotEmpty(srckey) && StringUtils.isNotEmpty(dstkey)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.rpoplpush(srckey, dstkey);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取list中指定下标位置的value
	 * 
	 * @param key
	 * @param index
	 * @return 如果没有返回null
	 */
	public static String lindex(String key, long index) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lindex(key, index);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回list的长度
	 * 
	 * @param key
	 * @return
	 */
	public static Long llen(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.llen(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取list指定下标位置的value 如果start 为 0 end 为 -1 则返回全部的list中的value
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> lrange(String key, Long start, Long end) {
		if (StringUtils.isNotEmpty(key) && null != start && null != end) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.lrange(key, start, end);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key向指定的set中添加value
	 * 
	 * @param key
	 * @param members
	 *            可以是一个String 也可以是一个String数组
	 * @return 添加成功的个数
	 */
	public static Long sadd(String key, String... members) {
		if (StringUtils.isNotEmpty(key) && null != members) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sadd(key, members);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key删除set中对应的value值
	 * 
	 * @param key
	 * @param members
	 *            可以是一个String 也可以是一个String数组
	 * @return 删除的个数
	 */
	public static Long srem(String key, String... members) {
		if (StringUtils.isNotEmpty(key) && null != members) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.srem(key, members);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key随机删除一个set中的value并返回该值
	 * 
	 * @param key
	 * @return
	 */
	public static String spop(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.spop(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取set中的差集 以第一个set为标准
	 * 
	 * @param keys
	 *            可以使一个string 则返回set中所有的value 也可以是string数组
	 * @return
	 */
	public static Set<String> sdiff(String... keys) {
		if (null != keys) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sdiff(keys);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取set中的差集并存入到另一个key中 以第一个set为标准
	 * 
	 * @param dstkey
	 *            差集存入的key
	 * @param keys
	 *            可以使一个string 则返回set中所有的value 也可以是string数组
	 * @return
	 */
	public static Long sdiffstore(String dstkey, String... keys) {
		if (StringUtils.isNotEmpty(dstkey) && null != keys) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sdiffstore(dstkey, keys);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取指定set中的交集
	 * 
	 * @param keys
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public static Set<String> sinter(String... keys) {
		if (null != keys) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sinter(keys);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取指定set中的交集 并将结果存入新的set中
	 * 
	 * @param dstkey
	 * @param keys
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public static Long sinterstore(String dstkey, String... keys) {
		if (StringUtils.isNotEmpty(dstkey) && null != keys) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sinterstore(dstkey, keys);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回所有set的并集
	 * 
	 * @param keys
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public static Set<String> sunion(String... keys) {
		if (null != keys) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sunion(keys);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回所有set的并集,并存入到新的set中
	 * 
	 * @param dstkey
	 * @param keys
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public static Long sunionstore(String dstkey, String... keys) {
		if (StringUtils.isNotEmpty(dstkey) && null != keys) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sunionstore(dstkey, keys);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key将set中的value移除并添加到第二个set中
	 * 
	 * @param srckey
	 *            需要移除的
	 * @param dstkey
	 *            添加的
	 * @param member
	 *            set中的value
	 * @return
	 */
	public static Long smove(String srckey, String dstkey, String member) {
		if (StringUtils.isNotEmpty(srckey) && StringUtils.isNotEmpty(dstkey) && StringUtils.isNotEmpty(member)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.smove(srckey, dstkey, member);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取set中value的个数
	 * 
	 * @param key
	 * @return
	 */
	public static Long scard(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.scard(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key判断value是否是set中的元素
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static Boolean sismember(String key, String member) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(member)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.sismember(key, member);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取set中随机的value,不删除元素
	 * 
	 * @param key
	 * @return
	 */
	public static String srandmember(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.srandmember(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取set中所有的value
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.smembers(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key向zset中添加value,score,其中score就是用来排序的 如果该value已经存在则根据score更新元素
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static Long zadd(String key, Double score, String member) {
		if (StringUtils.isNotEmpty(key) && null != score && StringUtils.isNotEmpty(member)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zadd(key, score, member);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key删除在zset中指定的value
	 * 
	 * @param key
	 * @param members
	 *            可以使一个string 也可以是一个string数组
	 * @return
	 */
	public static Long zrem(String key, String... members) {
		if (StringUtils.isNotEmpty(key) && null != members) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zrem(key, members);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key增加该zset中value的score的值
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	public static Double zincrby(String key, Double score, String member) {
		if (StringUtils.isNotEmpty(key) && null != score && StringUtils.isNotEmpty(member)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zincrby(key, score, member);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回zset中value的排名 下标从小到大排序
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static Long zrank(String key, String member) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(member)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zrank(key, member);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回zset中value的排名 下标从大到小排序
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static Long zrevrank(String key, String member) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(member)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zrevrank(key, member);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key将获取score从start到end中zset的value socre从大到小排序 当start为0 end为-1时返回全部
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Set<String> zrevrange(String key, Long start, Long end) {
		if (StringUtils.isNotEmpty(key) && null != start && null != end) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zrevrange(key, start, end);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回指定score内zset中的value
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public static Set<String> zrangebyscore(String key, String max, String min) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(max) && StringUtils.isNotEmpty(min)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zrevrangeByScore(key, max, min);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回指定score内zset中的value
	 * 
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	public static Set<String> zrangeByScore(String key, Double max, Double min) {
		if (StringUtils.isNotEmpty(key) && null != max && null != min) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zrevrangeByScore(key, max, min);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 返回指定区间内zset中value的数量
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public static Long zcount(String key, String min, String max) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(min) && StringUtils.isNotEmpty(max)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zcount(key, min, max);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key返回zset中的value个数
	 * 
	 * @param key
	 * @return
	 */
	public static Long zcard(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zcard(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key获取zset中value的score值
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public static Double zscore(String key, String member) {
		if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(member)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zscore(key, member);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key删除给定区间内的元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long zremrangeByRank(String key, Long start, Long end) {
		if (StringUtils.isNotEmpty(key) && null != start && null != end) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zremrangeByRank(key, start, end);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key删除指定score内的元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static Long zremrangeByScore(String key, Double start, Double end) {
		if (StringUtils.isNotEmpty(key) && null != start && null != end) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.zremrangeByScore(key, start, end);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 返回满足pattern表达式的所有key keys(*) 返回所有的key
	 * 
	 * @param pattern
	 * @return
	 */
	public static Set<String> keys(String pattern) {
		if (StringUtils.isNotEmpty(pattern)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.keys(pattern);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 通过key判断值得类型
	 * 
	 * @param key
	 * @return
	 */
	public static String type(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.type(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 删除指定的key,也可以传入一个包含key的数组
	 * 
	 * @param keys
	 *            一个key 也可以使 string 数组
	 * @return 返回删除成功的个数
	 */
	public static Long del(String key) {
		if (StringUtils.isNotEmpty(key)) {
			Jedis jedis = null;
			try {
				jedis = pool.getResource();
				return jedis.del(key);
			} catch (Exception e) {
				// do nothing
			} finally {
				returnResource(jedis);
			}
		}
		return null;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public static void returnResource(Jedis jedis) {
		if (jedis != null) {
			/**
			 * starting from Jedis 3.0 this method will not be exposed. Resource
			 * cleanup should be done using @see
			 * {@link redis.clients.jedis.Jedis#close()}
			 */
			jedis.close();
		}
	}

	public static boolean getSwitchFlag() {
		return switchFlag;
	}

	public static void setSwitchFlag(boolean switchFlag) {
		RedisUtil.switchFlag = switchFlag;
	}
}
