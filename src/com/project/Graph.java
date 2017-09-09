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
	private double[] dataX = new double[256];
	private double[] dataY = new double[256];
	private int x;
	private int y;
	private int width;
	private int height;
	private Shape clip;
	private Text text;
	private Function<Double,Double> function;
	private int mouseX=0;
	private int modNum = 10;
	private int modNumY = 500;
	private float dangerZone = 0.25f;
	boolean textRight=false;
	private boolean draggable=false;
	private double yInput=0;
	private double xInput=0;
	Color dangerCol = new Color(1f,0f,0f,0.3f);
	
	public Shape getClip() {
		return clip;
	}

	public void setClip(int x,int y, int width, int height) {
		this.clip = new Rectangle2D.Float(x, y, width, height);
	}

	public Graph(Function<Double,Double> function, int x, int y, int width, int height,boolean textRight) {
		this.textRight = textRight;
		this.function = function;
		for(int i = 0;i<dataY.length;i++) {
			dataX[i]=i;
			dataY[i]= function.apply((double) i);
			//System.out.println(Integer.toString(dataX[i])+","+Integer.toString(dataY[i]));
		}
		this.text = new Text("",false,x+width,y+20);
		this.text.setText("Power: "+Integer.toString(0)+" Fuel: "+Integer.toString(0));
		//text.changeMask(x, y, width, height);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setColor(Color.white);
		if(clip!=null) {g2d.setClip(clip);}
		for(int i =0;i<dataX.length-1;i++) {
			g2d.drawLine(x+(int)dataX[i]*width/dataX.length, y+height-(int)dataY[i]*height/function.apply((double)dataY.length).intValue(),x+(int) dataX[i+1]*width/dataX.length,y+height-(int) dataY[i+1]*height/function.apply((double)dataY.length).intValue());
		}
		g2d.drawLine(x, y+height, x+width, y+height);
		g2d.drawLine(x, y, x, y+height);
		g2d.setColor(Color.red);
		if(draggable) {
			g2d.drawLine(x,y+height-function.apply((double) (mouseX-mouseX%modNum)*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue(),x+(mouseX-mouseX%modNum),y+height-function.apply((double) (mouseX-mouseX%modNum)*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue());
			g2d.drawLine(x+(mouseX-mouseX%modNum), y+height, x+(mouseX-mouseX%modNum), y+height-function.apply((double) (mouseX-mouseX%modNum)*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue());
			g2d.fillRect(x+(mouseX-mouseX%modNum)-2, y+height-function.apply((double) (mouseX-mouseX%modNum)*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue()-2, 5, 5);
		}else if(xInput!=0) {
			g2d.drawLine(x,y+height-function.apply((double) (xInput)*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue(),x+((int)xInput),y+height-function.apply((double) (xInput)*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue());
			g2d.drawLine(x+(int)xInput, y+height, x+((int)xInput), y+height-function.apply((double) (xInput)*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue());
			g2d.fillRect((int) (x+(xInput-2)), y+height-function.apply((double) (xInput)*dataX.length/width).intValue()*height/function.apply((double)dataY.length).intValue()-2, 5, 5);
		}
		
		
		g2d.setColor(dangerCol);
		g2d.fillRect((int)(x+width-width*dangerZone), y, (int)(width*dangerZone), height);

	}
	
	public void setGraphPoint(int y) {
		int index= 0;
		int minDist =Integer.MAX_VALUE;
		for(int i =0;i<dataY.length;i++) {
			if(Math.abs(y-dataY[i])<minDist) {
				index = i;
				minDist = (int) Math.abs(y-dataY[i]);
			}
		}
		yInput = dataY[index];
		xInput = dataX[index];
		this.text.setText("Power: "+Integer.toString(y)+" Fuel: "+Integer.toString((int)xInput-(int)xInput%modNum));
		
		
		
	}
	

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		this.text.xCoordinate = x+width; 
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		this.text.yCoordinate = y +50;
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
		if(draggable) {
			if(pos.x-x<width&&pos.x-x>0) {
				this.mouseX = pos.x-x;
			}
			int round = function.apply((double) (mouseX-mouseX%modNum)).intValue();
			this.text.setText("Power: "+Integer.toString(round-round%modNumY)+" Fuel: "+Integer.toString(mouseX-mouseX%modNum)); 
			
		}
		
	}

	public void drag(int x,int y, int button) {
		if(draggable) {
			if(x-this.x<width&&x-this.x>0) {
				this.mouseX = x-this.x;
			}
			
			int round = function.apply((double) (mouseX-mouseX%modNum)).intValue();
			this.text.setText("Power: "+Integer.toString(round-round%modNumY)+" Fuel: "+Integer.toString(mouseX-mouseX%modNum));
		
		}
	}
		

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Graph copy() {
		return new Graph(function,x,y,width,height,textRight);
	}
	
	public int getFuelCost() {
		return mouseX-mouseX%modNum;
	}
	
	public int getPower() {
		int round = function.apply((double) (mouseX-mouseX%modNum)).intValue();
		return round-round%modNumY;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}

	public double getyInput() {
		return yInput;
	}

	public void setyInput(float yInput) {
		this.yInput = yInput;
	}

	public double getxInput() {
		return xInput;
	}

	public void setxInput(float xInput) {
		this.xInput = xInput;
	}

	public boolean inDangerZone() {
		return xInput>width*(1-dangerZone);
	}
	

}
