package Weapons;

public abstract class Weapon { // Holds the shared functionality between all weapons
	private int cooldownDuration; // weapons will have a cooldown period?
	protected int cooldownTurnsLeft; 
	
	public Weapon(int cooldownDuration){
		this.cooldownDuration=cooldownDuration;
	}
	
	protected int getCooldownDuration() {
		return cooldownDuration;
	}
	
	public int[] fire(){ // this should always be overridden
		return new int[]{0,0};
	}
	
}