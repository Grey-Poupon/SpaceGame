package com.project.phase2;

import java.util.Random;

public class ShopItem {

	public int cost;
	public String name;
	public ShopItem() {
		Random rand =new Random();
		name = "";
		for(int i = 0; i<rand.nextInt(5)+3;i++ ) {
			name =  name + (char)rand.nextInt(200);
		}
		cost = rand.nextInt(32)+30;
	}

}
