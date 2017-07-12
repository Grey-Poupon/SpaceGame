package Weapons;


public class Laser extends Weapon {
	Destructive destruct;
	public Laser(int cooldownDuration, int rateOfFire,int damagePerShot,int accuracy) {
		super(cooldownDuration);
		this.destruct= new Destructive(rateOfFire,damagePerShot,accuracy);
	}

	@Override
	public int[] fire(){
		
		resetCooldown();
		return destruct.fire();	
	}
	
	private void resetCooldown(){ // made a function for it in case it got more complicated with buffs/debuffs
		this.cooldownTurnsLeft = getCooldownDuration();
	}
}
