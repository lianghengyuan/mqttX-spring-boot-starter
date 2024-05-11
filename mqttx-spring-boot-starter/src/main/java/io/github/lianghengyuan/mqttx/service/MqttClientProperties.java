package io.github.lianghengyuan.mqttx.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ConfigurationProperties(prefix = "mqtt")
class MqttClientProperties {

    private String username;

    private String password;

    private String serveruri;

    private String clientid;

    private int keepaliveinterval;

    private int connectiontimeout;

    private List<String> consumertopics;

    private Producer producer;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServeruri() {
        return serveruri;
    }

    public void setServeruri(String serveruri) {
        this.serveruri = serveruri;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public int getKeepaliveinterval() {
        return keepaliveinterval;
    }

    public void setKeepaliveinterval(int keepaliveinterval) {
        this.keepaliveinterval = keepaliveinterval;
    }

    public int getConnectiontimeout() {
        return connectiontimeout;
    }

    public void setConnectiontimeout(int connectiontimeout) {
        this.connectiontimeout = connectiontimeout;
    }

    public List<String> getConsumertopics() {
        return consumertopics;
    }

    public void setConsumertopics(List<String> consumertopics) {
        this.consumertopics = consumertopics;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    static class Producer {
        private int qos = 0;

        private boolean retained = false;

        //FIXME 设置默认topic
        private String defaulttopic = "123";

        public int getQos() {
            return qos;
        }

        public void setQos(int qos) {
            this.qos = qos;
        }

        public boolean isRetained() {
            return retained;
        }

        public void setRetained(boolean retained) {
            this.retained = retained;
        }

        public String getDefaulttopic() {
            return defaulttopic;
        }

        public void setDefaulttopic(String defaulttopic) {
            this.defaulttopic = defaulttopic;
        }

        @Override
        public String toString() {
            return "Producer{" +
                    "qos=" + qos +
                    ", retained=" + retained +
                    ", defaulttopic='" + defaulttopic + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MqttClientProperties{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", serveruri='" + serveruri + '\'' +
                ", clientid='" + clientid + '\'' +
                ", keepaliveinterval=" + keepaliveinterval +
                ", connectiontimeout=" + connectiontimeout +
                ", consumertopics=" + consumertopics +
                ", producer=" + producer +
                '}';
    }
}
