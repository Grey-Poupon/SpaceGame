package com.project.phase2;

import java.util.Random;

public class ShopItem {

	public int cost;
	public String name;
	public ShopItem() {
		Random rand =new Random();
		name = "";
		for(int i = 0; i<rand.nextInt(5)+3;i++ ) {
			name =  name + (char)(1+rand.nextInt((int)'z'-(int)'A')+(int)'A');
		}
		cost = rand.nextInt(32)+30;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
