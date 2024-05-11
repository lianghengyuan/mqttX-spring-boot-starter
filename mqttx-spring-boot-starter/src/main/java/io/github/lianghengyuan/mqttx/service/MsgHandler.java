package io.github.lianghengyuan.mqttx.service;

public interface MsgHandler {
    void process(String jsonMessage);
}
