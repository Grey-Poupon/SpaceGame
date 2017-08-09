package com.project;

import java.awt.Color;
import java.awt.Graphics;

public class DistanceSystem implements Handleable {
	private static final int xLocation      = 490;
	private static final int yLocation      = 26;
	private static final int lineLength	    = 300;
	private static final int dotSize        = 10;
	private static final int escapeDistance = 1000;
	private static final double pixelRatio  = (double)lineLength/(double)escapeDistance;
	private int chaserSpeed=0;
	private int chasedSpeed=0;
	private int shipDistance = 500;
	private int	chaserDist   = 75;
	private int	chasedDist   = 75;
	
	public DistanceSystem(int shipDistance, int chaserDist, int chasedDist) {
		this.shipDistance = shipDistance;
		this.chaserDist = (int) (chaserDist*pixelRatio);
		this.chasedDist = (int) (chasedDist*pixelRatio);
		Handler.addHighPriorityEntity(this);
	}
	public void calculateDistances(Ship chaser, Ship chased) {
		Ship movingShip  = null;
		Ship stationaryShip = null;
		if(chaser.getSpeedChange()>0 ^ chased.getSpeedChange()>0) {
			if(chaser.getSpeedChange()>0) {
				movingShip  = chaser; 
				stationaryShip = chased;
			}
			else {
				movingShip = chased;
				stationaryShip= chaser;
			}
		}
		else {
			movingShip  = chaser;
			stationaryShip = chased;}
		if(movingShip.getDistanceToEnd()==0 && movingShip.getSpeedChange()>0) {
			int netSpeed = -(movingShip.getSpeed()-stationaryShip.getSpeed());
			stationaryShip.increaseDistanceToEnd(netSpeed);
			this.shipDistance-=netSpeed;
		}
		else {
			int netSpeed = movingShip.getSpeed()-stationaryShip.getSpeed();
			movingShip.increaseDistanceToEnd(netSpeed);
			this.shipDistance-=netSpeed;
		}
		if(movingShip.getDistanceToEnd() < 0) {
			stationaryShip.increaseDistanceToEnd(movingShip.getDistanceToEnd());
			movingShip.increaseDistanceToEnd(-movingShip.getDistanceToEnd());
		}
		if(stationaryShip.getDistanceToEnd() < 0) {
			movingShip.increaseDistanceToEnd(stationaryShip.getDistanceToEnd());
			stationaryShip.increaseDistanceToEnd(-stationaryShip.getDistanceToEnd());
		}
		chaserDist = (int)(chaser.getDistanceToEnd()*pixelRatio);
		chasedDist = (int)(chased.getDistanceToEnd()*pixelRatio);
		chaserSpeed = chaser.getSpeed();
		chasedSpeed = chased.getSpeed();

	}
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(xLocation, yLocation, xLocation+lineLength, yLocation);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(chaserSpeed),xLocation+chaserDist, yLocation-dotSize);
		g.drawString(String.valueOf(chasedSpeed),xLocation+lineLength-chasedDist, yLocation-dotSize);
		g.drawString(String.valueOf(shipDistance),xLocation+chaserDist+(int)(shipDistance*pixelRatio)/2, yLocation+dotSize+3);
		g.fillOval(xLocation+chaserDist, yLocation-(dotSize/2), dotSize, dotSize);
		g.fillOval(xLocation+lineLength-chasedDist, yLocation-(dotSize/2), dotSize, dotSize);

	}
	public void tick() {
		
	}
}
