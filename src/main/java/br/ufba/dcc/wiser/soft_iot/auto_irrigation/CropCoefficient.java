package br.ufba.dcc.wiser.soft_iot.auto_irrigation;

public final class CropCoefficient {
	
	public static double strawberryKc(int day){
		double kc;
		if(day <= 14 && day >= 1)
			kc=0.4;
		else if(day > 14 && day <= 34){
			kc = 0.4 + (day-14)*0.0225;
		}else if(day > 44 && day <= 64){
			kc=0.85;
		}else{
			kc=0.75;
		}
		return kc;
	}

}
