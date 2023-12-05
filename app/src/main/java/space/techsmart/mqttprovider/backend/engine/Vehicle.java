package space.techsmart.mqttprovider.backend.engine;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Vehicle {
    private String id;
    private double x;
    private double y;
    private boolean isPresent;
    private int step;
    String xmlFile;
    private int timestepCounter; // Internal timestep counter

    public Vehicle(String id, int startTimestep, int step, String xmlFile) {
        this.id = id;
        this.isPresent = false;
        this.timestepCounter = startTimestep;
        this.xmlFile = xmlFile;
        this.step = step;
        updateCoordinates();
    }

    public void updateCoordinates() {
        this.isPresent = false;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            // Load the XML file using the xmlFile directory string
            File xmlFile = new File(this.xmlFile);
            
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList timestepNodes = doc.getElementsByTagName("timestep");
            for (int i = 0; i < timestepNodes.getLength(); i++) {
                Node timestepNode = timestepNodes.item(i);
                if (timestepNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element timestepElement = (Element) timestepNode;
                    double currentTimestep = Double.parseDouble(timestepElement.getAttribute("time"));

                    if (currentTimestep == timestepCounter) {
                        NodeList vehicleNodes = timestepElement.getElementsByTagName("vehicle");
                        for (int j = 0; j < vehicleNodes.getLength(); j++) {
                            Node vehicleNode = vehicleNodes.item(j);
                            if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element vehicleElement = (Element) vehicleNode;
                                String currentId = vehicleElement.getAttribute("id");

                                if (currentId.equals(id)) {
                                    x = Double.parseDouble(vehicleElement.getAttribute("x"));
                                    y = Double.parseDouble(vehicleElement.getAttribute("y"));
                                    isPresent = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            this.timestepCounter += this.step;
            if(timestepCounter == 1092.0)
            	timestepCounter = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isPresent() {
        return isPresent;
    }
}