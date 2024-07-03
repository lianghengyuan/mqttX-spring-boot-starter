package io.github.lianghengyuan.mqttx.config;

import io.github.lianghengyuan.mqttx.dto.MqttProducer;
import io.github.lianghengyuan.mqttx.service.DispatchMqttMessageListener;
import io.github.lianghengyuan.mqttx.service.MqttProducerCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;


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
    private MqttProducerCallback mqttProducerCallback;


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "mqtt.enable", havingValue = "true")
    public MqttProducer mqttProducer() {

        List<MqttClientProperties.MqttConnectOptionsProperties> mqttConnectOptionsPropertiesList
                = mqttClientProperties.getMqttConnectOptionsPropertiesList();

        MqttProducer mqttProducer = new MqttProducer();

        mqttConnectOptionsPropertiesList.forEach(mqttConnectOptionsProperties -> mqttProducer.setMqttClient(mqttClient(mqttConnectOptionsProperties)));

        return mqttProducer;
    }

    private MqttClient mqttClient(MqttClientProperties.MqttConnectOptionsProperties mqttConnectOptionsProperties) {
        try {
            MqttConnectOptions mqttConnectOptions = mqttConnectOptions(mqttConnectOptionsProperties);

            MqttClient mqttClient = new MqttClient(mqttConnectOptionsProperties.getServeruris().get(0),mqttConnectOptionsProperties.getClientid(),mqttClientPersistence());
            mqttClient.setManualAcks(mqttConnectOptionsProperties.isManualacks());
            mqttClient.setCallback(mqttProducerCallback);
            mqttClient.connect(mqttConnectOptions);
            for (String topic : mqttConnectOptionsProperties.getTopics()) {
                mqttClient.subscribe(topic,dispatchMqttMessageListener);
            }
            return mqttClient;
        } catch (MqttException e) {
            log.error("mqtt error: {}",e.getMessage());
            return null;
        }
    }

    private MqttConnectOptions mqttConnectOptions(MqttClientProperties.MqttConnectOptionsProperties mqttConnectOptionsProperties) {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setServerURIs(mqttConnectOptionsProperties.getServeruris().toArray(new String[0]));
        mqttConnectOptions.setConnectionTimeout(mqttConnectOptionsProperties.getConnectionTimeout());
        mqttConnectOptions.setMaxInflight(mqttConnectOptionsProperties.getMaxinflight());
        mqttConnectOptions.setUserName(mqttConnectOptionsProperties.getUsername());
        mqttConnectOptions.setPassword(mqttConnectOptionsProperties.getPassword().toCharArray());
        mqttConnectOptions.setCleanSession(mqttConnectOptionsProperties.isCleansession());
        mqttConnectOptions.setAutomaticReconnect(mqttConnectOptionsProperties.isAutomaticreconnect());
        return mqttConnectOptions;
    }

   MqttClientPersistence mqttClientPersistence() {
        return new MemoryPersistence();
    }
}
