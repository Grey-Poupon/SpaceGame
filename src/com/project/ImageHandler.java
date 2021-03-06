package com.project;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;

public class ImageHandler implements Handleable {
	protected EntityID id;

	protected int xCoordinate;//  top left coordinates of where the image is to be placed
	protected int yCoordinate;
	protected float zCoordinate;
	protected Area outline;
	protected int xVel;//  speed of mob
	protected int yVel;
	protected float xScale = 1;
	protected float yScale = 1;
	private EntityID ID;
	private ImageObserver observer;
	private BufferedImage img; 
	private boolean visible = true;
	private Shape clip;
	protected BufferedImage oriImg;
	
	public BufferedImage getOriImg() {
		return this.oriImg;
	}
	public static BufferedImage copyBufferedImage(BufferedImage img) {
		ColorModel cm = img.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 
		WritableRaster raster = img.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);

	}
	public ImageHandler copy() {
		return new ImageHandler(xCoordinate, yCoordinate, img, isVisible(), xScale, yScale, getEntityID());
	}
	public void setOriImg(BufferedImage oriImg) {
		this.oriImg = oriImg;
	}

	public void tick(){	
		xCoordinate+=xVel;
		yCoordinate+=yVel;
	};



	public float getzCoordinate() {
		return zCoordinate;
	}
	
	public  void generateOutline() {
        Area area = new Area();
        for (int y=0; y<img.getHeight(); y++) {
        	boolean lastcheck = false;
        	int xStart=0;
            for (int x=0; x<img.getWidth(); x++) {
                Color pixel = new Color(img.getRGB(x,y),true);
                if(pixel.getAlpha()!=0) {
                	if(lastcheck!=true) {	xStart = x;lastcheck = true;}

                }
                if(lastcheck&&(pixel.getAlpha()==0||x==img.getWidth()-1)) {
                	Area r = new Area(new Rectangle(xStart,y,x-1-xStart,1));
                	area.add(r);
                	lastcheck = false;
                }
            }
        }
        this.outline =area;
    }
	
	public Area getOutline() {
		return this.outline;
	}

	public void setzCoordinate(float zCoordinate) {
		this.zCoordinate = zCoordinate;
	}

	public float getxScale() {
		return xScale;
	}

	public void setxScale(float xScale) {
		this.xScale = xScale;
	}

	public float getyScale() {
		return yScale;
	}

	public void setyScale(float yScale) {
		this.yScale = yScale;
	}
	
	public void render(Graphics g)
	{
		if(visible){
			if(clip != null) {g.setClip(clip);}
				g.drawImage(img, xCoordinate, yCoordinate,Math.round(img.getWidth()*xScale),Math.round(img.getHeight()*yScale), observer);	
		}
	};
	
	public void changeImage(String newPath, boolean visible){
		setImg(newPath);
		this.setVisible(visible);
		
	}
	

	public ImageHandler(int x, int y , String path, boolean visible, EntityID ID){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;
		setImg(path);
//		Handler.addLowPriorityEntity(this);
		}
	
	public ImageHandler(int x, int y , boolean visible, EntityID ID){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;

		}
	public ImageHandler(int x, int y,BufferedImage img , boolean visible, EntityID ID){		
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;
		setImg(img);

		}
	public ImageHandler(int x, int y , String path, boolean visible, float xscale, float yscale, EntityID ID){		// constructor for scaled mobs
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.xScale = xscale;
		this.yScale = yscale;
		this.ID = ID;
		setImg(path);
		}
	public ImageHandler(int x, int y , BufferedImage img, boolean visible, float xscale, float yscale, EntityID ID){		// constructor for scaled mobs
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.xScale = xscale;
		this.yScale = yscale;
		this.ID = ID;
		setImg(img);
		}
	public ImageHandler(int x, int y , String path, boolean visible, float scale, EntityID ID){		// constructor for scaled mobs
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.xScale = scale;
		this.yScale = scale;
		this.ID = ID;
		setImg(path);
//		Handler.addLowPriorityEntity(this);
		}
	public ImageHandler(int x, int y , String path, boolean visible, EntityID ID, String priority){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;
		setImg(path);
//		if(priority.equals("low")){
//			Handler.addLowPriorityEntity(this);
//			}
//		else{
//			Handler.addHighPriorityEntity(this);	
//			}
		}
	
	
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public int getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public void setZ(float z) {
		this.zCoordinate = z;
	}
	public float getZ() {
		return zCoordinate;
	}
	public void setXVel(int xVel) {
		this.xVel =xVel;
	}
	public int getXvel() {
		return xVel;
	}
	public void setYVel(int yVel) {
		this.yVel =yVel;
	}
	public int getYvel() {
		return xVel;
	}
	
	
	public ImageObserver getObserver() {					// getters and setters for all variables
		return observer;
	}
	public void setObserver(ImageObserver observer) {
		this.observer = observer;
	}
	public BufferedImage getImgCopy() {
		return ImageHandler.copyBufferedImage(img);
	}
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(String path) {

		this.img =ResourceLoader.getImage(path);
		
		ColorModel cm = img.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 
		WritableRaster raster = img.copyData(null);
		this.oriImg= new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		 
	}
	public void setImg(BufferedImage img) {
		this.img = img;
	}
	public void setImg(ImageHandler img) {
		this.img = img.getImg();
	}
	
	public Object getID() {
		// TODO Auto-generated method stub
		return this.id;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public EntityID getEntityID() {
		return ID;
	}
	public void setEntityID(EntityID mobID) {
		this.ID = mobID;
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
	public void changeMask(int x , int y, int width, int height) {
		this.clip = new Rectangle2D.Float(x, y, width, height);
	}
	public void removeMask() {
		this.clip = null;		
	}

	public int getxCoordinate() {
		return xCoordinate;
	}
	public float getOnScreenHeight() {
		return getImg().getHeight()*getyScale();
	}
	public float getOnScreenWidth() {
		return getImg().getWidth()*getxScale();
	}
	public boolean isClicked(int x, int y) {
		if(x>xCoordinate && x<xCoordinate+(img.getWidth()*xScale) && y>yCoordinate && y<yCoordinate+(img.getHeight()*yScale)) {
			double X = ((x-xCoordinate)/xScale);
			double Y = ((y-yCoordinate)/yScale);
			int newX = (int)X;
			int newY = (int)Y;
			if( (img.getRGB(newX,newY)>>24) != 0x00 ) {
			    return true;
			}
		}
		return false;
	}
	public static void delete(Handler handler,ImageHandler img){
		handler.stars.remove(img);
		handler.entitiesLowPriority.remove(img);
		handler.entitiesHighPriority.remove(img);
	}

	public int getHeight() {
		return img.getHeight();
	}

	public int getWidth() {
		return img.getWidth();
	}
	public void start(Handler handler,boolean high) {
		if(high){
			handler.addHighPriorityEntity(this);
		}
		else{handler.addLowPriorityEntity(this);}
	}
	public void addImageFrame(BufferedImage bf,int xOffset,int yOffset){
		
		for(int x = xOffset;x<bf.getWidth();x++){
			for(int y = yOffset;y<bf.getHeight();y++){
				
				if((bf.getRGB(x,y)>>24) == 0x00 && x-xOffset< img.getWidth() && y-yOffset < img.getHeight()){
					bf.setRGB(x, y, img.getRGB(x-xOffset, y-yOffset));
				}
				
			}
		}
		this.img = bf;
	
	}
	
}
