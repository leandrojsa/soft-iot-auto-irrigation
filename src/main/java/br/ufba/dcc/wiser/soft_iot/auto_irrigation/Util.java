package br.ufba.dcc.wiser.soft_iot.auto_irrigation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufba.dcc.wiser.soft_iot.entities.SensorData;

public final class Util {
	
	public static SensorData averageListSensorData(List<SensorData> listSensorData){
		List<SensorData> resultList = new ArrayList<SensorData>();
		Date startDate =  listSensorData.get(0).getStartTime();
		Date endDate =  startDate;
		float average = 0;
		for(SensorData sensorData : listSensorData){
			endDate = sensorData.getEndTime();
			if(isInteger(sensorData.getValue())){
				average += Integer.parseInt(sensorData.getValue());
			}else if (isFloat(sensorData.getValue())){
				average += Float.parseFloat(sensorData.getValue());
			}
		}
		average = average / listSensorData.size();
		String value;
		if(isInteger(listSensorData.get(0).getValue())){
			value = String.valueOf(Math.round(average));
		}else{
			value = String.valueOf(average);
		}
		SensorData aggSensorData = new SensorData(listSensorData.get(0).getDevice(), listSensorData.get(0).getSensor(),
				value,startDate, endDate);
		
		return aggSensorData;
	}
	
	public static SensorData maxListSensorData(List<SensorData> listSensorData){
		List<SensorData> resultList = new ArrayList<SensorData>();
		Date startDate =  listSensorData.get(0).getStartTime();
		Date endDate =  startDate;
		float max = 0;
		if(isInteger(listSensorData.get(0).getValue())){
			max = Integer.parseInt(listSensorData.get(0).getValue());
		}else if (isFloat(listSensorData.get(0).getValue())){
			max = Float.parseFloat(listSensorData.get(0).getValue());
		}
		for(SensorData sensorData : listSensorData){
			endDate = sensorData.getEndTime();
			if(isInteger(sensorData.getValue())){
				int temp = Integer.parseInt(sensorData.getValue());
				if (temp > max) max = temp;
			}else if (isFloat(sensorData.getValue())){
				float temp = Float.parseFloat(sensorData.getValue());
				if (temp > max) max = temp;
			}
		}
		String value;
		if(isInteger(listSensorData.get(0).getValue())){
			value = String.valueOf(Math.round(max));
		}else{
			value = String.valueOf(max);
		}
		SensorData aggSensorData = new SensorData(listSensorData.get(0).getDevice(), listSensorData.get(0).getSensor(),
				value,startDate, endDate);
		return aggSensorData;
	}
	
	public static SensorData minListSensorData(List<SensorData> listSensorData){
		List<SensorData> resultList = new ArrayList<SensorData>();
		Date startDate =  listSensorData.get(0).getStartTime();
		Date endDate =  startDate;
		float min = 0;
		if(isInteger(listSensorData.get(0).getValue())){
			min = Integer.parseInt(listSensorData.get(0).getValue());
		}else if (isFloat(listSensorData.get(0).getValue())){
			min = Float.parseFloat(listSensorData.get(0).getValue());
		}
		for(SensorData sensorData : listSensorData){
			endDate = sensorData.getEndTime();
			if(isInteger(sensorData.getValue())){
				int temp = Integer.parseInt(sensorData.getValue());
				if (temp < min) min = temp;
			}else if (isFloat(sensorData.getValue())){
				float temp = Float.parseFloat(sensorData.getValue());
				if (temp < min) min = temp;
			}
		}
		String value;
		if(isInteger(listSensorData.get(0).getValue())){
			value = String.valueOf(Math.round(min));
		}else{
			value = String.valueOf(min);
		}
		SensorData aggSensorData = new SensorData(listSensorData.get(0).getDevice(), listSensorData.get(0).getSensor(),
				value,startDate, endDate);
		
		return aggSensorData;
	}
	
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	
	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public static boolean isBoolean(String str) {
		if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
		    return true;
		}
		return false;
	}

}
