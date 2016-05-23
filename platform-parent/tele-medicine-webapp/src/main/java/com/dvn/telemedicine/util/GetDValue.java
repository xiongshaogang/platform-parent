package com.dvn.telemedicine.util;

public class GetDValue {
	public static int getMemberSex(String idCard){
		int index=Integer.parseInt(idCard.substring(17, 18));
		return index%2;
	}
	public static Double getSugarValue(int isLimosis,Double sugarValue){
		Double value=null;
		if(isLimosis==AppKeys.IS_LIMOSIS_YES){//空腹
			if(sugarValue<3.9){
				value=3.9-sugarValue;
			}else if(sugarValue>7){
				value=7-sugarValue;
			}
		}else if(isLimosis==AppKeys.IS_LIMOSIS_NO){//非空腹
			if(sugarValue<3.9){
				value=3.9-sugarValue;
			}else if(sugarValue>10){
				value=10-sugarValue;
			}
		}
		return value;
	}
	public static int getLowPressureValue(int lowValue){
		int value=0;
		if(lowValue<60){
			value=60-lowValue;
		}else if(lowValue>80){
			value=lowValue-80;
		}
		return value;
	}
	public static int getHighPressureValue(int highValue){
		int value=0;
		if(highValue<90){
			value=90-highValue;
		}else if(highValue>120){
			value=highValue-120;
		}
		return value;
	}
}
