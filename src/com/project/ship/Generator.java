package com.project.ship;

import java.util.function.Function;

import com.project.Graph;

public class Generator {
	private String name;
	private boolean exploded=false;
	private Overclockable overclock;
	private Function<Double,Double> efficiencyFunction;
	private Graph efficiencyGraph; 
	
	public Generator(String name,Function<Double,Double> function) {
		this.name = name;
		this.efficiencyFunction = function;
		this.efficiencyGraph = new Graph(function,0,0,0,0,true);
		this.efficiencyGraph.setDraggable(false);
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
