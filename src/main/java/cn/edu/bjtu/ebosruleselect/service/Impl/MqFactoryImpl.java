package cn.edu.bjtu.ebosruleselect.service.Impl;

import cn.edu.bjtu.ebosruleselect.service.MqConsumer;
import cn.edu.bjtu.ebosruleselect.service.MqFactory;
import cn.edu.bjtu.ebosruleselect.service.MqGetIp;
import cn.edu.bjtu.ebosruleselect.service.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MqFactoryImpl implements MqFactory {
    @Autowired
    KafkaTemplate kafkaTemplate;
    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;
    @Value("${mq}")
    private String name;

    @Override
    public MqProducer createProducer(){
        switch (this.name){
            case "activemq" :
                return new ActiveMqProducerImpl(jmsMessagingTemplate);
            case "kafka" :
                return new KafkaProducerImpl(kafkaTemplate);
            default:
                return new ActiveMqProducerImpl(jmsMessagingTemplate);
        }
    }

    @Override
    public MqConsumer createConsumer(String topic){
        switch (this.name){
            case "activemq" :
                return new ActiveMqConsumerImpl(topic);
            case "kafka" :
                return new KafkaConsumerImpl(topic);
            default:
                return new ActiveMqConsumerImpl(topic);
        }
    }

    @Override
    public MqGetIp createGetIp(String ip, String topic) {
        return new ActiveMqGetIpImpl(ip,topic);
    }
}
