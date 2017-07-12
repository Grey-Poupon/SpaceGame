import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

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
	
	public void displayPlayerStats(Player player,Graphics g){
		
		Font courierBold10 = new Font("Courier", Font.BOLD, 10);
		g.setColor(Color.red);
		g.setFont(courierBold10);
		g.drawString("Money: "+Integer.toString(player.getMoney()), 120, 120);
		g.drawString("Race: "+player.getRaceID().toString(), 120, 130);
	}	

}
