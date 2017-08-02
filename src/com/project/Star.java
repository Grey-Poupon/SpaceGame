package com.project;
import java.util.Random;

public class Star extends Entity{
	private int boundaryLeft;
	private int boundaryRight;
	private int boundaryTop;
	private int boundaryBottom;
	Random rand;
	public Star(int x, int y, String path, boolean visible,int left,int right,int top, int bottom) {
		super(x, y, path, visible,EntityID.star);
		this.boundaryLeft = left;
		this.boundaryBottom = bottom;
		this.boundaryRight = right;
		this.boundaryTop = top;
		rand = new Random();
		this.setXVel(-(int)(2*Math.abs((rand.nextGaussian()+10))));
	}
	
	public void tick(){
		super.tick();
		if(this.xCoordinate+this.getImg().getWidth()<boundaryLeft) {
			this.setxCoordinate(boundaryRight);
			this.setyCoordinate(boundaryTop+rand.nextInt(boundaryBottom-this.getImg().getHeight()-boundaryTop));
		}
	}

}
