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
	private Actionable item;
	private List<CrewAction> actions = new ArrayList<CrewAction>();
	private List<ActionBox > boxes   = new ArrayList<ActionBox >();
	private ImageHandler background;
	private Ship ship;
	private BattleScreen bs;
	private List<Point> actionPlacement = new ArrayList<Point>();
	private ImageHandler itemImage;
	private Point imagePosition;
	// Item image dimensions should always be 150,50
	
	private final int marginWidth            = 5;
	private final int image_ActionBoxGap     = 10;
	private final int ActionBox_ActionBoxGap = 10;
	private final int ActionBox_TextGap      = 5;

	public ShipItemCard(Actionable item, BattleScreen bs){
		this.item       = item;
		this.actions    = item.getActions();
		this.background = item.getCardBackground();
		this.itemImage  = item.getCardImage();
		this.bs         = bs;
		this.ship       = bs.getPlayerShip();;
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
		background.start(false);
		
		// place item image
		itemImage.setxCoordinate(background.xCoordinate+imagePosition.x);
		itemImage.setyCoordinate(background.yCoordinate+imagePosition.y);
		itemImage.start(false);
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
			CrewAction a = actions.get(actions.size()-i-1);
			ActionBox actionBox = new ActionBox(a.getActionImg(), background.xCoordinate+actionPlacement.get(i).x , background.yCoordinate+actionPlacement.get(i).y, a, room, bs);
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
		ImageHandler.delete(card.itemImage);
		ImageHandler.delete(card.background);
		for(ActionBox box: card.boxes ){ ActionBox.delete(box); BattleUI.actionBoxes.remove(box);}
		card.boxes.clear();

		
	}
}
