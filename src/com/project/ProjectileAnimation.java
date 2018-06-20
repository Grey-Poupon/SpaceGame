package com.project;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.project.battle.BattleScreen;
import com.project.ship.Ship;
import com.project.ship.Slot;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class ProjectileAnimation implements Handleable{
	private static int animationsRunning;
	private Ship primary;
	private Weapon weapon;
	private Point click;
	private int noOfProjectiles;
	private int projectileGap;
	private int projectilesStarted = 0;
	private boolean isLeftToRight;
	private boolean isCrossScreen;
	private Slot slot;
	private Object[] damageInfo;
	private List<ProjectileInfo> projs;
	private Animation animations[];
	private Random rand = new Random();
	private boolean outbound;
	
	public ProjectileAnimation( Ship primary,List<ProjectileInfo> projectiles,boolean outbound) {

		this.primary       = primary;
		this.click         = projectiles.get(0).getDest();
		this.slot          = projectiles.get(0).getSlot();
		this.projectileGap = projectiles.get(0).getWaitGap();// in pixels

		this.isLeftToRight = click.x > slot.getX();
		this.isCrossScreen = isLeftToRight ? click.x > Main.WIDTH/2: click.x < Main.WIDTH;
		this.outbound      = outbound;
		this.noOfProjectiles = projectiles.size();
		this.animations    = new Animation[noOfProjectiles];
		this.projs = projectiles;
		
		animationsRunning++;
		
		Rectangle2D mask = new Rectangle2D.Double(0,0,Main.WIDTH,Main.HEIGHT);
		AdjustmentID align = AdjustmentID.None;
		Point mid = new Point();

		// set mask , end point and alignment based off of direction
		if(isCrossScreen) {
			if(isLeftToRight) {
				mask = new Rectangle2D.Double(Main.WIDTH/2,0,Main.WIDTH,Main.HEIGHT);
				align = AdjustmentID.MidUp_Left;
				mid.setLocation(Main.WIDTH/2 + slot.getSlotItem().getSlotItemBody().getOnScreenWidth(),Main.HEIGHT/2);
			}
			else {
				mask = new Rectangle2D.Double(0,0,Main.WIDTH/2,Main.HEIGHT); 
				align = AdjustmentID.MidUp_MidLeft;
				mid.setLocation(Main.WIDTH/2,Main.HEIGHT/2);
			}
		
		for(int i = 0;i<noOfProjectiles;i++) {
			
			Point start = new Point ();
			// set animation and start & end points
			Animation temp;
			
			if(outbound){
				temp = projs.get(i).getOutboundAnimation();
				temp.setMonitored(false);
				if(slot.isFront()) {
					start.setLocation(slot.getSlotItem().getSlotItemBody().getxCoordinate()
							+slot.getSlotItem().getSlotItemBody().getOnScreenWidth(),
							slot.getSlotItem().getSlotItemBody().getyCoordinate()
							+slot.getSlotItem().getSlotItemBody().getOnScreenHeight()/2);
				}else {
					start.setLocation(slot.getX(),slot.getY());
				}
				temp.setStartAndEnd(start,mid) ;
			}
			else{
				temp = projs.get(i).getInboundAnimation();
				temp.setMonitored(true);
				temp.setStartAndEnd(mid,click) ;
			}
			
			temp.setMask(new Rectangle2D.Double(0,0,Main.WIDTH,Main.HEIGHT));//temp.setMask(mask);
			temp.setAlign(align);
			animations[i] =  temp;
			}
		}
		start();
	}
	
	public static boolean areAnimationsRunning() {
		return (animationsRunning != 0);
	}
	
	public void render(Graphics g) {
		
	}
	
	public void tick() {
		boolean stillRunning = false;;
		
		for(int i = 0;i<animations.length;i++) {
			
			if(projectilesStarted<noOfProjectiles) {
				// staggered starting
				if(i!=0 && animations[i-1].getxCoordinate()-slot.getX() > projectileGap){
					animations[i].start();
				}
			}
			// kill after the mid point
			if(animations[i].isRunning()) {
				stillRunning = true;
				if(outbound){
					if(isLeftToRight) {
						if(animations[i].getxCoordinate()>Main.WIDTH/2) {
							Animation.delete(animations[i]);
							stillRunning = false;
						}
					}
					else if((animations[i].getxCoordinate()+animations[i].getTileWidth())<Main.WIDTH/2) {
						Animation.delete(animations[i]);
						stillRunning = false;
					}
				}
			}
			
			else {
				// if the animation has finished & is an inbound projectile, do effects
				if(!outbound){
					projs.get(i).doEffects();
				}
				// delete self
				 Animation.delete(animations[i]);
			}		
		}
		if(!stillRunning) {
			animationsRunning--;
			BattleScreen.handler.entitiesHighPriority.remove(this);
		}
	}
	// start function for the group of animation
	public void start() {
		BattleScreen.handler.addHighPriorityEntity(this);
		animations[0].start();
	}
	
	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}


}