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
	private Object[] damageInfo;
	private List<WeaponEffect> effects;
	private Animation animations[];
	public ProjectileAnimation( Ship primary, Ship secondary, int pushBack, boolean isCrossScreen ,Point click,Slot slot, int[] accuracy) {

		this.primary       = primary;
		this.secondary     = secondary;
		this.isCrossScreen = isCrossScreen;
		this.pushBack	   = pushBack;
		this.click         = click;
		this.fromSlot      = slot;
		this.isLeftToRight = click.x > slot.getX();
		this.weapon        = (Weapon)  slot.getSlotItem();
		this.projectileGap = weapon.getProjectileGap();// in pixels
		this.effects       = weapon.getEffects(); 

	
		for(int i:accuracy){noOfProjectiles+=i;}
		
		//effect handler; 
		for(int i = 0;i<effects.size();i++) {
			WeaponEffect effect = effects.get(i);
			
			/**Process Weapon Damage Info**/
			if(effect instanceof Destructive) {

				this.damageInfo = ((Destructive) effect).getInfo();
				// DPS , IsPhysical , Radius
			}
		}
		this.animations    = new Animation[noOfProjectiles];
		
		animationsRunning++;
		
		Rectangle2D mask = new Rectangle2D.Double(0,0,Main.WIDTH,Main.HEIGHT);
		AdjustmentID align = AdjustmentID.None;
		
		// set mask and alignment based off of direction
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
						//System.out.println("PushBack");
						animations[i].setMask(new Rectangle2D.Double(Main.WIDTH/2,0,Main.WIDTH/2,Main.HEIGHT));
					}
				}
				else if(!animations[i].isPushed() && (animations[i].getxCoordinate()+animations[i].getTileWidth())<Main.WIDTH/2) {
					animations[i].pushBack(pushBack);
					//System.out.println("PushBack");
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
			BattleScreen.handler.entitiesHighPriority.remove(this);
		}
		
	}
	// start function for the group of animation
	public void start() {
		BattleScreen.handler.addHighPriorityEntity(this);
		animations[0].start();
	}
	private void doDamage() {
			primary.doDamage(effects,damageInfo,weapon.isTargetSelf(),click);
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}


}