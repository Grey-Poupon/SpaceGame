package com.project;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import com.project.ship.Ship;
import com.project.ship.Slot;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;

public class ProjectileAnimation implements Handleable{
	private static int animationsRunning;
	private Ship primary;
	private Ship secondary;
	private Weapon weapon;
	private Point click;
	private Random rand = new Random();
	private int noOfProjectiles;
	private int projectileGap;
	private int pushBack;
	private int projectilesStarted = 0;
	private boolean isLeftToRight;
	private boolean isCrossScreen;
	private Slot fromSlot;
	private Object[] damageDealt;
	private Animation animations[];
	private boolean running;
	public ProjectileAnimation(Ship primary, Ship secondary, int pushBack, boolean isCrossScreen ,Object[] effects,Point click,Slot slot) {
		this.primary       = primary;
		this.secondary     = secondary;
		this.isCrossScreen = isCrossScreen;
		this.pushBack	   = pushBack;
		this.click         = click;
		this.fromSlot = slot;
		this.isLeftToRight = click.x > slot.getX();
		this.weapon        = (Weapon)  slot.getSlotItem();
		this.projectileGap = weapon.getProjectileGap();
		//effect handler; this only works if there's 1 instance of destructive
		for(Object effect: effects) {
			if(effect instanceof Destructive) {
				//rateOfFire,accuracy,weaponSwayMod,DamagePerShot,damageType
				this.damageDealt = ((Destructive) effect).fire();
				float[] accuracy = (float[])damageDealt[1];
				for(float i : accuracy) {
					if(i==1.0) {
						this.noOfProjectiles++;
					}
				}
			}
		}
		this.animations    = new Animation[noOfProjectiles];
		
		animationsRunning++;
		Rectangle2D mask = new Rectangle2D.Double(0,0,Main.WIDTH,Main.HEIGHT);
		AdjustmentID align = AdjustmentID.None;
		if(isCrossScreen) {
			if(isLeftToRight) {
				mask = new Rectangle2D.Double(0,0,Main.WIDTH/2,Main.HEIGHT); 
				align = AdjustmentID.MidUp_Left;
			}
			else {
				mask = new Rectangle2D.Double(Main.WIDTH/2,0,Main.WIDTH/2,Main.HEIGHT);
				align = AdjustmentID.MidUp_MidLeft;
			}
		
		for(int i = 0;i<noOfProjectiles;i++) {
			Animation temp = weapon.getFiringAnimation();


			temp.setMonitored(true);
			Point start = new Point ();
			if(slot.isFront()) {
				start.setLocation(slot.getSlotItem().getSlotItemBody().getxCoordinate()+slot.getSlotItem().getSlotItemBody().getOnScreenWidth(),
						slot.getSlotItem().getSlotItemBody().getyCoordinate()+slot.getSlotItem().getSlotItemBody().getOnScreenHeight()/2);
			}else {
				start.setLocation(slot.getX(),slot.getY());
			}
			
			temp.setStartAndEnd(start, click);
			temp.setMask(mask);
			temp.setAlign(align);
			animations[i] =  temp;
			}
		}
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
				if(i!=0 && animations[i-1].getxCoordinate()-fromSlot.getX() > projectileGap){
					animations[i].start();
				}
					
			}
			// pushback after the mid point
			if(animations[i].isRunning()) {
				stillRunning = true;
				if(isLeftToRight) {
					if(!animations[i].isPushed() && animations[i].getxCoordinate()>Main.WIDTH/2) {
						animations[i].pushBack(pushBack);
						System.out.println("PushBack");
						animations[i].setMask(new Rectangle2D.Double(Main.WIDTH/2,0,Main.WIDTH/2,Main.HEIGHT));
					}
				}
				else if(!animations[i].isPushed() && (animations[i].getxCoordinate()+animations[i].getTileWidth())<Main.WIDTH/2) {
					animations[i].pushBack(pushBack);
					System.out.println("PushBack");
					animations[i].setMask(new Rectangle2D.Double(0,0,Main.WIDTH/2,Main.HEIGHT));
				}
			}
			else {
				// if the animation has finished do dmg
				doDamage();
				// delete self
				 Animation.delete(animations[i]);
			}		
		}
		if(!stillRunning) {
			animationsRunning--;
			Handler.entitiesHighPriority.remove(this);
		}
		setRunning(stillRunning);
	}
	// start function for the group of animation
	public void start() {
		Handler.addHighPriorityEntity(this);
		animations[0].start();
	}
	private void doDamage() {
		float[] accuracy = (float[]) damageDealt[1];
		int newX,newY,dmg;
		Weapon fireWeapon =  weapon;
		for(int j=0;j<(int)damageDealt[0];j++) {
			if(accuracy[j]!=0) {
				// add weaponSway
				newX = (int) (rand.nextBoolean() ? click.x+((int)damageDealt[2]*(1/fireWeapon.getAccuracy())):click.x-((int)damageDealt[2]*(1/accuracy[j])));
				newY = (int) (rand.nextBoolean() ? click.y+((int)damageDealt[2]*(1/fireWeapon.getAccuracy())):click.y-((int)damageDealt[2]*(1/accuracy[j])));
				// dmg here
				if(!weapon.isTargetSelf()){
					dmg = secondary.roomDamage(newX, newY);
					secondary.takeDamage(dmg,(boolean)damageDealt[3]);					
				}else{
					primary.apply(weapon);
				}

			}
		}
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
}