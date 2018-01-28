package com.project;

import com.project.battle.BattleScreen;
import com.project.ship.rooms.Cockpit;

public class PilotCard extends ShipItemCard {
	
	private ImageHandler slider;
	
	
	public PilotCard(Cockpit cockpit, BattleScreen bs){
		super((Actionable)cockpit, bs);
		//create slider
		slider = new ImageHandler(0,0,"res/ui/slider.png",true,null);
	}
	
	public void assembleCard(int x, int y){
		// place background
		background.setxCoordinate(x);
		background.setyCoordinate(y);
		background.start(BattleScreen.handler,false);

		// place item image
		itemImage.setxCoordinate(background.xCoordinate+actionPlacement.get(0).x);
		itemImage.setyCoordinate(background.yCoordinate+actionPlacement.get(0).y);
		itemImage.setVisible(true);
		itemImage.start(BattleScreen.handler,false);

		//place slider
		slider.setxCoordinate(background.xCoordinate+17);
		slider.setyCoordinate(background.yCoordinate+43);
		slider.setVisible(true);
		slider.start(BattleScreen.handler,false);
	}
	
	
	
	public static void delete(PilotCard card) {
		ImageHandler.delete(BattleScreen.handler,card.itemImage);
		ImageHandler.delete(BattleScreen.handler,card.background);
		ImageHandler.delete(BattleScreen.handler,card.slider);
		
	}
	
	
	public void boundSlider() {
		
		//keep the slider in it's position
		//if(slider.getxCoordinate()>)
		
	}

}
