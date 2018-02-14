package com.project.slider;

import java.awt.image.BufferedImage;

import com.project.EntityID;
import com.project.Handleable;
import com.project.ImageHandler;
import com.project.battle.BattleScreen;

public class VerticalSliderHandle {

	private int x,y,startX,startY;
	private int stepLen;
	private int maxStep; // 0 based indexing
	private int curStep;
	private ImageHandler handleImg;
	private int mouseOffset= 0;
	
	/** curStep & maxStep use 0-based indexing */
	public VerticalSliderHandle(int x, int y, int stepLen, int maxStep, int curStep, BufferedImage handleImg) {
		this.x = x;
		this.startX = x;
		this.y = y;
		this.startY = y;
		
		this.stepLen = stepLen;
		this.maxStep = maxStep;
		this.curStep = curStep;
		this.handleImg = new ImageHandler(x, y, handleImg, true, EntityID.UI);
		
	}
	
	public void moveTo(int step) {
		
		if(step>0 && step<maxStep+1 && curStep!=step){
			// update Y pos of handle
			y = y+((curStep-step)*stepLen);
			curStep = step;	
			handleImg.setyCoordinate(y);
		}
	}

	public int getStep() {
		return curStep;
	}

	public boolean isInside(int x2, int y2) {
		return (x2>x && x2<x+handleImg.getWidth()) && (y2>y && y2<y+handleImg.getHeight()) ;
	}

	public void drag(int x2, int y2) {
		y2+=mouseOffset;
		
		if(y2 > startY && y2 < startY + (maxStep * stepLen)){
			handleImg.setyCoordinate(y2);
			y=y2;
		}
		
	}
	
	public void grabbed(int x, int y){
		this.mouseOffset = this.y-y;
	}

	public void setX(int x) {
		this.x = x;
		this.startX = x;
		this.handleImg.setxCoordinate(x);
	}
	public void setStartX(int x) {
		this.startX = x;
	}
	public void setY(int y) {
		this.y = y;
		this.handleImg.setyCoordinate(y);
	}
	
	public void setStartY(int y2) {
		this.startY = y;
	}
	
	public static void delete(VerticalSliderHandle handle) {
		ImageHandler.delete(BattleScreen.handler, handle.handleImg);
		handle = null;
	}

	public Handleable getImg() {
		return handleImg;
	}

	public void drop() {
		int middle =(y + handleImg.getHeight()/2) - startY;
		int step = middle / stepLen;

		// round up
		if(middle % stepLen > stepLen/2){
		//	step++;
		}
		System.out.println("Step: "+step+" Y: "+middle);
		setY(startY + (stepLen*step));
		
	}



}
