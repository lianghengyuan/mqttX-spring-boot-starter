package com.yhl.mqttx.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;

public interface MqttService {
    void processMessages(String topic, MqttMessage message) throws IOException;
}
