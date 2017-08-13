package com.project;

import java.awt.Color;
import java.awt.Graphics;

import com.project.ship.Ship;

public class DistanceSystem implements Handleable {
	private static final int xLocation      = 490;
	private static final int yLocation      = 26;
	private static final int lineLength	    = 300;
	private static final int dotSize        = 10;
	private static final int escapeDistance = 1000;
	private static final double pixelRatio  = (double)lineLength/(double)escapeDistance;
	private int distanceIncrement=1;
	private int chaserSpeed=0;
	private int chasedSpeed=0;
	private int shipDistanceCurrent = 500;
	private int shipDistanceDest = 500;
	private int	chaserXCurrent   = 75;
	private int	chasedXCurrent   = 75;
	private int	chaserXDest;
	private int	chasedXDest;
	
	
	public DistanceSystem(int shipDistance, int chaserDist, int chasedDist) {
		this.shipDistanceDest = shipDistance;
		this.shipDistanceCurrent = shipDistance;
		this.chaserXCurrent = (int) (chaserDist*pixelRatio);
		this.chasedXCurrent = (int) (chasedDist*pixelRatio);
		this.chasedXDest    =  this.chasedXCurrent;
		this.chaserXDest    =  this.chaserXCurrent;
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
			this.shipDistanceDest-=netSpeed;
		}
		else {
			int netSpeed = movingShip.getSpeed()-stationaryShip.getSpeed();
			movingShip.increaseDistanceToEnd(netSpeed);
			this.shipDistanceDest-=netSpeed;
		}
		if(movingShip.getDistanceToEnd() < 0) {
			stationaryShip.increaseDistanceToEnd(movingShip.getDistanceToEnd());
			movingShip.increaseDistanceToEnd(-movingShip.getDistanceToEnd());
		}
		if(stationaryShip.getDistanceToEnd() < 0) {
			movingShip.increaseDistanceToEnd(stationaryShip.getDistanceToEnd());
			stationaryShip.increaseDistanceToEnd(-stationaryShip.getDistanceToEnd());
		}
		chaserXDest = (int)(chaser.getDistanceToEnd()*pixelRatio);
		chasedXDest = (int)(chased.getDistanceToEnd()*pixelRatio);
		chaserSpeed = chaser.getSpeed();
		chasedSpeed = chased.getSpeed();
		int totalTicks;
		if(Math.abs(chasedXDest-chasedXCurrent)>Math.abs(chaserXDest-chaserXCurrent)) {
			  totalTicks = Math.abs(chasedXDest - chasedXCurrent);
		}
		else {
			  totalTicks = Math.abs(chaserXDest - chaserXCurrent);
		}
		distanceIncrement = Math.abs(shipDistanceDest-shipDistanceCurrent)/totalTicks;

	}
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(xLocation, yLocation, xLocation+lineLength, yLocation);
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(chaserSpeed),xLocation+chaserXCurrent, yLocation-dotSize);
		g.drawString(String.valueOf(chasedSpeed),xLocation+lineLength-chasedXCurrent, yLocation-dotSize);
		g.drawString(String.valueOf(shipDistanceCurrent),xLocation+chaserXCurrent+(int)(shipDistanceDest*pixelRatio)/2, yLocation+dotSize+3);
		g.fillOval(xLocation+chaserXCurrent, yLocation-(dotSize/2), dotSize, dotSize);
		g.fillOval(xLocation+lineLength-chasedXCurrent, yLocation-(dotSize/2), dotSize, dotSize);

	}
	public void tick() {
		if(chasedXCurrent!=chasedXDest) {
			int sign = chasedXDest>chasedXCurrent ? 1:-1; 
			chasedXCurrent += sign;
		}
		if(chaserXCurrent!=chaserXDest) {
			int sign = chaserXDest>chaserXCurrent ? 1:-1; 
			chaserXCurrent += sign;
		}
		if(shipDistanceCurrent!=shipDistanceDest) {
			int sign = shipDistanceDest>shipDistanceCurrent ? 1:-1; 
			shipDistanceCurrent += distanceIncrement*sign;
			if(shipDistanceCurrent>shipDistanceDest) {
				shipDistanceCurrent=shipDistanceDest;
			}

		}
	}
}
