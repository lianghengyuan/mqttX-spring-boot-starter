package io.github.lianghengyuan.mqttx.service;

public interface MsgHandler {
    void process(String topic,String jsonMessage);
}
