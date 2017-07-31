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
	private List<Button> buttons = new ArrayList<Button>();
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
	public void addButtons(List<Button> buttons){
		this.buttons.addAll(buttons);
	}
	public void removeButtons(List<Button> buttons){
		this.buttons.removeAll(buttons);
	}
	public void clearButtons(){
		this.buttons.clear();
	}
	public void checkButtons(int x,int y){
		for(int i =0; i<buttons.size();i++){
			if(buttons.get(i)!=null && buttons.get(i).isInside(x, y)){
				buttons.get(i).click();
			}
		}		
	}

}
