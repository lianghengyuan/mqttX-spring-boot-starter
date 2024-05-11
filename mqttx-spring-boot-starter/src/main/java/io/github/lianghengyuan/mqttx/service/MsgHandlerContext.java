package io.github.lianghengyuan.mqttx.service;

interface MsgHandlerContext {

    MsgHandler getHandler(String msgAction);
}
