package com.project.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import com.project.Graph;
import com.project.Handleable;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.Text;
import com.project.battle.BattleScreen;

public class Button extends Observable  implements Handleable{
	private int xCoordinate,yCoordinate,width,height,index;
	private Rectangle2D mask;
	private Text text;
	private ButtonID buttonID;
	private ImageHandler img;
	private boolean clickable;
	private boolean isButton = true;
	private boolean textBottomAligned = true;
	private Graph graph;
	private boolean draggable=false;
	private BattleScreen bs;

	public Graph getGraph() {
		return graph;
	}
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
	//8 Basic
	public Button(int x,int y,int width,int height,ButtonID buttonID,int index,boolean clickable, BattleScreen bs){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.mask = new Rectangle2D.Float(x, y, width, height);
		this.width = width;
		this.height = height;
		this.buttonID = buttonID;
		this.index = index;
		this.clickable = clickable;
		this.bs = bs;
		Handler.addButton(this);
	}

		
	// 9 Img
	public Button(int x,int y,int width,int height,ButtonID buttonID,int index,boolean clickable,ImageHandler img, BattleScreen bs){
			this.xCoordinate = x;
			this.yCoordinate =y;
			this.mask = new Rectangle2D.Float(x, y, width, height);
			this.width = width;
			this.height = height;
			this.buttonID = buttonID;
			this.index = index;
			this.clickable = clickable;
			this.bs = bs;
			img.setxCoordinate(x);
			img.setyCoordinate(y);
			this.img = img;
			Handler.addButton(this);
		}
	//14 Text
	public Button(int x,int y,int width,int height,ButtonID buttonID,int index,boolean clickable,String text,String fontName, int style, int size, Color colour, BattleScreen bs,boolean bottomAlign){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.mask = new Rectangle2D.Float(x, y, width, height);
		this.textBottomAligned = bottomAlign;
		this.width = width;
		this.height = height;
		this.buttonID = buttonID;
		this.index = index;
		this.clickable = clickable;
		this.bs = bs;
		this.text = new Text(text, clickable, x, y, fontName, style, size, colour,bs);
		Handler.addButton(this);
	}
	
	//14 Text default
	public Button(int x,int y,int width,int height,ButtonID buttonID,int index,boolean clickable,String text, BattleScreen bs,boolean bottomAlign){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.mask = new Rectangle2D.Float(x, y, width, height);
		this.textBottomAligned = bottomAlign;
		this.width = width;
		this.height = height;
		this.buttonID = buttonID;
		this.index = index;
		this.clickable = clickable;
		this.bs = bs;
		this.text = new Text(text, clickable, x, y,bs);
		Handler.addButton(this);
	}
	
	//14 Text + Image
	public Button(int x,int y,int width,int height,ButtonID buttonID,int index,boolean clickable,String text,String fontName, int style, int size, Color colour,ImageHandler img, BattleScreen bs){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.mask = new Rectangle2D.Float(x, y, width, height);
		this.width = width;
		this.height = height;
		this.buttonID = buttonID;
		this.index = index;
		this.clickable = clickable;
		this.bs = bs;
		this.text = new Text(text, clickable, x, y, fontName, style, size, colour,bs);
		img.setxCoordinate(x);
		img.setyCoordinate(y);
		this.img = img;
		Handler.addButton(this);
	}
	
	// 8 graph
	public Button(int x,int y,int width,int height,ButtonID buttonID,boolean clickable,Graph graph, BattleScreen bs){
		this.xCoordinate = x;
		this.yCoordinate =y;
		this.mask = new Rectangle2D.Float(x, y, width, height);
		this.width = width;
		this.height = height;
		this.buttonID = buttonID;
		this.clickable = clickable;
		this.bs = bs;
		graph.setX(graph.getX()+x);
		graph.setY(graph.getY()+y);
		this.graph = graph;
		this.graph.getText().setVisible(true);
		Handler.addLowPriorityEntity(this.graph);
		Handler.addButton(this);
	}
	
	
	public boolean isInside(int x, int y) {
		if(!clickable) {return false;}
		if(mask.contains(x, y)) {
			return true;
		}
		return false;
	}
	public void drag() {
		
	}
	public void click(int button){
		if(isButton) {
			setChanged();
			bs.update(buttonID,index,button);
		}
		
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
		if(img != null){
			img.setxCoordinate(x);
			img.setyCoordinate(y);
		}
		if(text != null) {
			if(textBottomAligned) {text.move(x,y+height);}
			else {text.move(x,y);}
		}
		if(graph!=null) {
			graph.setX(x);
			graph.setY(y);
		}
	}
	public void shift(int x , int y) {
		move(xCoordinate+x,yCoordinate+y);
	}
	public void setWidth(int w) {
		if(w>0) {
			this.width = w;
		}
	}
	public void setHeight(int h) {
		if(h>0) {
			this.height = h;
		}
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
		if(this.text != null) {
			this.text.changeMask(x, y, width, height);
		}
	}
	public void setImgMask(int x,int y, int width, int height) {
		if(this.img != null) {
			this.img.changeMask(x, y, width, height);
		}
	}
	public void setGraphMask(int x,int y, int width, int height) {
		if(this.graph != null) {
			this.graph.setClip(x, y, width, height);
		}
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
		g.setColor(Color.MAGENTA);
		g.drawRect(xCoordinate, yCoordinate, width, height);
		g.setColor(Color.GREEN);
		g.drawRect((int)mask.getX(), (int)mask.getY(), (int)mask.getWidth(), (int)mask.getHeight());
		
	}
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	public static void delete(Button b) {
		Handler.buttons.remove(b);
		if(b.text!=null) {Text.delete(b.text);}
		if(b.img!=null) {ImageHandler.delete(b.img);}
		if(b.graph!=null) { Handler.entitiesLowPriority.remove(b.graph);Text.delete(b.graph.getText());}
		
	}
	public void setIsButton(boolean clickable) {
		this.isButton  = clickable;
		
	}
	public boolean isDraggable() {
		return draggable;
	}
	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}
	public void drag(int x,int y,int button) {
		if(graph!=null) {
			graph.drag(x,y,button);
		}
		
		
	}
	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
