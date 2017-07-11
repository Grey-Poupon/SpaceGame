
public class Ship {
	private Entity entity;
	
	public Ship(int x,int y, String path, boolean visible, EntityID id, int health){
		entity = new Entity(x, y, path, visible, EntityID.ship);
	}

}
