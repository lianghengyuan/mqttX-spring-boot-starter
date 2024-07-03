package io.github.lianghengyuan.mqttx.exception;

public class UsernameIsNullOrEmpty extends BaseException{

    public UsernameIsNullOrEmpty() {
        super("用户名不能为null或者空字符串");
    }
}
