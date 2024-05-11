package io.github.lianghengyuan.mqttx.service;

class MsgHandlerIsNull extends BaseException{
    MsgHandlerIsNull(String code, Object[] args, String defaultMessage) {
        super(code, args, defaultMessage);
    }
}
