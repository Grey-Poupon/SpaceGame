package com.project;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.function.Function;

import com.project.Handleable;
import com.project.Handler;

public class Graph implements Handleable {
	//THIS MAKES ME CRY
	private float[] dataX = new float[256];
	private float[] dataY = new float[256];
	private int x;
	private int y;
	private int width;
	private int height;
	private Shape clip;
	private Text text;
	private Function<Double,Double> function;
	private int mouseX=0;
	public Shape getClip() {
		return clip;
	}

	public void setClip(int x,int y, int width, int height) {
		this.clip = new Rectangle2D.Float(x, y, width, height);;
	}

	public Graph(Function<Double,Double> function, int x, int y, int width, int height) {
		this.function = function;
		for(int i = 0;i<dataY.length;i++) {
			dataX[i]=x+i*width/dataX.length;
			dataY[i]= y+height-function.apply((double) i).intValue()*height/function.apply((double)dataY.length).intValue();
			//System.out.println(Integer.toString(dataX[i])+","+Integer.toString(dataY[i]));
		}
		this.text = new Text("",true,x,y);
		//text.changeMask(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		Handler.addHighPriorityEntity(this);
		//System.out.println("ASDA");
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setColor(Color.white);
		if(clip!=null) {g2d.setClip(clip);}
		
		for(int i =0;i<dataX.length-1;i++) {
			g2d.drawLine((int)dataX[i], (int)dataY[i],(int) dataX[i+1],(int) dataY[i+1]);
		}
		
		
		g2d.drawLine(x, y+height, x+width, y+height);
		g2d.drawLine(x, y, x, y+height);
		g2d.setColor(Color.red);
		g2d.drawLine(x,y+height-function.apply((double) mouseX*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue(),x+mouseX,y+height-function.apply((double) mouseX*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue());
		g2d.drawLine(x+mouseX, y+height, x+mouseX, y+height-function.apply((double) mouseX*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue());
		
		g2d.fillRect(x+mouseX-2, y+height-function.apply((double) mouseX*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue()-2, 5, 5);
		
	}
	

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void tick() {
		
	}

	public void setPoint(Point pos) {
		this.text.setText(Integer.toString(function.apply((double) (pos.x-x)).intValue())); 
		this.mouseX = pos.x-x;
	}

	public void drag(int x,int y, int button) {
		this.text.setText(Integer.toString(function.apply((double) (x-this.x)).intValue()));
		this.mouseX = x-this.x;
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
