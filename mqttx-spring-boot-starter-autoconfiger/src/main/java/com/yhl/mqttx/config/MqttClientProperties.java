package com.yhl.mqttx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ConfigurationProperties(prefix = "mqtt.client")
class MqttClientProperties {

    private String username;

    private String password;

    private String serverURI;

    private String clientId;

    private int KeepAliveInterval;

    private int connectionTimeout;

    private List<String> consumerTopics;

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

    public String getServerURI() {
        return serverURI;
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getKeepAliveInterval() {
        return KeepAliveInterval;
    }

    public void setKeepAliveInterval(int keepAliveInterval) {
        KeepAliveInterval = keepAliveInterval;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public List<String> getConsumerTopics() {
        return consumerTopics;
    }

    public void setConsumerTopics(List<String> consumerTopics) {
        this.consumerTopics = consumerTopics;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    static class Producer {
        private int QOS = 0;

        private boolean retained = false;

        //FIXME 设置默认topic
        private String defaultTopic = "123";

        public int getQOS() {
            return QOS;
        }

        public void setQOS(int QOS) {
            this.QOS = QOS;
        }

        public boolean isRetained() {
            return retained;
        }

        public void setRetained(boolean retained) {
            this.retained = retained;
        }

        public String getDefaultTopic() {
            return defaultTopic;
        }

        public void setDefaultTopic(String defaultTopic) {
            this.defaultTopic = defaultTopic;
        }

        @Override
        public String toString() {
            return "Producer{" +
                    "QOS=" + QOS +
                    ", retained=" + retained +
                    ", defaultTopic='" + defaultTopic + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MqttClientProperties{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", serverURI='" + serverURI + '\'' +
                ", clientId='" + clientId + '\'' +
                ", KeepAliveInterval=" + KeepAliveInterval +
                ", connectionTimeout=" + connectionTimeout +
                ", consumerTopics=" + consumerTopics +
                ", producer=" + producer.toString() +
                '}';
    }
}
