package bool.service.kafka.service;

import bool.service.kafka.listener.KafkaMessageInterface;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

/**
 * <p></p>
 * <pre>
 *     author      xiaoyunxiang
 *     date        2018/6/29
 *     email       xyx@tjbool.com
 *     kafka 消费业务层 实现KafkaMessageListener接口
 * </pr>
 */
public class KafkaTestService implements KafkaMessageInterface {

    /**
     * 执行手动提交
     * @param records
     * @param ack
     */
    @Override
    public void handleMessage(List<ConsumerRecord<?, ?>> records, Acknowledgment ack){

        try {
            for(ConsumerRecord<?, ?> record : records){
                System.out.println(record.value().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            ack.acknowledge();//手动提交偏移量
        }
    }

}
