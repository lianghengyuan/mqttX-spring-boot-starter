package com.yhl.mqttx.action;

public interface MsgHandlerContext {

    MsgHandler getHandler(String msgAction);
}
