package com.project;

import java.awt.Color;
import java.awt.Graphics;

import com.project.ship.Ship;

public class DistanceSystem implements Handleable {
	private static final int xLocation      = 490;
	private static final int yLocation      = 40;
	private static final int segmentLength = 25;
	private static final int segmentGap    = 10;
	private static final int numOfSegments  = 12; 
	
	private static final int lineLength	    = 300;
	private static final int dotSize        = 10;
	private static final int escapeDistance = 1000;
	private static final double pixelRatio  = (double)lineLength/(double)escapeDistance;
	private int distanceIncrement=1;

	private int shipDistanceCurrent = 500;
	private int shipDistanceDest = 500;
	private int	chaserXCurrent   = 75;
	private int	chasedXCurrent   = 75;
	private int	chaserXDest;
	private int	chasedXDest;
	private static Handler handler;
	
	public void render(Graphics g) {
		for(int i = 0;i<numOfSegments;i++){
			g.setColor(Color.WHITE);
			if(i==chaserPos){
				g.setColor(Color.RED);
			}
			if(i==chasedPos){
				g.setColor(Color.GREEN);
			}
			g.drawLine(xLocation+i*(segmentLength+segmentGap), yLocation, xLocation+segmentLength+i*(segmentLength+segmentGap), yLocation);
			
		}
		g.setColor(Color.YELLOW);
		
		//neg Speed
		if(chaserSpeed < 0){
			for(int i = chaserPos + chaserSpeed;i<chaserPos;i++){
				g.drawRect(xLocation+i*(segmentLength+segmentGap), yLocation+5, 20, 3);
			}
		}
		// pos speed
		else{
			for(int i = chaserPos+1;i<chaserPos+chaserSpeed+1;i++){
				g.drawRect(xLocation+i*(segmentLength+segmentGap), yLocation+5, 20, 3);
			}
		}
		//neg Speed

		if(chasedSpeed < 0){
			for(int i = chasedPos + chasedSpeed;i<chasedPos;i++){
				g.drawRect(xLocation+i*(segmentLength+segmentGap), yLocation+5, 20, 3);
			}
		}
		// pos speed
		else{
			for(int i = chasedPos+1;i<chasedPos+chasedSpeed+1;i++){
				g.drawRect(xLocation+i*(segmentLength+segmentGap), yLocation+5, 20, 3);
			}
		}
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int chaserPos;
	private int chasedPos;
	private int chaserSpeed=0;
	private int chasedSpeed=0;
	
	public DistanceSystem(int chaserPos, int chasedPos, int chaserSpeed, int chasedSpeed){
		this.chaserPos = chaserPos;
		this.chasedPos = chasedPos;
		this.chaserSpeed = chaserSpeed;
		this.chasedSpeed = chasedSpeed;
	}
	public void changeSpeed(int chaserSpeed, int chasedSpeed){
		this.chaserSpeed = chaserSpeed;
		this.chasedSpeed = chasedSpeed;
	}
	public void moveShips(){
		int newChaserPos = chaserPos + chaserSpeed;
		int newChasedPos = chasedPos + chasedSpeed;

		if(newChaserPos<0){
			// out of bounds
			newChasedPos -= newChaserPos;
			
		}
		if(newChasedPos>(numOfSegments - 1)){
			// out of bounds
			newChaserPos -= newChasedPos - (numOfSegments - 1);
			
			if(newChaserPos<0){
				// Escape
			}
		}
		
		if(newChaserPos>newChasedPos){
			//collision
			float halfGap = (chasedPos - chaserPos)/2f;
			if(chaserSpeed > halfGap){
				newChasedPos = newChaserPos; 
			}
			else if(chasedSpeed > -halfGap){
				newChaserPos = newChasedPos;
			}
			else{
				newChaserPos = chaserPos + Math.round(halfGap);
				newChasedPos = newChaserPos;
			}
		}
		
		this.chaserPos = newChaserPos;
		this.chasedPos = newChasedPos;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	

}
