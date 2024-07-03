package io.github.lianghengyuan.mqttx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = "mqtt")
public class MqttClientProperties {

    private List<MqttConnectOptionsProperties> mqttConnectOptionsPropertiesList;

    public List<MqttConnectOptionsProperties> getMqttConnectOptionsPropertiesList() {
        return mqttConnectOptionsPropertiesList;
    }

    public void setMqttConnectOptionsPropertiesList(List<MqttConnectOptionsProperties> mqttConnectOptionsPropertiesList) {
        this.mqttConnectOptionsPropertiesList = mqttConnectOptionsPropertiesList;
    }

    public static class MqttConnectOptionsProperties {

        private List<String> serveruris;

        private boolean automaticreconnect = true;

        private Integer connectionTimeout = 30;

        private boolean cleansession = false;

        private Integer keepaliveinterval = 60;

        private Integer maxinflight = 10;

        private String username;

        private String password;

        private String clientid;

        private List<String> topics;

        private boolean manualacks = false;

        private List<String> tags = Arrays.asList("MsgAction");

        public List<String> getServeruris() {
            return serveruris;
        }

        public void setServeruris(List<String> serveruris) {
            this.serveruris = serveruris;
        }

        public boolean isAutomaticreconnect() {
            return automaticreconnect;
        }

        public void setAutomaticreconnect(boolean automaticreconnect) {
            this.automaticreconnect = automaticreconnect;
        }

        public Integer getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(Integer connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public boolean isCleansession() {
            return cleansession;
        }

        public void setCleansession(boolean cleansession) {
            this.cleansession = cleansession;
        }

        public Integer getKeepaliveinterval() {
            return keepaliveinterval;
        }

        public void setKeepaliveinterval(Integer keepaliveinterval) {
            this.keepaliveinterval = keepaliveinterval;
        }

        public Integer getMaxinflight() {
            return maxinflight;
        }

        public void setMaxinflight(Integer maxinflight) {
            this.maxinflight = maxinflight;
        }

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

        public String getClientid() {
            return clientid;
        }

        public void setClientid(String clientid) {
            this.clientid = clientid;
        }

        public List<String> getTopics() {
            return topics;
        }

        public void setTopics(List<String> topics) {
            this.topics = topics;
        }

        public boolean isManualacks() {
            return manualacks;
        }

        public void setManualacks(boolean manualacks) {
            this.manualacks = manualacks;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }

}
