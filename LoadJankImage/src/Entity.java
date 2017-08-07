
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entity {

	protected float xCoordinate;//  top left coordinates of where the image is to be placed
	protected float yCoordinate;
	protected int xVel;//  speed of mob
	protected int yVel;
	protected float xScale = 1;
	protected float yScale = 1;
	private ImageObserver observer;// any observer that wants to be notified when the this terrain is rendered
	private BufferedImage img; 
	private boolean visible = true;
	protected float zCoordinate;
	protected float oriZCoordinate;
	protected BufferedImage oriImg;
	
	public void tick(){
		xCoordinate+=xVel;
		yCoordinate+=yVel;
	};

	public void render(Graphics g)
	{
		if(visible){
				g.drawImage(img, Math.round(xCoordinate),Math.round(yCoordinate),Math.round(img.getWidth()*xScale),Math.round(img.getHeight()*yScale), observer);	
		}
	};
	public void changeImage(String newPath, boolean visible){
		this.setImg(newPath);
		this.setVisible(visible);	
	}
	public Entity(int x, int y , String path, boolean visible){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		setImg(path);
		Handler.addLowPriorityEntity(this);
		}
	
	public Entity(int x, int y , boolean visible){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		Handler.addLowPriorityEntity(this);
		}
	public Entity(int x, int y , String path, boolean visible, float scale){		// constructor for scaled mobs
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.xScale = this.yScale=scale;
		setImg(path);
		Handler.addLowPriorityEntity(this);
		}
	public Entity(int x, int y , String path, boolean visible, String priority){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		setImg(path);
		if(priority.equals("low")){
			Handler.addLowPriorityEntity(this);
			}
		else{
			Handler.addHighPriorityEntity(this);	
			}
		}
	
	
	public void setxCoordinate(float xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public float getxCoordinate() {
		return xCoordinate;
	}
	public float getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(float yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public void setXVel(int xVel) {
		this.xVel = xVel;
	}
	public int getXVel() {
		return xVel;
	}
	public void setYVel(int yVel) {
		this.yVel = yVel;
	}
	public int getYVel() {
		return yVel;
	}
	public ImageObserver getObserver() {					// getters and setters for all variables
		return observer;
	}
	public void setObserver(ImageObserver observer) {
		this.observer = observer;
	}
	
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	public BufferedImage getImg() {
		return img;
	}
	public BufferedImage getOriImg() {
		return oriImg;
	}
	public void setImg(String path) {
		try {
			this.img = ImageIO.read(new FileInputStream("res/"+path));
			this.oriImg =  ImageIO.read(new FileInputStream("res/"+path));
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public void setXScale(float scale){
		this.xScale = scale;
	}
	public float getXScale(){
		return this.xScale;
	}
	public void setYScale(float scale){
		this.yScale = scale;
	}
	public float getYScale(){
		return this.yScale;
	}
	
	public void setZ(float z) {
		this.zCoordinate = z;		
	}
	public float getZ() {
		return zCoordinate;
	}
	public void setOriZ(float z) {
		this.zCoordinate = z;		
	}
	public float getOriZ() {
		return zCoordinate;
	}

}

