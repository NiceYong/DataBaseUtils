/**
 * 
 */
package bool.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis属性
 * @author wangw
 */
@Data
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {

	private int database;

	private String host;

	private int port;

	private String password;

	private int maxActive;

	private long maxWait;

	private int maxIdle;

	private int minIdle;

	private int timeout;


	public JedisPoolConfig startServer() {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(this.getMaxActive());
		poolConfig.setMaxIdle(this.getMaxIdle());
		poolConfig.setMinIdle(this.getMinIdle());
		poolConfig.setMaxWaitMillis(this.getMaxWait());
		poolConfig.setTestOnCreate(true);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);

		return poolConfig ;
	}
}