package io.github.lianghengyuan.mqttx.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MqttProducer {

    private static final Logger log = LoggerFactory.getLogger(MqttProducer.class);

    private static Map<String, MqttClient> mqttClientMap = new HashMap<>(64);


    public void sendAll(String topic,String payload, int qos, boolean retained) {
        mqttClientMap.forEach((key,value) -> {
            try {
                value.publish(topic, payload.getBytes(StandardCharsets.UTF_8), qos, retained);
            } catch (MqttException e) {
                log.error("主题{}消息:{},发送失败，失败原因",topic, payload, e);
            }
        });
    }

    public void sendAll(String topic,String payload, int qos) {
       sendAll(topic,payload,qos,false);
    }

    public void sendAll(String topic,String payload) {
        sendAll(topic,payload,0,false);
    }

    public void send(String clientId, String topic, String payload, int qos, boolean retained) {
        try {
            mqttClientMap.get(clientId).publish(topic, payload.getBytes(StandardCharsets.UTF_8), qos, retained);
        } catch (MqttException e) {
            log.error("主题{}消息:{},发送失败，失败原因",topic, payload, e);
        }
    }

    public void send(String clientId,String topic, String payload, int qos) {
        send(clientId, topic, payload, qos, false);
    }

    public void send(String clientId, String topic, String payload) {
        send(clientId, topic, payload,0);
    }


    public<T extends Object> void sendByJson(String clientId, String topic, T msg, int qos, boolean retained) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(msg);
        send(clientId,topic,valueAsString, qos, retained);
    }

    public<T extends Object> void sendByJson(String clientId, String topic, T msg, int qos) throws JsonProcessingException {
        sendByJson(clientId,topic, msg, qos, false);
    }

    public<T extends Object> void sendByJson(String clientId, String topic, T msg) throws JsonProcessingException {
        sendByJson(clientId, topic, msg, 0, false);
    }

    public void subscribe(String clientId, String topic) throws MqttException {
        mqttClientMap.get(clientId).subscribe(topic);
    }

    public void subscribe(String clientId, String topic, IMqttMessageListener iMqttMessageListener) throws MqttException {
        mqttClientMap.get(clientId).subscribe(topic, iMqttMessageListener);
    }

    public void subscribe(String clientId,List<String> topicList) throws MqttException {
        mqttClientMap.get(clientId).subscribe(topicList.toArray(new String[topicList.size()]));
    }
    //TODO 补全所有subscribe方法，包括对于List、map等变量类型的支持。

    public void subscribe(String clientId, String[] topic) throws MqttException {
        mqttClientMap.get(clientId).subscribe(topic);
    }

    public void subscribe(String clientId, String[] topic, IMqttMessageListener[] iMqttMessageListener) throws MqttException {
        mqttClientMap.get(clientId).subscribe(topic,iMqttMessageListener);
    }

    public void subscribe(String clientId, String topic, int qos, IMqttMessageListener iMqttMessageListener) throws MqttException {
        mqttClientMap.get(clientId).subscribe(topic, qos, iMqttMessageListener);
    }

    public void subscribe(String clientId, String topic, int qos) throws MqttException {
        mqttClientMap.get(clientId).subscribe(topic,qos);
    }
    public void setMqttClient(MqttClient mqttClient) {
        mqttClientMap.put(mqttClient.getClientId(),mqttClient);
    }




    public void disconnect(String clientId) throws MqttException {
        MqttClient mqttClient = mqttClientMap.get(clientId);
        Objects.requireNonNull(mqttClient);
        if (mqttClient.isConnected()){
            mqttClient.disconnect();
        }
    }

    public void reconnect(String clientId) throws MqttException {
        MqttClient mqttClient = mqttClientMap.get(clientId);
        Objects.requireNonNull(mqttClient);
        if (!mqttClient.isConnected()){
            mqttClient.reconnect();
        }
    }

    public void close(String clientId) throws MqttException {
        MqttClient mqttClient = mqttClientMap.get(clientId);
        Objects.requireNonNull(mqttClient);
        if (mqttClient.isConnected()){
            mqttClient.disconnect();
        }
        mqttClient.close();
    }
}
