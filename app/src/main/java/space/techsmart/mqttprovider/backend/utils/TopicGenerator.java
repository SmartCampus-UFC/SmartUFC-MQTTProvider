package space.techsmart.mqttprovider.backend.utils;

public final class TopicGenerator {
    
    public static String getSensorPublishTopic(String apiKey, String deviceId) {
        return "/"+apiKey+"/"+deviceId+"/attrs";
    }

    public static String getActuatorSubscribeTopic(String apiKey, String deviceId) {
        return "/"+apiKey+"/"+deviceId+"/cmd";
    }

    public static String getCommandPublishTopic(String apiKey, String deviceId) {
        return "/"+apiKey+"/"+deviceId+"/cmd";
    }


}
