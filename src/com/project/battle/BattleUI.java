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
import com.project.engines.Engine;
import com.project.ship.Ship;
import com.project.ship.rooms.Cockpit;
import com.project.ship.rooms.GeneratorRoom;
import com.project.ship.rooms.WeaponsRoom;
import com.project.weapons.Weapon;

public class BattleUI extends UI{
	private ImageHandler overlay;
	private static ImageHandler tooltipSeperator = new ImageHandler(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static List<Weapon> weapons = new ArrayList<Weapon>();
	private static TooltipSelectionID tooltipMenuSelection; 
	private static final int	tooltipButtonWidth  = 524;
	private static final int 	tooltipButtonHeight = 40;
	private static final int 	tooltipButtonHeightRight = 200;
	private static final int    xListOffset 		= 104; 
	private static final int    yListOffset			= Main.HEIGHT - (tooltipButtonHeight*5 + 10);
	private static final int    tooltipBoxWidth 	= tooltipButtonWidth;
	private static final int    tooltipBoxHeight 	= tooltipButtonHeight*5;
	private static final String fontName 			= "Sevensegies";
	private static final int    fontStyle 			= Font.PLAIN;
	private static final int    fontSize 			= 50;
	private static final Color  fontColour 			= Color.WHITE;
	private static ScrollableList rightHandList;
	private static BattleScreen   bs;
	private static ScrollableList tooltipList;
	private static Ship playerShip;
	public BattleUI (BattleScreen battleScreen, Ship pShip, Ship eShip){

		bs = battleScreen;
		List<Button> flavourTexts = new ArrayList<Button>();
		for(int i =0;i<eShip.getFlavourTexts().size();i++) {
			flavourTexts.add(new Button(0,0,tooltipButtonWidth,5*tooltipBoxHeight,ButtonID.EnemyShip,i,true,eShip.getFlavourTexts().get(i),fontName,fontStyle,fontSize,fontColour,bs,false));
		}
		rightHandList = new ScrollableList(flavourTexts,xListOffset+tooltipButtonWidth+13,yListOffset+2,tooltipBoxWidth,tooltipBoxHeight);
		
//		List<Button> resources = new ArrayList<Button>();
//		int i = 0;
//		for(String key : pShip.getResources().keySet()) {
//			
//			flavourTexts.add(new Button(0,0,tooltipButtonWidth,5*tooltipBoxHeight,ButtonID.PlayerShip,i,true,key+": "+Integer.toString(pShip.getResources().get(key)),fontName,fontStyle,fontSize,fontColour,bs,false));
//			i++;
//		}
//		ScrollableList resourceList = new ScrollableList(flavourTexts,xListOffset,50,tooltipBoxWidth,tooltipBoxHeight);
		
		
	}

	public static void changeTootlipSelection(Crew crew, TooltipSelectionID option){
		boolean clickable = true;
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
					Button b =new Button(0,0,tooltipButtonWidth, tooltipButtonHeightRight, ButtonID.BattleEngineChoice, true,e.getEfficiencyGraph(),bs);
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleEngineChoice, i, true, "Engine Choice "+i, fontName, fontStyle, fontSize, fontColour, bs,true));
					b.setDraggable(true);
					rightTooltipButtons.add(b);
				}
			}
			
			if(tooltipMenuSelection == TooltipSelectionID.Stats) {
				for(int i = 0;i<crew.getStats().size();i++) {
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.Crew, i, false, Crew.statNames[i]+": "+Byte.toString(crew.getStat(Crew.statNames[i])), fontName, fontStyle, fontSize, fontColour, bs,true));
				}
				clickable = false;
			}
			if(tooltipList != null){ScrollableList.delete(tooltipList);}
			tooltipList = new ScrollableList(tooltipButtons, xListOffset,yListOffset, tooltipBoxWidth,tooltipBoxHeight,tooltipButtonWidth,tooltipButtonHeight,clickable);
			if(rightTooltipButtons.size()>0) {
				rightHandList.clear();
				rightHandList  = new ScrollableList(rightTooltipButtons,xListOffset+tooltipButtonWidth+13,yListOffset+2,tooltipBoxWidth,tooltipBoxHeight);
			}
			
		
	}
}
