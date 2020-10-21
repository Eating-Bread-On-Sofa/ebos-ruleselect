package cn.edu.bjtu.ebosruleselect.service;

public interface MqProducer {
    void publish(String topic, String message);
}
