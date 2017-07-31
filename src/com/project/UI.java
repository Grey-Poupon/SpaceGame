package com.project;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class UI {
	private Entity healthBar;
	private Entity distanceBar;
	private static Entity mousePointer = new Entity(0,0, "res/mousepointer.png",true, EntityID.UI,"high");
	private static List<Button> tooltipButtons = new ArrayList<Button>();

	private static List<Button> crewButtons = new ArrayList<Button>();

	public UI(){
		//healthBar = new Entity(0,0,"res/healthbar.png",true,EntityID.UI);
	}
	public void updateMouse(int x, int y){
		mousePointer.setxCoordinate(x);
		mousePointer.setyCoordinate(y);
	}
	public void checkClick(int x, int y) {
		checkButtons(x, y);
	}
	
	public void displayPlayerStats(Player player,Graphics g){
		
		Font courierBold20 = new Font("Courier", Font.BOLD, 20);
		g.setColor(Color.red);
		g.setFont(courierBold20);
		g.drawString("Money: "+Integer.toString(player.getMoney()), 120, 120);
		g.drawString("Race: "+player.getRaceID().toString(), 120, 130);
	}	

	public static void clearTooltipButtons(){
		tooltipButtons.clear();
	}
	public void checkButtons(int x,int y){
		List<Button> buttons = crewButtons;
		buttons.addAll(tooltipButtons);
		for(int i =0; i<buttons.size();i++){
			if(buttons.get(i)!=null && buttons.get(i).isInside(x, y)){
				buttons.get(i).click();
			}
		}		
	}
	public static boolean addTooltipButton(Button button) {
		return tooltipButtons.add(button);
	}
	public static boolean removeTooltipButton(Button button) {
		return tooltipButtons.remove(button);
	}
	public static void addCrewButton(Button button) {
		crewButtons.add(button);
	}
	public static boolean removeCrewButton(Button button) {
		return tooltipButtons.remove(button);
	}

}
