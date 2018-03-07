package com.project;

import com.project.battle.BattleHandler;
import com.project.battle.BattleScreen;
import com.project.ship.rooms.Cockpit;
import com.project.slider.SliderID;
import com.project.slider.VerticalSlider;

public class PilotCard extends ShipItemCard {
	
	private VerticalSlider slider;
	private BattleHandler bh;
	
	public PilotCard(Cockpit cockpit, BattleScreen bs){
		super((Actionable)cockpit, bs);
		//create slider
		//slider = new ImageHandler(0,0,"res/ui/slider.png",true,null);
		bh =bs.handler;
		this.slider = new VerticalSlider(0, 0, 50, 4, 0, bs, SliderID.speed, ResourceLoader.getImage("res/sliderPanel.png"), ResourceLoader.getImage("res/ui/slider.png"), bs.handler);
	}
	
	public void assembleCard(int x, int y){
		
		//Place Background
		background.setxCoordinate(x);
		background.setyCoordinate(y);
		background.start(BattleScreen.handler,false);
		
		//Place Slider
		slider.setX(background.xCoordinate+17);
		slider.setY(background.yCoordinate);
		slider.start(bh);
	}
	
	public static void delete(PilotCard card) {
		ImageHandler.delete(BattleScreen.handler,card.itemImage);
		ImageHandler.delete(BattleScreen.handler,card.background);
		VerticalSlider.delete(card.slider);
	}

}
