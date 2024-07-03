package io.github.lianghengyuan.mqttx.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;

public interface MqttDispatcher {
   void dispatcher(String topic, MqttMessage message) throws IOException;
}
