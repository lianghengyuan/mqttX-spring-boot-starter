package io.github.lianghengyuan.mqttx.exception;

@Deprecated
public class MsgHandlerIsNull extends BaseException{
    MsgHandlerIsNull(String code, Object[] args, String defaultMessage) {
        super(code, args, defaultMessage);
    }
}
