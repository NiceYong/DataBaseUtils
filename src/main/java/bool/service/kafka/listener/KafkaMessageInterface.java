/**
 * 
 */
package bool.service.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

/**
 * Kafka消息监听器
 * @author
 */
public interface KafkaMessageInterface {

	/**
	 * kafka执行方法
	 * 打包前需要确定修改kafkaTopic
	 * @param records kafka通道内批量获取信息
	 * @param ack 手动提交
	 *
	 * 使用方式见 test包中   bool.service.kafka.service 测试类
     */
	@KafkaListener(topics={"maxu0701"},containerFactory = "kafkaListenerContainerFactory")
	public void handleMessage (List<ConsumerRecord<?, ?>> records, Acknowledgment ack);

}