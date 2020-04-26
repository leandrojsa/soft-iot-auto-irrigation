package br.ufba.dcc.wiser.soft_iot.auto_irrigation;

public final class CropEvotranspiration {
	
	public static float FAOPenmanMonteith(float Rn, float Tmin, float Tmax, float RHmean){
		float G = (float) 0.0;
		float g = (float) 0.067;
		float u2 = (float) 1.0;
		float es = meanSaturationVapourPressureEs(Tmin, Tmax);
		float ea = actualVapourPressureEa(Tmin, Tmax, RHmean);
		float Tmean = (Tmin + Tmax) / 2;
		float D = slopeSaturationVapourPressureD(Tmean);
		
		float eTo = (float) (0.408 * D * (Rn - G));
		
		eTo = eTo + (g*(900 / (Tmean + 273))* u2 *(es - ea));
		
		eTo = (float) (eTo / (D + g * (1 + 0.34 * u2)));
		
		return eTo;
	}
	
public static float meanSaturationVapourPressureEs(float Tmin, float Tmax){
		
		return (float) ((saturationVapourPressureEo(Tmin) + saturationVapourPressureEo(Tmax))/2.0);
	}
	
	public static float  saturationVapourPressureEo(float T){
		float eo = (float) (0.6108 * Math.exp((17.27*T)/(T+237.3)));
		
		return eo;
	}
	
	public static float actualVapourPressureEa(float Tmin, float Tmax, float RHmean){
		float ea = RHmean / 100;
		ea = ea * meanSaturationVapourPressureEs(Tmin, Tmax);
		
		return ea;
		
	}
	
	public static float  slopeSaturationVapourPressureD(float Tmean){
		float D = 4098 * saturationVapourPressureEo(Tmean);
		D = (float) (D / Math.pow((Tmean + 237.3), 2));
		
		return D;
	}
	
	public static float luxToMJperDay(float lux){		
		float v =  (float) (lux * 0.0079); //lux to W m^-2
		v = (float) (v * 0.0864); //W m^-2 to MJ m^-2d^-1
		
		return v;
	}
	
}
