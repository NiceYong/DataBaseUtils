/**
 * 
 */
package bool.service.kafka.server;

import bool.config.property.KafkaBoolProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka服务器
 * @author wangw
 */
@Configuration
@EnableKafka
public class KafkaConsumerServer {
    /**
     * Kafka属性
     */
    @Autowired
    private KafkaBoolProperties kafkaBoolProperties;


    private Map<String, Object> consumerConfigs(){
        Map<String, Object> propsMap = new HashMap<String, Object>(9);
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBoolProperties.getKafkaServers());
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, this.kafkaBoolProperties.isConsumerEnableAutoCommit());
        /* 自动确认offset的时间间隔  */
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, this.kafkaBoolProperties.getConsumerAutoCommitInterval());
        //max.poll.records条数据需要在在session.timeout.ms这个时间内处理完
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, this.kafkaBoolProperties.getConsumerSessionTimeout());
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaBoolProperties.getConsumerGroupId());
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.kafkaBoolProperties.getConsumerAutoCommitOffsetReset());
        //避免重复消费
        //一次从kafka中poll出来的数据条数
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, this.kafkaBoolProperties.getConsumerAutoCommitInterval());


        return propsMap;
    }
    
    private ConsumerFactory<String, String> consumerFactory(){
    	
    	
    	return new DefaultKafkaConsumerFactory<>(this.consumerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(this.consumerFactory());
        factory.setConcurrency(this.kafkaBoolProperties.getConsumerConcurrency());
        //设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        factory.getContainerProperties().setPollTimeout(15000);
        //设置提交偏移量的方式
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }
    

}