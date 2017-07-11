
public class UI {
	private Entity healthBar;
	private Entity distanceBar;
	private static Entity mousePointer;
	public UI(){
		healthBar = new Entity(0,0,"healthbar.png",true,EntityID.UI);
		mousePointer= new Entity(0,0, "mousepointer.png",true, EntityID.UI);
	}
	public static void updateMouse(int x, int y){
		mousePointer.setxCoordinate(x);
		mousePointer.setyCoordinate(y);
	}
	
	
	public static void checkClick(int x, int y) {
		
	}

}
