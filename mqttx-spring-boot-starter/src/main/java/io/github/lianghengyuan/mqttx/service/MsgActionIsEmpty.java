package io.github.lianghengyuan.mqttx.service;

class MsgActionIsEmpty extends BaseException{


    MsgActionIsEmpty(String code, Object[] args, String defaultMessage) {
        super(code, args, defaultMessage);
    }
}
