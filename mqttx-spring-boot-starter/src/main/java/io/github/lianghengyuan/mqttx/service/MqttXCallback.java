package io.github.lianghengyuan.mqttx.service;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * FiXME 此类并未起作用
 */
@Component
class MqttXCallback implements MqttCallbackExtended {
    private static final Logger log = LoggerFactory.getLogger(MqttXCallback.class);
    private static int reconnectNumber = 1;
    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        if (reconnect) {
            log.debug("{}重新连接{}次 ", serverURI, reconnectNumber);
            reconnectNumber++;
        }else {
            log.debug("{}连接成功",serverURI);
            reconnectNumber = 1;
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.error("mqtt连接丢失:",throwable);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println(String.format("收到主题为{}的消息：{}",topic, mqttMessage.getPayload().toString()));
        log.info("收到主题为{}的消息：{}",topic, mqttMessage.getPayload().toString());
        log.debug("收到主题为{}的消息：{}",topic, mqttMessage.getPayload().toString());

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.debug("消息已经到达{}",iMqttDeliveryToken.isComplete());
    }

}
