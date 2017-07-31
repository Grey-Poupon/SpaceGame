package com.project.ship;

public class Generator {
	private String name;
	private boolean exploded;
	private Overclockable overclock;
	
	public Generator(String name, boolean exploded, Overclockable overclock) {
		this.name = name;
		this.exploded = exploded;
		this.overclock = overclock;
	}

	public double getPower(double amountOfFuel) {
		double effiency = overclock.getEffiency(amountOfFuel);
		if (effiency<0) {
			explode();
			return -1;
		}
		return amountOfFuel * effiency;
	}
	
	public void explode() {
		exploded = true;
	}
	public int getSafetyCap() {
		return overclock.getSafetyCap();
	}

	public int getExplodeChance() {
		return overclock.getExplodeChance();
	}
}
