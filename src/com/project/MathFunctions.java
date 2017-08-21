package com.project;

import java.util.function.Function;

public class MathFunctions {
	public static double basicEffiency(double amountIn) {
		double mu = 20;
		double sigma = 5;
		double a = (1/(Math.sqrt(2*Math.PI))*Math.E);
		double b = -Math.pow(amountIn+mu, 2);
		return Math.pow(a,b);
	}
	
	public static Function<Double,Double> gaussian = x -> {
		return ((5/(Math.sqrt(2*Math.PI)))*Math.pow(Math.E, -x*x/4));	};
	public static Function<Double,Double> square = x -> {
			return x*x;	};
			


}
