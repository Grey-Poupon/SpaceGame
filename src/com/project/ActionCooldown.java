package com.project;

public class ActionCooldown {
	private int maxCooldown;
	private int cooldown;
	
	public ActionCooldown(int cooldown) {
		this.maxCooldown = cooldown;
		this.cooldown = cooldown;
	}
	
	public boolean isOffCooldown(){
		return cooldown<=0;
	}
	
	public void updateCooldown(){
		if(isOffCooldown()){
			resetCooldown();
		}
		else{
			lowerCooldown();
		}
	}
	private void lowerCooldown(){
		cooldown--;
	}
	private void resetCooldown(){
		cooldown = maxCooldown;
	}
}
