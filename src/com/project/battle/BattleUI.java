package com.project.battle;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.project.Crew;
import com.project.EntityID;
import com.project.ImageHandler;
import com.project.Main;
import com.project.ScrollableList;
import com.project.TooltipSelectionID;
import com.project.UI;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Ship;
import com.project.ship.rooms.Cockpit;
import com.project.ship.rooms.EngineRoom;
import com.project.ship.rooms.WeaponsRoom;
import com.project.weapons.Weapon;

public class BattleUI extends UI{
	private ImageHandler overlay;
	private static ImageHandler tooltipSeperator = new ImageHandler(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static List<Weapon> weapons = new ArrayList<Weapon>();
	private static TooltipSelectionID tooltipMenuSelection; 
	private static final int	tooltipButtonWidth  = 524;
	private static final int 	tooltipButtonHeight = 40;
	private static final int    xListOffset 		= 104; 
	private static final int    yListOffset			= Main.HEIGHT - (tooltipButtonHeight*5 + 10);
	private static final int    tooltipBoxWidth 	= tooltipButtonWidth;
	private static final int    tooltipBoxHeight 	= tooltipButtonHeight*5;
	private static final String fontName 			= "Sevensegies";
	private static final int    fontStyle 			= Font.PLAIN;
	private static final int    fontSize 			= 50;
	private static final Color  fontColour 			= Color.WHITE;
	private static BattleScreen   bs;
	private static ScrollableList tooltipList;

	public BattleUI (BattleScreen battleScreen, Ship pShip, Ship eShip){

		bs = battleScreen;
		List<Button> flavourTexts = new ArrayList<Button>();
		for(int i =0;i<eShip.getFlavourTexts().size();i++) {
			flavourTexts.add(new Button(0,0,tooltipButtonWidth,5*tooltipBoxHeight,ButtonID.EnemyShip,i,true,eShip.getFlavourTexts().get(i),fontName,fontStyle,fontSize,fontColour,bs,false));
		}
		ScrollableList flavourTextList = new ScrollableList(flavourTexts,xListOffset+tooltipButtonWidth+13,yListOffset+2,tooltipBoxWidth,tooltipBoxHeight);
	}

	public static void changeTootlipSelection(Crew crew, TooltipSelectionID option){
		boolean clickable = true;
		
			//tooltipMenuSelection = option == TooltipSelectionID.Room ? crew.getLocationOnShip() : option;
			List<Button> tooltipButtons = new ArrayList<Button>();
			for(int i = 0;i<crew.getSpeechOptions().size(); i++){
				tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.Crew, i, true, crew.getSpeechOptions().get(i), fontName, fontStyle, fontSize, fontColour, bs,true));
			}
			
			if(crew.getRoomIn() instanceof WeaponsRoom) {
				WeaponsRoom room = (WeaponsRoom) crew.getRoomIn();
				if(room.isChased()) {
					for(int i = 0;i<room.getBackWeapons().size();i++) {
						
						Weapon w = room.getBackWeapons().get(i);
						tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleWeaponsChoice, i, true, w.getWeaponInfo(), fontName, fontStyle, fontSize, fontColour, bs,true));
					}
				}else {
					for(int i = 0;i<room.getFrontWeapons().size();i++) {
						Weapon w = room.getFrontWeapons().get(i);
						tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleWeaponsChoice, i, true, w.getWeaponInfo(), fontName, fontStyle, fontSize, fontColour, bs,true));
					}
				}			
			}
			else if(crew.getRoomIn() instanceof EngineRoom) {
				
			}
			else if(crew.getRoomIn() instanceof Cockpit) {
				for(int i = 0; i < 4;i++){
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleEngineChoice, i, true, "Engine Choice "+i, fontName, fontStyle, fontSize, fontColour, bs,true));
				}
			}
			
			
			
//			if(tooltipMenuSelection == TooltipSelectionID.Weapons){// weapons selected
//				for(int i = 0;i<weapons.length;i++){
//					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleWeaponsChoice, i, true, weapons[i].getWeaponInfo(), fontName, fontStyle, fontSize, fontColour, bs,true));
//				}		
//			}
//			else if(tooltipMenuSelection == TooltipSelectionID.Cockpit) {
//				for(int i = 0; i < 4;i++){
//					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleEngineChoice, i, true, "Engine Choice "+i, fontName, fontStyle, fontSize, fontColour, bs,true));
//				}
//			}
			if(tooltipMenuSelection == TooltipSelectionID.Stats) {
				for(int i = 0;i<crew.getStats().size();i++) {
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.Crew, i, false, Crew.statNames[i]+": "+Byte.toString(crew.getStat(Crew.statNames[i])), fontName, fontStyle, fontSize, fontColour, bs,true));
				}
				clickable = false;
			}
			if(tooltipList != null){ScrollableList.delete(tooltipList);}
			tooltipList = new ScrollableList(tooltipButtons, xListOffset,yListOffset, tooltipBoxWidth,tooltipBoxHeight,tooltipButtonWidth,tooltipButtonHeight,clickable);
			
			
		
	}
}
