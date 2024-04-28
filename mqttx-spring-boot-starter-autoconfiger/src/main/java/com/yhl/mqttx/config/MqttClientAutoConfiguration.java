package com.yhl.mqttx.config;

import com.yhl.mqttx.MqttProducer;
import com.yhl.mqttx.event.DispatchMqttMessageListener;
import com.yhl.mqttx.event.MqttXCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(MqttClientProperties.class)
@ComponentScan("com.yhl.mqttx")
public class MqttClientAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttClientAutoConfiguration.class);


    @Autowired
    private MqttClientProperties mqttClientProperties;

    @Autowired
    private DispatchMqttMessageListener dispatchMqttMessageListener;

    @Autowired
    private MqttXCallback mqttXCallback;


    @Bean
    public MqttProducer mqttProducer() {
        MqttProducer mqttProducer = new MqttProducer();
        mqttProducer.setMqttClient(mqttClient());
        mqttProducer.setDefaultQOS(mqttClientProperties.getProducer().getQOS());
        mqttProducer.setDefaultRetained(mqttClientProperties.getProducer().isRetained());
        mqttProducer.setDefaultTopic(mqttClientProperties.getProducer().getDefaultTopic());
        return mqttProducer;
    }

    public MqttClient mqttClient() {
        try {
            MqttClient mqttClient = new MqttClient(mqttClientProperties.getServerURI(),mqttClientProperties.getClientId(),mqttClientPersistence());
            mqttClient.setManualAcks(false);
            mqttClient.setCallback(mqttXCallback);
            mqttClient.connect(mqttConnectOptions());
            for (String topic : mqttClientProperties.getConsumerTopics()) {
                mqttClient.subscribe(topic,dispatchMqttMessageListener);
            }
            return mqttClient;
        } catch (MqttException e) {
            log.error("mqtt error: {}",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(mqttClientProperties.getUsername());
        mqttConnectOptions.setPassword(mqttClientProperties.getPassword().toCharArray());
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(mqttClientProperties.getConnectionTimeout());
        mqttConnectOptions.setKeepAliveInterval(mqttClientProperties.getKeepAliveInterval());
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        return mqttConnectOptions;
    }

    public MqttClientPersistence mqttClientPersistence() {
        return new MemoryPersistence();
    }
}
