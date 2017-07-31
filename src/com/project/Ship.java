package com.project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.Position;

import com.project.crew_types.BugBitch;
import com.project.ship.Engine;
import com.project.ship.Generator;
import com.project.ship.Overclockable;
import com.project.weapons.Weapon;
import com.project.weapons.weapon_types.FireableWeapon;

public class Ship {
	private LayeredImage lImage;
	private int health;
	private int maxHealth;
	private int currHealth;
	private int speed = 0;
	private int power = 0;
	private Engine engine;
	private Generator generator;
	
	private Weapon[]       frontWeapons		   = new Weapon[4]; // only allowed 4 front + 4 back weapons
	private Weapon[]       backWeapons 		   = new Weapon[4];
	private List<Crew>     crew                = new ArrayList<Crew>();

	Map<DamageType,Double> damageTakenModifier = new HashMap<DamageType,Double>();
	Map<DamageType,Double> damageDealtModifier = new HashMap<DamageType,Double>();

	
	
	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale){
		lImage = new LayeredImage(x, y, path, zPerLayer, z,scale);
		this.currHealth = this.maxHealth = health;
		Weapon defaultWeapon = new FireableWeapon(1, 3, 5, 0.8, "Laser Mark I",DamageType.Laser);

		for(DamageType dmg : DamageType.values()){
			damageTakenModifier.put(dmg, 1d);
			damageDealtModifier.put(dmg, 1d);
		}
		for(int i=0;i<frontWeapons.length;i++){
			setFrontWeapon(defaultWeapon, i);
			setBackWeapon(defaultWeapon, i);
			crew.add(new BugBitch(50, 50, 50, 50, 50, 50, 50,50,'m'));
		}
	}
	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale,Weapon[] frontWeapons,Weapon[] backWeapons,Engine engine,Generator generator,List<Crew> crew){
		lImage = new LayeredImage(x, y, path, zPerLayer, z,scale);
		this.frontWeapons = frontWeapons;
		this.backWeapons = backWeapons;
		this.health = health;
		for(DamageType dmg : DamageType.values()){
			damageTakenModifier.put(dmg, 1d);
			damageDealtModifier.put(dmg, 1d);
		}
		this.generator=generator;
		this.engine=engine;
		this.crew = crew;
	}
	public void useEngine(int amountOfFuel) {
		double thrust = engine.getThrust(amountOfFuel);
		if(thrust<0) {System.out.println("your engine exploded, gg boyo");}//engine exploded
		speed = (int) thrust;
	}
	
	public void generatePower(int amountOfFuel) {
		double power = generator.getPower(amountOfFuel);
		if(power<0) {System.out.println("your generator exploded, gg boyo");}//engine exploded
		power = (int) power;
	}
	public List<Crew> getCrew() {
		return crew;
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
