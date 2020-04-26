package br.ufba.dcc.wiser.soft_iot.auto_irrigation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.ufba.dcc.wiser.soft_iot.entities.Actuator;
import br.ufba.dcc.wiser.soft_iot.entities.Device;
import br.ufba.dcc.wiser.soft_iot.entities.Sensor;
import br.ufba.dcc.wiser.soft_iot.entities.SensorData;
import br.ufba.dcc.wiser.soft_iot.local_storage.LocalDataController;
import br.ufba.dcc.wiser.soft_iot.local_storage.MqttPublisherController;
import br.ufba.dcc.wiser.soft_iot.mapping_devices.Controller;



public class IrrigationNode {
	
	private Controller fotDevices;
	private LocalDataController localDataController;
	private MqttPublisherController publisher;
	private Device devAgroDigital;
	private Device devSonoff;
	private Sensor tempSensor;
	private Sensor humidSensor;
	private Sensor lightSensor;
	private Actuator irrigActuator;
	private Date initialDate;
	
	public void init(){
		System.out.println("Hello Again!");
		this.initialDate = new Date(120,3,5);
		
		this.devAgroDigital = fotDevices.getDeviceById("agrodigital");
		this.devSonoff = fotDevices.getDeviceById("sc01");
		
		this.tempSensor = devSonoff.getSensorbySensorId("temperatureSensor");
		this.humidSensor = devSonoff.getSensorbySensorId("humiditySensor");
		this.lightSensor = devSonoff.getSensorbySensorId("luminositySensor");
		this.irrigActuator = devAgroDigital.getActuatorbyActuatorId("irrigation_actuator");
		
		
	}
	
	public void irrigate(){
		System.out.println("Starting irrigation procedure...");
	
		Date currentDate = new Date();
		int day = (int) TimeUnit.DAYS.convert((currentDate.getTime() - this.initialDate.getTime()), TimeUnit.MILLISECONDS);
		double kc = CropCoefficient.strawberryKc(day);
		System.out.println("Kc: " + kc);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, -1);
		Date dateBefore1Day = cal.getTime();
		
		List<SensorData> minTempData = localDataController.getSensorDataByAggregationStatusDateAndFunction(devSonoff, this.tempSensor, 1, dateBefore1Day, "MinAggregationFunction");
		List<SensorData> maxTempData = localDataController.getSensorDataByAggregationStatusDateAndFunction(devSonoff, this.tempSensor, 1, dateBefore1Day, "MaxAggregationFunction");
		List<SensorData> humdData = localDataController.getSensorDataByAggregationStatusDateAndFunction(devSonoff, this.humidSensor, 1, dateBefore1Day, "AverageAggregationFunction");
		List<SensorData> lightData = localDataController.getSensorDataByAggregationStatusDateAndFunction(devSonoff, this.lightSensor, 1, dateBefore1Day, "AverageAggregationFunction");
		
		float Tmax = Float.parseFloat(Util.maxListSensorData(maxTempData).getValue());
		float Tmin = Float.parseFloat(Util.minListSensorData(minTempData).getValue());
		float RHmean = Float.parseFloat(Util.averageListSensorData(humdData).getValue());
		float lux = Float.parseFloat(Util.averageListSensorData(lightData).getValue());
		float Rn = CropEvotranspiration.luxToMJperDay(lux);
		
		System.out.println("Min Temperature: " + Tmin);
		System.out.println("Max Temperature: " + Tmax);
		System.out.println("Avg Humidity: " + RHmean);
		System.out.println("Avg Lux: " + lux);
		System.out.println("Avg Radiation: " + Rn);
		
		float eTo = CropEvotranspiration.FAOPenmanMonteith(Rn,Tmin, Tmax, RHmean);
		Integer  actuatorTime = (int) Math.round(eTo*kc*1000/2.12);
		System.out.println("eTo: " + eTo);
		
		System.out.println("eTc: " + eTo*kc);
		System.out.println("ml: " + eTo*kc*1000);
		System.out.println("Actuator Time: " +  actuatorTime);
		publisher.sendPostActuator(devAgroDigital, irrigActuator, actuatorTime.toString());
	}

	public void setFotDevices(Controller fotDevices) {
		this.fotDevices = fotDevices;
	}

	public void setLocalDataController(LocalDataController localDataController) {
		this.localDataController = localDataController;
	}
	
	public void setPublisher(MqttPublisherController publisher) {
		this.publisher = publisher;
	}
	
	public void destroy(){
		System.out.println("Destroyed!");
	}
	
	

}
