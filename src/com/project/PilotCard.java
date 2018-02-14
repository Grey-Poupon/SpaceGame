package com.project;

import java.util.HashMap;

import com.project.battle.BattleScreen;
import com.project.battle.BattleUI;
import com.project.ship.Room;
import com.project.ship.rooms.Cockpit;
import com.project.slider.SliderID;
import com.project.slider.VerticalSlider;
import com.project.thrusters.Thruster;
import com.project.weapons.Weapon;

public class PilotCard extends ShipItemCard {
	
	private VerticalSlider slider;
	
	
	public PilotCard(Cockpit cockpit, BattleScreen bs){
		super((Actionable)cockpit, bs);
		//create slider
		//slider = new ImageHandler(0,0,"res/ui/slider.png",true,null);
		this.slider = new VerticalSlider(0, 0, 50, 4, 0, bs, SliderID.speed, ResourceLoader.getImage("res/sliderPanel.png"), ResourceLoader.getImage("res/sliderHandle.png"), bs.handler);
					
	}
	
	public void assembleCard(int x, int y){
		// place background
		background.setxCoordinate(x);
		background.setyCoordinate(y);
		//background.start(BattleScreen.handler,false);
		

		//place slider
		slider.setX(background.xCoordinate);
		slider.setY(background.yCoordinate);
	}
	
	public static void delete(PilotCard card) {
		ImageHandler.delete(BattleScreen.handler,card.itemImage);
		ImageHandler.delete(BattleScreen.handler,card.background);
		card.slider.deleteObservers();
		VerticalSlider.delete(card.slider);
	}
	


}
