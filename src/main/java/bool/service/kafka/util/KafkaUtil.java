/**
 * 
 */
package bool.service.kafka.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka工具类
 * @author wangw
 */
@Component
public class KafkaUtil {
	private static KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired(required=true)
	public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
		KafkaUtil.kafkaTemplate = kafkaTemplate;
	}
	
	public static void send(String topic, String data) {
		kafkaTemplate.send(topic, data);
	}
	
	public static void send(String topic, String key, String data) {
		kafkaTemplate.send(topic, key, data);
	}

}