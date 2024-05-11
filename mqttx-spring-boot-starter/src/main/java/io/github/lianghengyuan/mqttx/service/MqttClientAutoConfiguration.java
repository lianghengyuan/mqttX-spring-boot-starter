package io.github.lianghengyuan.mqttx.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass({MqttProducer.class})
@EnableConfigurationProperties(MqttClientProperties.class)
@ComponentScan("io.github.lianghengyuan")
class MqttClientAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MqttClientAutoConfiguration.class);


    @Autowired
    private MqttClientProperties mqttClientProperties;

    @Autowired
    private DispatchMqttMessageListener dispatchMqttMessageListener;

    @Autowired
    private MqttXCallback mqttXCallback;


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty({"mqtt.serveruri"})
    public MqttProducer mqttXProducer() {
        MqttProducer mqttProducer = new MqttProducer();
        mqttProducer.setMqttClient(mqttClient());
        mqttProducer.setDefaultQOS(mqttClientProperties.getProducer().getQos());
        mqttProducer.setDefaultRetained(mqttClientProperties.getProducer().isRetained());
        mqttProducer.setDefaultTopic(mqttClientProperties.getProducer().getDefaulttopic());
        return mqttProducer;
    }

    MqttClient mqttClient() {
        try {
            MqttClient mqttClient = new MqttClient(mqttClientProperties.getServeruri(),mqttClientProperties.getClientid(),mqttClientPersistence());
            mqttClient.setManualAcks(false);
            mqttClient.setCallback(mqttXCallback);
            mqttClient.connect(mqttConnectOptions());
            for (String topic : mqttClientProperties.getConsumertopics()) {
                mqttClient.subscribe(topic,dispatchMqttMessageListener);
            }
            return mqttClient;
        } catch (MqttException e) {
            log.error("mqtt error: {}",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(mqttClientProperties.getUsername());
        mqttConnectOptions.setPassword(mqttClientProperties.getPassword().toCharArray());
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(mqttClientProperties.getConnectiontimeout());
        mqttConnectOptions.setKeepAliveInterval(mqttClientProperties.getKeepaliveinterval());
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        return mqttConnectOptions;
    }

   MqttClientPersistence mqttClientPersistence() {
        return new MemoryPersistence();
    }
}
