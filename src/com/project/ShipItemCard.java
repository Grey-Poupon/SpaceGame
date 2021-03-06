package com.project;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.project.battle.BattleScreen;
import com.project.battle.BattleUI;
import com.project.ship.Room;
import com.project.ship.Ship;
import com.project.thrusters.Thruster;
import com.project.weapons.Weapon;

public class ShipItemCard {
	protected Actionable item;
	protected List<CrewAction> actions = new ArrayList<CrewAction>();
	protected List<ActionBox > boxes   = new ArrayList<ActionBox >();
	protected ImageHandler background;
	protected Ship ship;
	protected BattleScreen bs;
	protected List<Point> actionPlacement = new ArrayList<Point>();
	protected ImageHandler itemImage;
	protected Point imagePosition;
	// Item image dimensions should always be 150,50
	
	protected final int marginWidth            = 5;
	protected final int image_ActionBoxGap     = 10;
	protected final int ActionBox_ActionBoxGap = 10;
	protected final int ActionBox_TextGap      = 5;

	public ShipItemCard(Actionable item, BattleScreen bs){
		this.item       = item;
		this.actions    = item.getActions();
		this.background = item.getCardBackground();
		this.itemImage  = item.getCardImage();
		this.bs         = bs;
		this.ship       = bs.getPlayerShip();
		boolean notInBox = true;
		
		for(int x =0;x<item.getCardBackground().getWidth();x++) {
			for(int y = 0; y<item.getCardBackground().getHeight();y++) {
				
				if(imagePosition == null) {
					if(item.getCardBackground().getImg().getRGB(x, y)==-5552961) {
						imagePosition = new Point(x,y);
					}
				}
				if(item.getCardBackground().getImg().getRGB(x, y)==-12009650) {
					notInBox= true;
					for(int i=0;i<actionPlacement.size();i++) {
						Point p = actionPlacement.get(i);
						if(x>=p.x&&x<=p.x+50&&y>=p.y&&y<=p.y+50) {
							notInBox =false;
						}
					}
					if(notInBox) {
						actionPlacement.add(new Point(x,y));
						y+=50;
					}	
				}
			}
		}
	}
	public void assembleCard(int x, int y, HashMap<Crew,DraggableIcon> crewToIcon){
		// place background
		background.setxCoordinate(x);
		background.setyCoordinate(y);
		background.start(BattleScreen.handler,false);
		
		// place item image
		itemImage.setxCoordinate(background.xCoordinate+imagePosition.x);
		itemImage.setyCoordinate(background.yCoordinate+imagePosition.y);
		itemImage.start(BattleScreen.handler,false);
		// create & place action boxes with text
			// create variables
		int lastY = y+marginWidth+itemImage.getHeight()+image_ActionBoxGap;
		Room room = null;
		String text;
		if     (item instanceof Weapon)  {room = ship.getWeaponRoom();}
		else if(item instanceof Thruster){room = ship.getGeneratorRoom();}
		
		// loop to place
		for(int i = 0; i < actions.size(); i++){
			// make box
			CrewAction action = actions.get(actions.size()-i-1);
			ActionBox actionBox = new ActionBox(BattleScreen.handler,action.getActionImg(), background.xCoordinate+actionPlacement.get(i).x , background.yCoordinate+actionPlacement.get(i).y, action, room, bs);
		    action.setActionBox(actionBox);
			boxes.add(actionBox);
		    BattleUI.actionBoxes.add(actionBox);
	
			// update variables
			lastY+=ActionBox_ActionBoxGap+actionBox.getHeight();
			

			
			if(actionBox.getActor()!=null) {
				DraggableIcon icon = crewToIcon.get(actionBox.getActor());
				icon.moveTo(actionBox.getX(),actionBox.getY());
				icon.setActionBox(actionBox);
				actionBox.setCrew(icon);
			}
		}
	}
	
	public int getWidth() {
		return background.getWidth();
	}
	public static void delete(ShipItemCard card) {
		if(card instanceof PilotCard) {
			PilotCard.delete((PilotCard)card);
			return;
		}
		ImageHandler.delete(BattleScreen.handler,card.itemImage);
		ImageHandler.delete(BattleScreen.handler,card.background);
		for(ActionBox box: card.boxes ){ ActionBox.delete(box); BattleUI.actionBoxes.remove(box);}
		card.boxes.clear();

		
	}
}
