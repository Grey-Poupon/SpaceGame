package com.project;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Animation implements Handleable {
	private static final int DEFAULT_SPEED = 5;
	private boolean running;
	private int tileWidth;
	private int tileHeight;
	private int noVertTiles;
	private int noHorizTiles;
	private int ticksPerFrame;
	private int framesLeft;
	private int tickCounter = 0;
	private int xTile = 0 ;
	private int yTile = 0 ;
	private float xCoordinate;
	private float yCoordinate;
	private float xVel=0;
	private float yVel=0;
	private float xPixelsToMove =-1;
	private float yPixelsToMove =-1;
	private float xStart,xEnd;
	private float yStart,yEnd;
	private float xScale=1;
	private float yScale=1;
	private String path;
	private boolean moving = false;
	private boolean pushed = false; //for projectiles
	private boolean monitored = false; // ^^
	private Rectangle2D mask;
	private AdjustmentID align;
	int xStartGap;
	int yStartGap;
	int xGap;
	int yGap;
	private float scale = 1;
	private BufferedImage spritesheet;
	private BufferedImage sprite;
	private List<Animation> followingAnims = new ArrayList<Animation>();
	private float z;
	private int xFlip=1;
	private int yFlip = 1;
	
	public AdjustmentID getAlign() {
		return align;
	}

	//16 stationary
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles,int xStartGap, int yStartGp,int xGap,int yGap, int frameRate, float xCoordinate, float yCoordinate,float scale,int NoOfloops,boolean running,AdjustmentID align) {

		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.align = align;
		this.xStartGap = xStartGap;
		this.yStartGap = yStartGp;
		this.xGap = xGap;
		this.yGap = yGap;
		this.framesLeft = NoOfloops<0 ? -10 : NoOfloops*noHorizTiles*noVertTiles;
		this.scale = scale;
		this.mask = null;
		setSpritesheet(path);
		setSprite();
		if(running) {
			start();
		}
	}
	// 22 moving
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles,int xStartGap, int yStartGp,int xGap,int yGap, int frameRate,float scale,float xStart,float xEnd, float yStart, float yEnd,float xVel,Rectangle2D mask,boolean running,AdjustmentID align) {
		this.path          = path;
		this.tileWidth     = tileWidth;
		this.tileHeight    = tileHeight;
		this.noVertTiles   = noVertTiles;
		this.noHorizTiles  = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate   = xStart;
		this.yCoordinate   = yStart;
		this.align         = align;
		this.xStartGap     = xStartGap;
		this.yStartGap 	   = yStartGp;
		this.xGap 		   = xGap;
		this.yGap 		   = yGap;
		this.framesLeft    = -1;
		this.scale 		   = scale;
		this.xPixelsToMove = Math.abs(xEnd - xStart);
		this.yPixelsToMove = Math.abs(yEnd - yStart);;
		this.xVel 		   = xVel;
		this.mask 		   = mask;
		this.yStart 	   = yStart;
		this.yEnd 		   = yEnd;
		this.xStart 	   = xStart;
		this.xEnd 		   = xEnd;
		this.moving = true;
		
		if(xPixelsToMove!=0) {
			this.yVel = (yPixelsToMove*xVel)/(xPixelsToMove);}
		else {
			this.yVel = xVel; 
			this.xVel = 0;
			}
		
		setSpritesheet(path);
		setSprite();
		if(running) {
			start();
		}
	}
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public void setxCoordinate(float xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public void setyCoordinate(float yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public Animation(Animation animation,Animation[] followingAnims,boolean running) {
		this.path 		    = animation.path;
		this.tileWidth 	    = animation.tileWidth;
		this.tileHeight     = animation.tileHeight;
		this.noVertTiles    = animation.noVertTiles;
		this.noHorizTiles   = animation.noHorizTiles;
		this.ticksPerFrame  = animation.ticksPerFrame;
		this.xCoordinate    = animation.xStart;
		this.yCoordinate    = animation.yStart;
		this.align 		    = animation.align;
		this.xStartGap      = animation.xStartGap;
		this.yStartGap 	    = animation.yStartGap;
		this.xGap 		    = animation.xGap;
		this.yGap 		    = animation.yGap;
		this.framesLeft     = animation.framesLeft;
		this.scale 		    = animation.scale;
		this.xPixelsToMove  = animation.xPixelsToMove;
		this.yPixelsToMove  = animation.yPixelsToMove;
		this.xVel 		    = animation.xVel;
		this.mask 		    = animation.mask;
		this.yStart 	    = animation.yStart;
		this.yEnd 		    = animation.yEnd;
		this.xStart 	    = animation.xStart;
		this.xEnd 		    = animation.xEnd;
		this.moving         = animation.moving;
		this.running        = running;
		this.followingAnims.addAll(Arrays.asList(followingAnims));
		setSpritesheet(path);
		setSprite();
	}

	public void start() {
		running = true;
		Handler.addAnimation(this);
	}
	public void start(boolean add) {
		running = true;
		if(add) {
			Handler.addAnimation(this);
		}
	}
	
	
	public void addAnims(List<Animation> anims) {
		this.followingAnims = anims;
	}
	public void setSpritesheet(String path) {
		this.spritesheet = ResourceLoader.getImage(path);
		if(this.spritesheet == null) {
			throw new NullPointerException();
		}
	}
	public void setSprite() {
		if(spritesheet!=null) {

			sprite = spritesheet.getSubimage(xStartGap+xTile*(tileWidth+xGap), yStartGap+yTile*(tileHeight+yGap), tileWidth, tileHeight);
		}
	}
	public void nextSprite() {
		xTile++;
		if(xTile>noHorizTiles-1) {
			xTile = 0;
			yTile++;
		}
		if(yTile>noVertTiles-1) {
			yTile = 0;
		}
		setSprite();
	}
	public void render(Graphics g2) {
		Graphics g = g2.create();
		if(mask!=null) {g.setClip(mask);}
		int xAdjustment =(int) (AdjustmentID.getXAdjustment(align)*tileWidth*scale);
		int yAdjustment =(int) (AdjustmentID.getYAdjustment(align)*tileHeight*scale);
		g.drawImage(sprite, (int)xCoordinate+xAdjustment, (int)yCoordinate+yAdjustment,Math.round(sprite.getWidth()*scale*xScale*xFlip),Math.round(sprite.getHeight()*scale*yScale*yFlip), null);

	}
	public static void delete(Animation anim) {
		Handler.anims.remove(anim);
		anim = null;
		
	}
	public void tick() {
		if(running) {
			tickCounter++;
			// move
			if(moving) {
				System.out.println(xVel+" "+yVel);
				xCoordinate   += xVel;
				xPixelsToMove -= Math.abs(xVel);
				yCoordinate   += yVel;
				yPixelsToMove -= Math.abs(yVel);
			}
			// next frame/sprite
			if(tickCounter==ticksPerFrame) {
				nextSprite();
				tickCounter=0;
				if(framesLeft>-5){
					framesLeft--;
				}
				
			}
			// kill
			if(moving) {
				if(xPixelsToMove < 1 && yPixelsToMove < 1 ) {
					// setup next animation in chain
					if(followingAnims.size()>0 && followingAnims.get(0)!=null) {
						followingAnims.get(0).setX(xCoordinate);
						followingAnims.get(0).sety(yCoordinate);
						followingAnims.get(0).start();
					}
					// kill self
					if(monitored) {running = false;}
					else {Animation.delete(this);}
				}
			}
			else
			{					
				if (framesLeft < 1&&framesLeft>-5) {
					// setup next animation in chain
					if (followingAnims.size()>1) {
						Animation next = followingAnims.get(0);
						List<Animation> anns = followingAnims.subList(1, followingAnims.size());							next.addAnims(anns);
					}
					if(followingAnims.size()>0) {
						followingAnims.get(0).start();
					}
					// kill self
					if(!monitored) {Animation.delete(this);}
					running = false;
				}
			}
		}
	}
	private void sety(float yCoordinate2) {
		this.yCoordinate =yCoordinate2;
		
	}
	private void setX(float xCoordinate2) {
		this.xCoordinate = xCoordinate2;
	}
	public float getYVel() {
		
		return yVel;
	}
	public float getXVel() {
		return xVel;
	}
	public float getYPixelsToMove() {
		return yPixelsToMove;
	}
	public float getXPixelsToMove() {
		return xPixelsToMove;
	}
	public Animation copy() {
		Animation[] anims = new Animation[followingAnims.size()];
		for(int i = 0;i<anims.length;i++) {
			if(followingAnims.get(i)!=null) {anims[i] = followingAnims.get(i).copy();}
			}
		
		if(moving) {
			Animation temp = new Animation(path, tileWidth, tileHeight, noVertTiles, noHorizTiles, xStartGap, yStartGap, xGap, yGap, 60/ticksPerFrame, scale ,xStart, xEnd, yStart,yEnd,xVel, mask				   ,false,align);
			return new Animation(temp, anims, running);
		}
		else 	   {
			Animation temp = new Animation(path, tileWidth, tileHeight, noVertTiles, noHorizTiles, xStartGap, yStartGap, xGap, yGap, 60/ticksPerFrame, xCoordinate, yCoordinate, scale, framesLeft/(noHorizTiles*noVertTiles),false,align);
			return new Animation(temp, anims, running);
		}      
		
	}

	public void setStartAndEnd(Point start,Point end) {
		this.xStart = (float) start.getX();
		this.yStart = (float) start.getY();
		this.xEnd   = (float) end.getX();
		this.yEnd   = (float) end.getY();
		this.xPixelsToMove = Math.abs(xEnd-xStart);
		this.yPixelsToMove = Math.abs(yEnd-yStart);
		this.xCoordinate = this.xStart;
		this.yCoordinate = this.yStart;
		if(xVel == 0 ^ yVel == 0) {
			if(xVel == 0) {
				xVel = ((xPixelsToMove)*Math.abs(yVel))/(yPixelsToMove);
			}
			else {
				yVel = ((yPixelsToMove)*Math.abs(xVel))/(xPixelsToMove);
			}
		}
		else if (xVel == 0) {
			xVel = xEnd>xStart ? DEFAULT_SPEED : -DEFAULT_SPEED;
			yVel = yEnd>yStart ? ((yPixelsToMove)*Math.abs(xVel))/(xPixelsToMove): -((yPixelsToMove)*Math.abs(xVel))/(xPixelsToMove);
		}
		if(xVel<0) {this.scale = - Math.abs(scale);}
		else {
			this.scale = Math.abs(scale);
		}

	}
	public int getTileWidth() {
		return sprite.getTileWidth();
	}
	public int getTileHeight() {
		return sprite.getTileHeight();
	}
	public void setMask(Rectangle2D mask) {
		this.mask = mask;
	}
	public boolean isRunning() {
		return running;
	}
	public float getxCoordinate() {
		return xCoordinate;
	}
	public float getyCoordinate() {
		return yCoordinate;
	}
	public void pushBack(int pushBack) {
		float ticks = Math.abs(pushBack/xVel);
		float y = ticks*yVel;
		float x = ticks*xVel;
		yCoordinate -= y;
		xCoordinate -= x;
		this.xPixelsToMove += Math.abs(x);
		this.yPixelsToMove += Math.abs(y);
		this.pushed = true;
	}
	public boolean isPushed() {
		return pushed;
	}
	public void setMonitored(boolean b) {
		this.monitored  = b;
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

	@Override
	public float getZ() {
		
		// TODO Auto-generated method stub
		return z;
	}
	

	public void setAlign(AdjustmentID align) {
		this.align = align;
	}

	public int getyFlip() {
		return yFlip;
	}

	public void setyFlip(int yFlip) {
		this.yFlip = yFlip;
	}

	public int getxFlip() {
		return xFlip;
	}

	public void setxFlip(int xFlip) {
		this.xFlip = xFlip;
	}
	
	public float getOnScreenHeight() {
		return getTileHeight()*getyScale()*getScale();
	}
	public float getOnScreenWidth() {
		return getTileWidth()*getxScale()*getScale();
	}
	
}
