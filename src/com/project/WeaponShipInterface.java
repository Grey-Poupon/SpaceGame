package com.project;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.project.ship.Room;
import com.project.ship.Ship;
import com.project.weapons.Destructive;
import com.project.weapons.Target;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class WeaponShipInterface implements Observer{
	private Ship chaser;
	private Ship chased;
	private int gap;
	private List<ProjectileInfo> projectiles = new ArrayList<>();

	public WeaponShipInterface(Ship chaser, Ship chased){
		this.chaser = chaser;
		this.chased = chased;
		this.gap = chaser.getDistanceToEnd() - chased.getDistanceToEnd();
	}
	
	// Add weapons that have been fired into the simulation
	public List<ProjectileInfo> addCreateProj(Ship target, Ship source, Weapon weapon, Point point){
		List<ProjectileInfo> projs = new ArrayList<>();
		for(int i = 0; i < weapon.getRateOfFire();i++){
			ProjectileInfo temp = new ProjectileInfo(weapon, source.getDistanceToEnd()+(i*weapon.getProjectileGap()),source.getSpeed(),source==chaser);
			temp.addDest(point);
			projs.add(temp);
		}
		projectiles.addAll(projs);
		return projs;
	}
	// same as above but for list of weapons
	public List<List<ProjectileInfo>> addCreateProj(Ship target, Ship source, List<Weapon> weapons, List<Point> points){
		List<List<ProjectileInfo>> projs = new ArrayList<>();
		for(int i = 0;i<weapons.size();i++){
			projs.add(addCreateProj(target,source,weapons.get(i),points.get(i)));
		}
		return projs;
	}
	// update the movements of the ships
	private void updateShips(){
		chaser.updateDistance();
		chased.updateDistance();
	}
	
	/* Returns 2 lists of ProjectileInfo
	 * 1st of proj that hit the chaser
	 * 2nd that hit the chased
	 * Updates all other objects**/
	public List<List<ProjectileInfo>> simulate(){
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

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ProjectileInfo){
			doEffects((ProjectileInfo)o);
		}
		
	}

	private void doEffects(ProjectileInfo p) {
		for(WeaponEffect e: p.effects){
			if(e instanceof Destructive){
				int damagePerShot      = ((Destructive) e).getDamagePerShot();
				boolean isPhysical     = ((Destructive) e).isPhysical();
				int radiusOfHit = ((Destructive) e).getRadiusOfHit();
			
				if(p.getTarget() == Target.Enemy){
						if(p.isFromChaser()){
							List<Room> rooms = chased.getRoomsHit(p.getDest(),radiusOfHit);
							System.out.println("Number of hit rooms: "+rooms.size());
							
							/**loop through rooms that get hit**/
							for(int k = 0;k < rooms.size();k++){
								
								/**Room takes damage**/
								int roomTableRoll = rooms.get(k).takeDamage(damagePerShot);
								
								/**Rooms Roll Table**/
								chased.doRollTableEffect(roomTableRoll,rooms.get(k));//Unimplemented

								/**If its physical, damage crew in room**/
								if(isPhysical){
									for(Crew crew: rooms.get(k).getCrewInRoom()){
										
										/**Crew takes damage**/
										int crewTableRoll = crew.takeDamage(damagePerShot);
										
										/**Crews Roll Table**/
										chased.doRollTableEffect(crewTableRoll, crew);//Unimplemented
									}
								}
							}
						}
						else{
							
						}
				}else if(p.getTarget() == Target.Self){
					if(p.isFromChaser()){
						
					}
					else{
						
					}
				}
				else if(p.getTarget() == Target.Both){
					if(p.isFromChaser()){
						
					}
					else{
						
					}
				}
			}
		}
		
	}
}
