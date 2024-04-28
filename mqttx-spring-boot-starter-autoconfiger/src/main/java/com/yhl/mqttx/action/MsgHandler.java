package com.yhl.mqttx.action;

public interface MsgHandler {
    void process(String jsonMessage);
}
