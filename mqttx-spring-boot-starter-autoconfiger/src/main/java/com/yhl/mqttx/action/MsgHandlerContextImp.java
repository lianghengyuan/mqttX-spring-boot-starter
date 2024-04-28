package com.yhl.mqttx.action;

import com.yhl.mqttx.annotation.MsgAction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MsgHandlerContextImp implements ApplicationContextAware, MsgHandlerContext {

//    private ApplicationContext applicationContext;

    private Map<String, MsgHandler> msgHandlerMap = new HashMap<>();



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, MsgHandler> map = applicationContext.getBeansOfType(MsgHandler.class);
        map.values().stream().forEach(msgHandler -> {
            String msgAction = msgHandler.getClass().getAnnotation(MsgAction.class).value();
            msgHandlerMap.put(msgAction, msgHandler);
        });
    }

    @Override
    public MsgHandler getHandler(String msgAction) {
        return msgHandlerMap.get(msgAction);
    }
}
