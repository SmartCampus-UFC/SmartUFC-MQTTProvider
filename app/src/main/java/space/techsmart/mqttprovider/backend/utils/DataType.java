package space.techsmart.mqttprovider.backend.utils;

public enum DataType {

    INT, FLOAT, BOOLEAN, CHAR;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

}
