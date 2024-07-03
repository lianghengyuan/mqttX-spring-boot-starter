package io.github.lianghengyuan.mqttx.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
class MsgHandlerContextImp implements ApplicationContextAware, MsgHandlerContext {

    private static final String TAG_PREFIX = "TAG";

    private static final String TOPIC_PREFIX = "TOPIC";

    private Map<String, MsgHandler> msgHandlerMap = new HashMap<>(64);



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, MsgHandler> map = applicationContext.getBeansOfType(MsgHandler.class);
        map.values().stream().forEach(msgHandler -> {
//            String msgAction = msgHandler.getClass().getAnnotation(MsgAction.class).value().trim();
//            msgHandlerMap.put(msgAction, msgHandler);
            MsgAction msgActionAnnotation = msgHandler.getClass().getAnnotation(MsgAction.class);
            if (msgActionAnnotation != null) {
                String msgAction = msgActionAnnotation.value().trim();
                msgHandlerMap.put(TAG_PREFIX+msgAction, msgHandler);
            }
            Topic topicAnnotation = msgHandler.getClass().getAnnotation(Topic.class);
            if (topicAnnotation != null) {
                String topic = msgActionAnnotation.value().trim();
                msgHandlerMap.put(TOPIC_PREFIX+topic, msgHandler);
            }

        });
    }

    @Override
    public MsgHandler getMsgActionHandler(String msgAction) {
        return msgHandlerMap.get(TAG_PREFIX+msgAction);
    }

    @Override
    public MsgHandler getTopicHandler(String topic) {
        return msgHandlerMap.get(TOPIC_PREFIX+topic);
    }
}
