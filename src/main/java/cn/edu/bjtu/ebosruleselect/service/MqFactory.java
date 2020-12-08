package cn.edu.bjtu.ebosruleselect.service;

public interface MqFactory {
    MqProducer createProducer();
    MqConsumer createConsumer(String topic);
    MqGetIp createGetIp(String ip,String topic);
}
