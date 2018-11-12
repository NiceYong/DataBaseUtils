/**
 * 
 */
package bool.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Netty属性
 * @author wangw
 */
@Data
@Component
public class NettyProperties {

	@Value("${netty.client.host}")
	private String host;

	@Value("${netty.client.targetPort}")
	private int targetPort;

	@Value("${netty.client.port}")
	private int port;

	@Value("${netty.client.userName}")
	private String userName;

	@Value("${netty.client.password}")
	private String password;

	@Value("${netty.client.initialDelay}")
	private long initialDelay;

	@Value("${netty.client.period}")
	private long period;

	@Value("${netty.client.channel.read-timeout}")
	private int channelReadTimeout;

	@Value("${netty.client.channel.write-timeout}")
	private int channelWriteTimeout;

	@Value("${netty.client.channel.all-timeout}")
	private int channelAllTimeout;

	@Value("${netty.pool.max-total}")
	private int maxTotal;

	@Value("${netty.pool.max-wait}")
	private int maxWait;


}