package com.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Text {

	protected int xCoordinate;//  top left coordinates of where the image is to be placed
	protected int yCoordinate;
	private ImageObserver observer;// any observer that wants to be notified when the this terrain is rendered
	private boolean visible = true;
	private String text;
	private Font font;
	private Color colour;	
	
	public Text(String text,boolean visible,int x, int y,String name, int style, int size, Color colour){
		this.xCoordinate=x;
		this.yCoordinate=y;
		this.text=text;
		this.font = new Font(name, style, size);
		this.colour =colour;
		Handler.texts.add(this);
		
	}
	public Text(String text,boolean visible,int x, int y){
		this.xCoordinate=x;
		this.yCoordinate=y;
		this.text=text;	
		this.font = new Font("Sevensegies", Font.PLAIN, 36);
		this.colour = Color.BLACK;
		Handler.texts.add(this);
	}
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR);
		g2d.setColor(colour);
		g2d.setFont(font);
		if(visible){
			g2d.drawString(text, xCoordinate, yCoordinate);
		}
	};
	public static void delete(Text t) {
		Handler.texts.remove(t);
		t = null;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
