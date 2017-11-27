package com.project;

public enum RoomSize {
	Small, Medium, Large;
	
	private static final int roomOutlineSizeStart = 30;
	private static final int roomOutlineSizeIncr  = 5;
	private static final int crewStep  = 5;

	
	public  int getNumericalSize(){
		if(this == Small){
			return 1;
		}
		else if(this == Medium){
			return 2;
		}
		else if(this == Large){
			return 3;
		}
		return 0;
	}
	
	public int getMaxPopulation(){
		return getNumericalSize() * 3;
	}
	
	public int getLength(){
		return getNumericalSize()*roomOutlineSizeIncr + roomOutlineSizeStart;
	}
	public static int getCrewStep(){
		return crewStep;
	}
}
