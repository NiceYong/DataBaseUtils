/**
 * 
 */
package bool.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Kafka消费者属性
 * @author wangw
 */
@Data
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaBoolProperties {

//	集群IP端口
	private String kafkaServers;

//	是否自动提交
	private boolean consumerEnableAutoCommit;

//	超时时间
	private String consumerSessionTimeout;

//	获取提交个数
	private String consumerAutoCommitInterval;

//	消费方式：earliest最前消费，latest最新消费
	private String consumerAutoCommitOffsetReset;

//	消费者组
	private String consumerGroupId;

//	消费者个数
	private int consumerConcurrency;


	private int producerRetries;

	private int producerBatchSize;

	private int producerLinger;

	private int producerBufferMemory;

//	topic名称
	private String producerTopic;





}