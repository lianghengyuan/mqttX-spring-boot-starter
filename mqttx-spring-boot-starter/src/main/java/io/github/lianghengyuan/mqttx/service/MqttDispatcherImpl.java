package io.github.lianghengyuan.mqttx.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;


@Component
class MqttDispatcherImpl implements MqttDispatcher {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private MsgHandlerContext msgHandlerContext;

    private static final Logger log = LoggerFactory.getLogger(MqttDispatcherImpl.class);

    @Override
    public void dispatcher(String topic, MqttMessage message) throws IOException {

        String jsonMessage = new String(message.getPayload());

        MsgHandler topicHandler = msgHandlerContext.getTopicHandler(topic);
        if (topicHandler != null) {
            topicHandler.process(topic, jsonMessage);
            log.debug("处理Topic为{}的消息",topic);
        }

        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        String msgAction = jsonNode.get("msgAction").asText().trim();

        if (!StringUtils.hasLength(msgAction)) return;

        MsgHandler msgHandler = msgHandlerContext.getMsgActionHandler(msgAction);
        if (msgHandler != null) {
            msgHandler.process(topic,jsonMessage);
            log.debug("处理MsgAction为{}的消息",msgAction);
        }

    }
}
