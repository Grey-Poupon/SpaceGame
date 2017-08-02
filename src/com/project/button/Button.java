package com.project.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import com.project.Handler;
import com.project.Text;

public class Button extends Observable{
	private int xCoordinate,yCoordinate,width,height,index;
	private Rectangle2D mask;
	private Text text;
	private ButtonID buttonID;
	private boolean clickable;

	public Button(int x,int y,int width,int height,ButtonID buttonID,int index,boolean clickable, Observer obs){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.mask = new Rectangle2D.Float(x, y, width, height);
		this.width = width;
		this.height = height;
		this.buttonID = buttonID;
		this.index = index;
		this.clickable = clickable;
		this.addObserver(obs);
		Handler.addButton(this);
	}
	public Button(int x,int y,int width,int height,ButtonID buttonID,int index,boolean clickable,String text,String fontName, int style, int size, Color colour, Observer obs){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.mask = new Rectangle2D.Float(x, y, width, height);
		this.width = width;
		this.height = height;
		this.buttonID = buttonID;
		this.index = index;
		this.clickable = clickable;
		this.addObserver(obs);
		this.text = new Text(text, clickable, x, y, fontName, style, size, colour);
		Handler.addButton(this);
	}
	
	public boolean isInside(int x, int y) {
		if(!clickable) {return false;}
		if(mask.contains(x, y)) {
			return true;
		}
		return false;
	}
	public void click(){
		setChanged();
		Object[] pass = new Object[] {buttonID,index};
		notifyObservers(pass);
	}
	public void changeMask(int x , int y, int width, int height) {
		width  = checkWidth(width);
		height = checkHeight(height);
		y 	   = checkY(y);
		x	   = checkX(x);
		this.mask = new Rectangle2D.Float(x,y,width,height);
	}
	public int checkY(int y) {
		if(y<yCoordinate) {y=0;}
		if(y>yCoordinate+height) {y=height;this.clickable=false;}
		return y;
	}
	public int checkX(int x) {
		if(x<xCoordinate) {x=0;}
		if(x>xCoordinate+width) {x=width;this.clickable=false;}
		return x;
	}
	public int checkWidth(int width) {
		if(width<0) 	{width = 0;this.clickable = false;}
		if(width>this.width) {width = this.width;}
		return width;
		//this.mask = new Rectangle2D.Float(xCoordinate, yCoordinate, x, height);
	}
	public int checkHeight(int height) {
		if(height<0) 	{height = 0; this.clickable = false;}
		if(height>this.height){height = this.height;}
		return height;
		//this.mask = new Rectangle2D.Float(xCoordinate, yCoordinate, width, y);
	}
	public void setFont(Font font) {
		text.setFont(font);
	}
	public void setColour(Color colour) {
		text.setColour(colour);
	}
	public void move(int x , int y) {
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.mask = new Rectangle2D.Float(x,y,width,height);
		if(text != null) {
			text.move(x,y+height/2);
		}
	}
	public void shift(int x , int y) {
		move(xCoordinate+x,yCoordinate+y);
		
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public boolean isClickable() {
		return clickable;
	}
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
		if(this.text != null) {this.text.setVisible(clickable);}
	}
	public void setTextMask(int x,int y, int width, int height) {
		this.text.changeMask(x, y, width, height);
	}
	public int getX() {
		return xCoordinate;
	}
	public void setX(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public int getY() {
		return yCoordinate;
	}
	public void setY(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public int getxCoordinate() {
		return xCoordinate;
	}
	public int getyCoordinate() {
		return yCoordinate;
	}
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(xCoordinate, yCoordinate, width, height);
		g.setColor(Color.GREEN);
		g.drawRect((int)mask.getX(), (int)mask.getY(), (int)mask.getWidth(), (int)mask.getHeight());
		
	}
	

}
