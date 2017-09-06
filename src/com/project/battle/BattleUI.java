package com.project.battle;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.project.ActionBox;
import com.project.Actionable;
import com.project.Crew;
import com.project.CrewAction;
import com.project.DraggableIcon;
import com.project.EntityID;
import com.project.ImageHandler;
import com.project.Main;
import com.project.ResourceLoader;
import com.project.ScrollableList;
import com.project.StatID;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.UI;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.engines.Engine;
import com.project.ship.Room;
import com.project.ship.Ship;
import com.project.ship.rooms.Cockpit;
import com.project.ship.rooms.GeneratorRoom;
import com.project.ship.rooms.WeaponsRoom;
import com.project.weapons.Weapon;

public class BattleUI extends UI{
	private ImageHandler overlay;
	//private static ImageHandler buttonTooltipUI = new ImageHandler(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static List<Weapon> weapons = new ArrayList<Weapon>();
  	private static TooltipSelectionID tooltipMenuSelection; 
	private static final int	tooltipButtonWidth  = 524;
	private static final int 	tooltipButtonHeight = 40;
	private static final int	genericButtonWidth  = 150;
	private static final int 	genericButtonHeight = 50;
	private static final int 	tooltipButtonHeightRight = 200;
	private static final int    xLeftListOffset     = 104; 
	private static final int    yLeftListOffset		= Main.HEIGHT - 208;
	private static final int    xRightListOffset    = Main.WIDTH  - 208; 
	private static final int    yRightListOffset	= Main.HEIGHT - 208;

	private static final int    tableTitleHeight    = 40;
	private static final int 	tableColumnWidth    = 965/5;
	
	private static final int    leftListWidth	 	= 965;
	private static final int    leftListHeight 		= tooltipButtonHeight*5;
	private static final int    rightListHeight	=208;
	private static final int    rightListWidth	=208;

	private static final int    boxGap           	= 20;
	private static final String fontName 			= "Sevensegies";
	private static final int    fontStyle 			= Font.PLAIN;
	private static final int    fontSize 			= 50;
	private static final Color  fontColour 			= Color.WHITE;
	private static ScrollableList rightHandList;
	private static BattleScreen   bs;
	private static ScrollableList tooltipList;

	private static List<Button> flavourTexts;

	private static List<DraggableIcon> actionIcons = new ArrayList<DraggableIcon>();
	public  static List<ActionBox>  actionBoxes = new ArrayList<ActionBox>();
	private static List<Button>  miscButtons = new ArrayList<Button>();
	private static List<Text> actionTableTitleText = new ArrayList<Text>();
	
	private static Ship playerShip;



	public BattleUI (BattleScreen battleScreen, Ship pShip, Ship eShip){

		bs = battleScreen;
		 flavourTexts = new ArrayList<Button>();
//		for(int i =0;i<eShip.getFlavourTexts().size();i++) {
//			flavourTexts.add(new Button(0,0,tooltipButtonWidth,5*tooltipBoxHeight,ButtonID.EnemyShip,i,true,eShip.getFlavourTexts().get(i),fontName,fontStyle,fontSize,fontColour,bs,false));
//		}
//		rightHandList = new ScrollableList(flavourTexts,xRightListOffset,yRightListOffset,tooltipBoxWidth,tooltipBoxHeight);
	}

	public static void generateRoomButtons(Crew crew, TooltipSelectionID option){
		boolean clickable = true;
	//	rightHandList.clear();
		if(bs.isPlayerIsChaser()) {
			playerShip = bs.getChaserShip();
		}else {playerShip = bs.getChasedShip();}
		
			//tooltipMenuSelection = option == TooltipSelectionID.Room ? crew.getLocationOnShip() : option;
			List<Button> tooltipButtons = new ArrayList<Button>();
			List<Button> rightTooltipButtons = new ArrayList<Button>();

			for(int i = 0;i<crew.getSpeechOptions().size(); i++){
				tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.Crew, i, true, crew.getSpeechOptions().get(i), fontName, fontStyle, fontSize, fontColour, bs,true));
			}
			
			if(crew.getRoomIn() instanceof WeaponsRoom) {

				if(!bs.isPlayerIsChaser()) {
					for(int i = 0;i<playerShip.getBackWeapons().size();i++) {
						
						Weapon w = playerShip.getBackWeapons().get(i);
						tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleWeaponsChoice, i, true, w.getWeaponInfo(), fontName, fontStyle, fontSize, fontColour, bs,true));
					}
				}else {
					for(int i = 0;i<playerShip.getFrontWeapons().size();i++) {
						Weapon w = playerShip.getFrontWeapons().get(i);
						tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleWeaponsChoice, i, true, w.getWeaponInfo(), fontName, fontStyle, fontSize, fontColour, bs,true));
					}
					
				}
			}
			else if(crew.getRoomIn() instanceof GeneratorRoom) {
				
			}
			else if(crew.getRoomIn() instanceof Cockpit) {
				for(int i = 0; i<playerShip.getEngines().size();i++) {
					Engine e = playerShip.getEngines().get(i);
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleEngineChoice, i, true, e.getName(), fontName, fontStyle, fontSize, fontColour, bs,true));
				}
			}


			if(tooltipMenuSelection == TooltipSelectionID.Stats) {
				for(int i = 0;i<crew.getStats().size();i++) {
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.Crew, i, false, Crew.statNames[i]+": "+Byte.toString(crew.getStat(StatID.values()[i])), fontName, fontStyle, fontSize, fontColour, bs,true));
				}
				clickable = false;
			}
			
			

			
			//clear 
			for(Button btn:miscButtons) {Button.delete(btn);}
			if(actionBoxes != null){clearActionImg();}
			if(tooltipList != null){ScrollableList.delete(tooltipList);}
			
			// setup scrollable lists
			tooltipList = new ScrollableList(tooltipButtons, xLeftListOffset,yLeftListOffset, leftListWidth,leftListHeight,tooltipButtonWidth,tooltipButtonHeight,clickable);
			
			if(rightTooltipButtons.size()>0) {
				rightHandList  = new ScrollableList(rightTooltipButtons,xLeftListOffset+tooltipButtonWidth+13,yLeftListOffset+2,leftListWidth,leftListHeight);
			}
			else {
				rightHandList = new ScrollableList(flavourTexts,xLeftListOffset+tooltipButtonWidth+13,yLeftListOffset+2,leftListWidth,leftListHeight);
			}
			
			
		
	}

	private static void clearActionImg() {
		for(DraggableIcon img: actionIcons) {
			DraggableIcon.delete(img);
		}
		for(ActionBox box: actionBoxes) {
			ActionBox.delete(box);
		}
		
	}
	public static void generateActionList(List<? extends Actionable> actionables,Room room) {
		// wipe tooltip
		clearTooltip();

		// initalise variables

		List<CrewAction> actions = new ArrayList<CrewAction>();
		List<Crew>       crew    = room.getCrewInRoom();
		Text name;
		
		// confirm button
		 miscButtons.add(new Button(xLeftListOffset+leftListWidth-genericButtonWidth, yLeftListOffset, genericButtonWidth, genericButtonHeight, ButtonID.BattleWeaponActionChoice, 0, true, "ffffff",fontName, fontStyle, fontSize, fontColour, bs,true));
		
		 // set crew pics
		for(int i = 0; i<crew.size();i++) {
			ImageHandler portrait = crew.get(i).getPortrait();
			portrait.start();
			int column = (i % 3) + 1;
			int row    = (i / 3) + 1;
			portrait.setVisible(true);
			portrait.setxCoordinate(xLeftListOffset+leftListWidth  - (column*portrait.getWidth()));
			portrait.setyCoordinate(yLeftListOffset+leftListHeight - (row*portrait.getHeight()));
			DraggableIcon icon = new DraggableIcon(portrait,crew.get(i), portrait.getxCoordinate(), portrait.getyCoordinate());
			actionIcons.add(icon);
		}
		
		// set Action Table
		for(int j = 0; j<actionables.size();j++) {
			// populate variables
			actions = (actionables.get(j)).getActions();
			name    = new Text(actionables.get(j).getName(), true, xLeftListOffset+(j*tableColumnWidth), yLeftListOffset);
			// set column title
			actionTableTitleText.add(name);
			
			// set the action boxes
			for(int i = 0; i<actions.size();i++) {
				BufferedImage img  = ResourceLoader.getImage("res/actionBox.png");
				ActionBox box = new ActionBox(img, xLeftListOffset+(j*tableColumnWidth), yLeftListOffset + ((img.getHeight()+ boxGap)*(i+1)), actions.get(i));
				actionBoxes.add(box);
			}
		}
	}

	public static void generateActionList(Engine engine, Room room,boolean bool) {
		clearTooltip();
		
		List<CrewAction> actions = engine.getActions();
		List<Crew>       crew    = room.getCrewInRoom();
		List<Button> tooltipButtons = new ArrayList<Button>();
		List<Button> rightTooltipButtons = new ArrayList<Button>();

		Button b =new Button(0,0,rightListWidth, rightListHeight, ButtonID.BattleEngineGraph, true,engine.getEfficiencyGraph(),bs);
		b.setDraggable(true);
		
		rightTooltipButtons.add(b);
		rightHandList = new ScrollableList(rightTooltipButtons,xRightListOffset,yRightListOffset,rightListWidth,rightListHeight);
		
		for(int i =0;i<engine.getActions().size();i++) {
			tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleEngineActionChoice, i, true, actions.get(i).getName(), fontName, fontStyle, fontSize, fontColour, bs,true));
		}
		tooltipList = new ScrollableList(tooltipButtons, xLeftListOffset,yLeftListOffset, leftListWidth,leftListHeight,tooltipButtonWidth,tooltipButtonHeight,true);
	}
	
	public static void clearTooltip() {
		if(tooltipList  != null){tooltipList.clear();}
		if(actionIcons  != null && actionBoxes != null ){clearActionImg();}
		if(rightHandList!= null){rightHandList.clear();}
		for(Button btn:miscButtons) {Button.delete(btn);}
		for(Text txt:actionTableTitleText) {Text.delete(txt);}

	}
}
