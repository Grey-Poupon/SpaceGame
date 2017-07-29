package com.project;

public class MathFunctions {
	public static double basicEffiency(double amountIn) {
		double mu = 20;
		double a = 1/(Math.sqrt(2*Math.PI)*Math.E);
		double b = -Math.pow(amountIn+mu, 2);
		return Math.pow(a,b);
	}


}
