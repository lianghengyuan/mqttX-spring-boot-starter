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
class MqttServiceImpl implements MqttService {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MsgHandlerContext msgHandlerContext;

    private static final Logger log = LoggerFactory.getLogger(MqttServiceImpl.class);

    @Override
    public void processMessages(String topic, MqttMessage message) throws IOException {
        String jsonMessage = new String(message.getPayload());
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        String msgAction = jsonNode.get("msgAction").asText();
        if (!StringUtils.hasLength(msgAction)) {
            throw new MsgActionIsEmpty("1001",new Object[]{topic, message},"消息处理标签解析失败");
        }
        MsgHandler msgHandler = msgHandlerContext.getHandler(msgAction);
        if (msgHandler == null) {
            throw new MsgHandlerIsNull("1002",new Object[]{topic, message}, "没有与msgAction对应的msgHandler");
        }
        //FIXME 加上TOPIC
        msgHandler.process(topic,jsonMessage);
    }
}
