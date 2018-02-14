package com.project.ship;

import java.util.Random;

public class Engine {
	private String name;
	private Generator generator;
	private boolean exploded = false;
	private Overclockable overclock;
	
	
	public Engine(String name, Generator generator, boolean exploded, Overclockable overclock) {
		this.name = name;
		this.generator = generator;
		this.exploded = exploded;
		this.overclock = overclock;
	}
	public Engine(String name, boolean exploded, Overclockable overclock) {
		this.name = name;
		this.exploded = exploded;
		this.overclock = overclock;
	}
	
	

	
	private void explode() {
		if(generator!=null) {
			generator.explode();
		}
	}

	public double getThrust(double amountOfFuel) {
		double effiency = overclock.getEffiency(amountOfFuel);
		if (effiency<0) {
			explode();
			return -1;
		}
		return amountOfFuel * effiency;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSafetyCap() {
		return overclock.getSafetyCap();
	}
	
	public int getExplodeChance() {
		return overclock.getExplodeChance();
	}
	

	

}
