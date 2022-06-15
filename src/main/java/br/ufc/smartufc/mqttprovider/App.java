package br.ufc.smartufc.mqttprovider;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import br.ufc.smartufc.mqttprovider.code.MqttPublish;
import br.ufc.smartufc.mqttprovider.code.Param;
import br.ufc.smartufc.mqttprovider.model.TDSensor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {    	
    	String mosquitto_host = System.getenv("MOSQUITTO_HOST");
    	String mosquitto_port = System.getenv("MOSQUITTO_PORT");
    	Param.address = "tcp://"+mosquitto_host+":"+mosquitto_port;
		Param.name_experiment = "gen_site_quixada";
		//Param.path = "D:\\Downloads\\";
		Param.time_of_experiment = 60000;
    	
    	CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();    	
    	Reader readerTDSensors = Files.newBufferedReader(Paths.get("csv/tdsensors.csv"));
    	//Reader readerEDSensors = Files.newBufferedReader(Paths.get("edsensors.csv"));
        CSVReader csvReaderTDSensors = new CSVReaderBuilder(readerTDSensors).withSkipLines(1).withCSVParser(parser).build();
        //CSVReader csvReaderEDSensors = new CSVReaderBuilder(readerEDSensors).withSkipLines(1).build();    	
    	
        List<String[]> tdSensors = csvReaderTDSensors.readAll();
        //List<String[]> edSensors = csvReaderEDSensors.readAll();
        
        TDSensor[] tdSensorArray = new TDSensor[tdSensors.size()];
        
        int index = 0; 
        TDSensor t = new TDSensor();
        for (String[] tdSensor : tdSensors) {
        	t.setType(tdSensor[0]);
        	t.setTopic(tdSensor[1]);
        	t.setPeriodicity(Integer.parseInt(tdSensor[2]));
        	t.setNumberOfDevices(Integer.parseInt(tdSensor[3]));
        	String[] array = new String[1];
    		array[0] = tdSensor[4];
    		t.setData(array);
    		String[] max = new String [1];
    		max[0] = tdSensor[5];
    		t.setMax(max);
    		String[] min = new String [1];
    		min[0] = tdSensor[6];
    		t.setMin(min);
    		
    		tdSensorArray[index] = t;
    		index++;
    		t = new TDSensor();
        }       	
        	
        System.out.println("RUN!! Sending to ip " + Param.address);
		//System.out.println("Experiment's name: " + Param.name_experiment + " time of exp: " + Param.time_of_experiment);		
        System.out.println("Time of experiment (in seconds): " + Param.time_of_experiment);
		
		MqttPublish mqttp = new MqttPublish();
		mqttp.setEventSensors(null);
		mqttp.setTimeSensors(tdSensorArray);
		Thread threadDoPdf = new Thread(mqttp);
        threadDoPdf.start();
        
        
    	/*Param.address = "tcp://10.0.200.2:1883";
		Param.name_experiment = "gen_site_quixada";
		//Param.path = "D:\\Downloads\\";
		Param.time_of_experiment = 60;
		
		TDSensor[] tdSensorArray = new TDSensor[2];
		
		//Criado o sensor temperatura
		TDSensor t = new TDSensor();		
		t.setType("t");
		System.out.println("tipo: " + t.getType());
		t.setTopic("temperature");
		System.out.println("topic: " + t.getTopic());
		t.setPeriodicity(3);
		System.out.println("periodicidade: " + t.getPeriodicity());
		t.setNumberOfDevices(1);
		System.out.println("number of devices: " + t.getNumberOfDevices());
		String[] array = new String[1];
		array[0] = "int";
		t.setData(array);
		System.out.println("data: " + t.getData()[0]);
		String[] max = new String [1];
		max[0] = "35";
		t.setMax(max);
		System.out.println("max: " + t.getMax()[0]);
		String[] min = new String [1];
		min[0] = "15";
		t.setMin(min);
		System.out.println("min: " + t.getMin()[0]);
		
		tdSensorArray[0] = t;
		  
		t = new TDSensor();
		
		t.setType("h");
		System.out.println("tipo: " + t.getType());
		t.setTopic("humidity");
		System.out.println("topic: " + t.getTopic());
		t.setPeriodicity(3);
		System.out.println("periodicidade: " + t.getPeriodicity());
		t.setNumberOfDevices(1);
		System.out.println("number of devices: " + t.getNumberOfDevices());
		array = new String[1];
		array[0] = "int";
		t.setData(array);
		System.out.println("data: " + t.getData()[0]);
		max = new String [1];
		max[0] = "85";
		t.setMax(max);
		System.out.println("max: " + t.getMax()[0]);
		min = new String [1];
		min[0] = "80";
		t.setMin(min);
		System.out.println("min: " + t.getMin()[0]);
		
		tdSensorArray[1] = t;
		
		System.out.println("RUN!! Sending to ip " + Param.address);
		System.out.println("Experiment's name: " + Param.name_experiment + " time of exp: " + Param.time_of_experiment);		
		
		MqttPublish mqttp = new MqttPublish();
		mqttp.setEventSensors(null);
		mqttp.setTimeSensors(tdSensorArray);
		Thread threadDoPdf = new Thread(mqttp);
        threadDoPdf.start();*/
	    
		
    }
}
