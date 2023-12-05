package space.techsmart.mqttprovider.backend.utils;

public enum SmartSpecSensorType {

    TEMPERATURE, DOOR, WIFI;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

}
