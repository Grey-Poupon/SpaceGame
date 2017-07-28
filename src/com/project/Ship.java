package com.project;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.Position;

import com.project.weapons.Weapon;
import com.project.weapons.weapon_types.Laser;

public class Ship {
	private LayeredImage lImage;
	private int health;
	private int maxHealth;
	private int currHealth;
	private Weapon[] frontWeapons = new Weapon[4]; // only allowed 4 front + 4 back weapons
	private Weapon[] backWeapons = new Weapon[4];
	Map<DamageType,Double> damageTakenModifier = new HashMap<DamageType,Double>();
	Map<DamageType,Double> damageDealtModifier = new HashMap<DamageType,Double>();

	public Ship(int x,int y,float z, float zPerLayer,String path, boolean visible, EntityID id, int health){
		lImage = new LayeredImage(x, y, path, zPerLayer, z);
		this.currHealth = this.maxHealth = health;
		
		for(DamageType dmg : DamageType.values()){
			damageTakenModifier.put(dmg, 1d);
			damageDealtModifier.put(dmg, 1d);
		}
		Weapon defaultWeapon = new Laser(1, 3, 5, 0.8, "Laser Mark I");
		for(int i=0;i<frontWeapons.length;i++){
			setFrontWeapon(defaultWeapon, i);
			setBackWeapon(defaultWeapon, i);
		}
	}
	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale){
		lImage = new LayeredImage(x, y, path, zPerLayer, z);
		this.currHealth = this.maxHealth = health;
		for(DamageType dmg : DamageType.values()){
			damageTakenModifier.put(dmg, 1d);
			damageDealtModifier.put(dmg, 1d);
		}
		Weapon defaultWeapon = new Laser(1, 3, 5, 0.8, "Laser Mark I");
		for(int i=0;i<frontWeapons.length;i++){
			setFrontWeapon(defaultWeapon, i);
			setBackWeapon(defaultWeapon, i);
		}
	}
	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale,Weapon[] frontWeapons,Weapon[] backWeapons){
		lImage = new LayeredImage(x, y, path, zPerLayer, z);
		this.frontWeapons = frontWeapons;
		this.backWeapons = backWeapons;
		this.health = health;
		for(DamageType dmg : DamageType.values()){
			damageTakenModifier.put(dmg, 1d);
			damageDealtModifier.put(dmg, 1d);
		}
	}

	public int getHealth() {
		return health;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public int getCurrHealth(){
		return currHealth;
	}
	public void takeDamage(int damage, DamageType type){
		this.currHealth-=damage*damageTakenModifier.get(type);
	}

	public Double getDamageTakenModifier(DamageType dt) {
		return damageTakenModifier.get(dt);
	}

	public void setDamageTakenModifier(DamageType dt, Double double1) {
		damageDealtModifier.put(dt, Double.valueOf(double1));
	}

	public Double getDamageDealtModifier(DamageType dt) {
		return damageDealtModifier.get(dt);
	}

	public void setDamageDealtModifier(DamageType dt, int mod) {
		damageDealtModifier.put(dt, Double.valueOf(mod));
	}

	public Weapon getFrontWeapon(int position) {
		return frontWeapons[position];
	}

	public Weapon getBackWeapon(int position) {
		return backWeapons[position];
	}

	public void setFrontWeapon(Weapon weapon, int position) {
		this.frontWeapons[position] = weapon;
	}
	public void setBackWeapon(Weapon weapon, int position) {
		this.backWeapons[position] = weapon;
	}
	public Weapon[] getFrontWeapons() {
		return frontWeapons;
	}
	public Weapon[] getBackWeapons() {
		return backWeapons;
	}
	public LayeredImage getLayeredImage() {
		return lImage;
	}
	public void tickLayers() {
		lImage.tick();
	}

	

}
