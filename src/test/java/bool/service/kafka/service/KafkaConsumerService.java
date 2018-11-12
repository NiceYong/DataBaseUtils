/**
 * 
 */
package bool.service.kafka.service;

import bool.service.kafka.listener.KafkaMessageInterface;
import bool.service.kafka.server.KafkaConsumerServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Kafka消费实现继承 KafkaConsumerServer
 * @author xyx
 */
@Configuration
@EnableKafka
public class KafkaConsumerService extends KafkaConsumerServer {

    /**
     * kafka 消费监听实现方式
     * @return
     */
    @Bean
    public KafkaMessageInterface kafkaListener() {
        return new KafkaTestService();
    }

}