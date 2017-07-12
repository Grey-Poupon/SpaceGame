import Weapons.Weapon;

public class Ship {
	private Entity entity;
	private int health;
	private Weapon[] frontWeapons = new Weapon[4]; // only allowed 4 front + 4 back weapons
	private Weapon[] backWeapons = new Weapon[4];

	public Ship(int x,int y, String path, boolean visible, EntityID id, int health){
		entity = new Entity(x, y, path, visible, EntityID.ship);
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	

	public void setFrontWeapon(Weapon weapon, int position) {
		this.frontWeapons[position] = weapon;
	}
	public void setBackWeapon(Weapon weapon, int position) {
		this.backWeapons[position] = weapon;
	}
	public int[] fireFrontWeapon(int position){
		return frontWeapons[position].fire();
		
	}
	public int[] fireBackWeapon(int position){
		return frontWeapons[position].fire();
		
	}

	

}
