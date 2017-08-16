package com.project.ship;

public class Sensor {

	
	private float efficiency;
	private float healthEfficiency =0.5f;
	public Sensor(float efficiency) {
		this.efficiency = efficiency;
	
	}
	public float getHealthEfficiency() {
		return healthEfficiency;
	}
	

}
