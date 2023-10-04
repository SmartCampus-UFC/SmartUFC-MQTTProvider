package br.ufc.smartufc.mqttprovider.util.MobileSensor;

public class Test {
	public static void main(String[] args) {
        Vehicle vehicle0 = new Vehicle("f_0.0",20,1,"src/main/java/br/ufc/smartufc/mqttprovider/util/MobileSensor/ufc-cenario/osmWithStop.xml");
        System.out.println(vehicle0.getX());
        System.out.println(vehicle0.getY());
        vehicle0.updateCoordinates();
        System.out.println(vehicle0.getX());
        System.out.println(vehicle0.getY());
    }

}
