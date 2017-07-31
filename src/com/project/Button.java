package com.project;

import java.util.Observable;
import java.util.Observer;

public class Button extends Observable{
	private int xCoordinate,yCoordinate,width,height,index;
	private ButtonID buttonID;

	public Button(int x,int y,int width,int height,ButtonID buttonID,int index, Observer obs){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.width = width;
		this.height = height;
		this.buttonID = buttonID;
		this.index = index;
		this.addObserver(obs);
	}
	public boolean isInside(int x, int y){
		if(x<this.xCoordinate+this.width&&x>this.xCoordinate){
			if(y<this.yCoordinate+this.height&&y>this.yCoordinate){
				return true;
			}
		}
		return false;
	}
	public void click(){
		setChanged();
		Object[] pass = new Object[] {buttonID,index};
		notifyObservers(pass);
	}

}
