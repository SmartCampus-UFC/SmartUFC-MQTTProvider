/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.smartufc.mqttprovider.util;

public class Param {
    // Metricas 
    public static int replication = 1;
    public static int uid = 1;
    public static int experiment_num = 1;
    public static int number_of_experiments = 8;
    public static int number_of_replications = 31; 
    public static int time_between_exp =1000; 
    public static double lambda=0;
    public static  int number_of_topics = 1;
    public static  int number_of_devices_Event = 5;
    public static  int qos = 0; // 0 - 1 - 2
	public static  long time_of_experiment= 210000; 
	public static  int eventDuration = 200000;// evento 2/3 metade do exp
    public static String address ="tcp://localhost:1883";
    public static final String topic = "default";
    public static int number_of_devices = 10000; 
    
}