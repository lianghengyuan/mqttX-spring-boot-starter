package io.github.lianghengyuan.mqttx.exception;

@Deprecated
public class MsgActionIsEmpty extends BaseException{


    MsgActionIsEmpty(String code, Object[] args, String defaultMessage) {
        super(code, args, defaultMessage);
    }


}
