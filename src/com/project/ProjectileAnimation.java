package com.project;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.PushbackReader;
import java.util.List;
import java.util.Random;

import com.project.ship.Ship;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;
import com.project.weapons.weapon_types.FireableWeapon;

public class ProjectileAnimation implements Handleable{
	private static int animationsRunning;
	private Ship primary;
	private Ship secondary;
	private Weapon weapon;
	private Point click;
	private Random rand = new Random();
	private int slotPostion;
	private int noOfProjectiles;
	private int projectileGap;
	private int pushBack;
	private int projectilesStarted = 0;
	private boolean isLeftToRight;
	private boolean isCrossScreen;
	private Object[] damageDealt;
	private Animation animations[];
	public ProjectileAnimation( Ship primary, Ship secondary, int slotPostion,int pushBack, boolean isCrossScreen ,Object[] effects,Point click) {
		this.primary       = primary;
		this.secondary     = secondary;
		this.slotPostion   = slotPostion;
		this.isCrossScreen = isCrossScreen;
		this.pushBack	   = pushBack;
		this.click         = click;
		this.isLeftToRight = click.x > primary.getSlot(slotPostion).getX();
		this.weapon        = (Weapon)  primary.getSlot(slotPostion).getSlotItem();
		this.projectileGap = weapon.getProjectileGap();
		
		
		// effect handler; this only works if there's 1 instance of destructive
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
		for(int i = 0; i < animations.length;i++) {
			animations[i] =  weapon.getAnimation();
			animations[i].setMonitored(true);
		}
		Handler.addHighPriorityEntity(this);
		animationsRunning++;
		Rectangle2D mask = new Rectangle2D.Double(0,0,Main.WIDTH,Main.HEIGHT);
		if(isCrossScreen) {
			if(isLeftToRight) {
				mask = new Rectangle2D.Double(0,0,Main.WIDTH/2,Main.HEIGHT); 
			}
			else {
				mask = new Rectangle2D.Double(Main.WIDTH/2,0,Main.WIDTH/2,Main.HEIGHT);
			}
		
		for(int i = 0;i<noOfProjectiles;i++) {
			Animation temp = weapon.getFiringAnimation();
			temp.setXStart(primary.getSlot(slotPostion).getX());
			temp.setYStart(primary.getSlot(slotPostion).getY());
			temp.setXEnd(click.x);
			temp.setYEnd(click.y);
			temp.setMask(mask);
			}
		}
	}
	
	public static boolean areAnimationsRunning() {
		return (animationsRunning != 0);
	}
	
	@Override
	public void render(Graphics g) {
		
	}
	@Override
	public void tick() {
		boolean stillRunning = false;;
		
		for(int i = 0;i<animations.length;i++) {
			
			if(projectilesStarted<noOfProjectiles) {
				// staggered starting
				if(i!=0 && animations[i-1].getxCoordinate()-primary.getSlot(slotPostion).getX() > projectileGap){
					animations[i].start();
				}
					
			}
			// pushback after the mid point
			if(animations[i].isRunning()) {
				stillRunning = true;
				if(!isLeftToRight) {
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
	}
	
	// start function for the group of animation
	public void start() {
		animations[0].start();
	}
	private void doDamage() {
		float[] accuracy = (float[]) damageDealt[1];
		int newX,newY,extraDmg;
		FireableWeapon fireWeapon = (FireableWeapon) weapon;
		for(int j=0;j<(int)damageDealt[0];j++) {
			if(accuracy[j]!=0) {
				// add weaponSway
				newX = (int) (rand.nextBoolean() ? click.x+((int)damageDealt[2]*(1/fireWeapon.getAccuracy())):click.x-((int)damageDealt[2]*(1/accuracy[j])));
				newY = (int) (rand.nextBoolean() ? click.y+((int)damageDealt[2]*(1/fireWeapon.getAccuracy())):click.y-((int)damageDealt[2]*(1/accuracy[j])));
				// get room dmg
				extraDmg = secondary.roomDamage(newX, newY);
				// dmg here
				
			}
		}
	}
	
}