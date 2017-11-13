package com.project.ship;

public class Sensor {
	
	private float efficiency;
	private float healthEfficiency =0.5f;
	
	public Sensor(float efficiency) {
		this.setEfficiency(efficiency);
	}
	public float getHealthEfficiency() {
		return healthEfficiency;
	}
	public float getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}
	
}
