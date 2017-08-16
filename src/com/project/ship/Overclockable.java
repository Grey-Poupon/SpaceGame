package com.project.ship;

import java.util.Random;
import java.util.function.Function;


public class Overclockable {
	private int safetyCap;
	private int explodeChance;
	private Function<Double,Double> function;
	
	
	public Overclockable(int safetyCap, int explodeChance, Function<Double,Double> function) {
		this.safetyCap = safetyCap;
		this.explodeChance = explodeChance;
		this.function = function;
	}

	
	public double getEffiency(double amountIn) {
		if(Explodes()) {return -1;}
		return function.apply(amountIn);
	}
	
	private boolean Explodes() {
		Random rand = new Random();
		if(rand.nextInt(100)<explodeChance) {
			return true;
		}
		return false;
	}
	
	
	public int getSafetyCap() {
		return safetyCap;
	}

	public int getExplodeChance() {
		return explodeChance;
	}

}
