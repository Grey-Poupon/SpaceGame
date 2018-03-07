package com.project;

public class ActionCooldown {
	private int maxCooldown;
	private int cooldown;
	
	public ActionCooldown(int cooldown) {
		this.maxCooldown = cooldown;
		this.cooldown = cooldown;
	}
	/*Manages reseting and lowering cooldowns so don't worry **/
	public boolean isOffCooldown(){
		if(cooldown<=0){
			resetCooldown();
			return true;
		}
		lowerCooldown();
		return false;
	}
	private void lowerCooldown(){
		cooldown--;
	}
	private void resetCooldown(){
		cooldown = maxCooldown;
	}
}
