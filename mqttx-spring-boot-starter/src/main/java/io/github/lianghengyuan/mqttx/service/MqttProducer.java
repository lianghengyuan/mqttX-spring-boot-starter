package io.github.lianghengyuan.mqttx.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MqttProducer {

    private static final Logger log = LoggerFactory.getLogger(MqttProducer.class);

    private MqttClient mqttClient;

    private int defaultQOS;


    private boolean defaultRetained;


    private String defaultTopic;

    public void send(String topic, String playload, int qos, boolean retained) {
        try {
            mqttClient.publish(topic, playload.getBytes(StandardCharsets.UTF_8), qos, retained);
        } catch (MqttException e) {
            log.error("主题%s消息:%s,发送失败",topic,playload);
//            throw new RuntimeException(e);
        }
    }

    public void send(String topic, String playload, int qos) {
        send(topic, playload, qos, defaultRetained);
    }

    public void send(String topic, String playload) {
        send(topic, playload, defaultQOS);
    }

    private void send(String playload) {
        send(defaultTopic, playload);
    }

    public<T extends Object> void send(String topic, int qos, T msg) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(msg);
        send(topic,valueAsString,qos);
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void setDefaultQOS(int defaultQOS) {
        this.defaultQOS = defaultQOS;
    }

    public void setDefaultRetained(boolean defaultRetained) {
        this.defaultRetained = defaultRetained;
    }

    public void setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public void disconnect() throws MqttException {
        Objects.requireNonNull(mqttClient);
        if (mqttClient.isConnected()){
            mqttClient.disconnect();
        }
    }

    public void reconnect() throws MqttException {
        Objects.requireNonNull(this.mqttClient);
        if (!mqttClient.isConnected()){
            mqttClient.reconnect();
        }
    }

    public void close() throws MqttException {
        disconnect();
        mqttClient.close();
    }
}
