/**
 * 
 */
package bool.service.kafka.server;

import bool.config.property.KafkaBoolProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka服务器
 * @author wangw
 */
@Configuration
@EnableKafka
public class KafkaProducerServer {
	/**
	 * Kafka属性
	 */
	@Autowired
	private KafkaBoolProperties kafkaBoolProperties;
    
    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBoolProperties.getKafkaServers());
        props.put(ProducerConfig.RETRIES_CONFIG, this.kafkaBoolProperties.getProducerRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, this.kafkaBoolProperties.getProducerBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, this.kafkaBoolProperties.getProducerLinger());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, this.kafkaBoolProperties.getProducerBufferMemory());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
    
    private ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(this.producerConfigs());
    }
    
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(this.producerFactory());
    }
}