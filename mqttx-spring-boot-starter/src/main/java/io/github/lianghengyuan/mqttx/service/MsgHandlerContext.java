package io.github.lianghengyuan.mqttx.service;

interface MsgHandlerContext {

    MsgHandler getMsgActionHandler(String msgAction);

    MsgHandler getTopicHandler(String topic);
}
