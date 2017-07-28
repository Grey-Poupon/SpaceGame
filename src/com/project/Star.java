package com.project;
import java.util.Random;

public class Star extends Entity{
	
	Random rand;
	Norm norm;
	public Star(int x, int y, String path, boolean visible) {
		super(x, y, path, visible,EntityID.star);
		rand = new Random();
		norm = new Norm();
		this.setXVel(-(norm.getNorm(10, 100)));
	}
	
	public void tick(){
		super.tick();
		if(this.xCoordinate+this.getImg().getWidth()<0) {
			this.setxCoordinate(Main.WIDTH+100);
			this.setyCoordinate(rand.nextInt(Main.HEIGHT-this.getImg().getHeight()));
		}
	}

}
