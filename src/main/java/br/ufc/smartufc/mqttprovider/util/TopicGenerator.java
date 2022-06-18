package br.ufc.smartufc.mqttprovider.util;

public final class TopicGenerator {
    
    public static String getPublishTopic(String apiKey, String deviceId) {
        return "/"+apiKey+"/"+deviceId+"/attrs";
    }
     
    public static String getSubscribeTopic(String apiKey, String deviceId) {
        return "/"+apiKey+"/"+deviceId+"/cmd";
    }
}
