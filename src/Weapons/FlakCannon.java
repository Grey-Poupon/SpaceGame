package Weapons;

public class FlakCannon extends Weapon {
	public FlakCannon(int cooldownDuration) {
		super(cooldownDuration);
		// TODO Auto-generated constructor stub
	}
	Destructive destruct;
	
	@Override
	public int[] fire(){
		
		resetCooldown();
		return destruct.fire();	
	}
	
	private void resetCooldown(){ // made a function for it in case it got more complicated with buffs/debuffs
		this.cooldownTurnsLeft = getCooldownDuration();
	}}

}
