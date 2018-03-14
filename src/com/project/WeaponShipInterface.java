package com.project;

import java.util.ArrayList;
import java.util.List;

import com.project.ship.Ship;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class WeaponShipInterface {
	private Ship chaser;
	private Ship chased;
	private int gap;
	private List<ProjectileInfo> projectiles = new ArrayList<>();

	public WeaponShipInterface(Ship chaser, Ship chased) {
		this.chaser = chaser;
		this.chased = chased;
		this.gap = chaser.getDistanceToEnd() - chased.getDistanceToEnd();
	}
	public void add (Ship target, Ship source, Weapon weapon){
		for(int i = 0; i < weapon.getRateOfFire();i++){
			projectiles.add(new ProjectileInfo(weapon, source.getDistanceToEnd()+(i*weapon.getProjectileGap()),source.getSpeed()));
		}
	}
	private void updateShips(){
		chaser.updateDistance();
		chased.updateDistance();
	}
	
	
	
	/* Returns 2 lists of ProjectileInfo
	 * 1st of proj that hit the chaser
	 * 2nd that hit the chased
	 * Updates all other objects**/
	public List<List<ProjectileInfo>> update(){
		List<List<ProjectileInfo>> criticalProj = updateProj();
		updateShips();
		return criticalProj;
		
	}
	
	/* Returns 2 lists of ProjectileInfo
	 * 1st of proj that hit the chaser
	 * 2nd that hit the chased
	 * Updates all proj that don hit**/
	private List<List<ProjectileInfo>> updateProj() {
		
		List<List<ProjectileInfo>> criticalProj = new ArrayList<List<ProjectileInfo>>();
		criticalProj.add(new ArrayList<ProjectileInfo>());
		criticalProj.add(new ArrayList<ProjectileInfo>());
		
		for(int i =0 ;i<projectiles.size();i++){
			ProjectileInfo proj = projectiles.get(i);
			if(!(proj.getDistanceToEnd()<chaser.getDistanceToEnd() ^ proj.getDistanceToEnd() + proj.getVelocity()>chaser.getDistanceToEnd() + chaser.getSpeed())){
				// hit chaser
				criticalProj.get(0).add(proj);
				projectiles.remove(i);
			}
			else if(!(proj.getDistanceToEnd()<chased.getDistanceToEnd() ^ proj.getDistanceToEnd() + proj.getVelocity()>chased.getDistanceToEnd() + chased.getSpeed())){
				// hit chased
				criticalProj.get(1).add(proj);
				projectiles.remove(i);
			}
			else{
				proj.updateDistance();
			}
		}
		return criticalProj;
	}
}
