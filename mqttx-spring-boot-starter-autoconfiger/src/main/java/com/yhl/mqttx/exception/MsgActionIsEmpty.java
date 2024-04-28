package com.yhl.mqttx.exception;

public class MsgActionIsEmpty extends BaseException{


    public MsgActionIsEmpty(String code, Object[] args, String defaultMessage) {
        super(code, args, defaultMessage);
    }
}
