package com.project.battle;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Scrollable;

import com.project.Crew;
import com.project.EntityID;
import com.project.ImageHandler;
import com.project.Main;
import com.project.ScrollableList;
import com.project.Text;
import com.project.UI;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Ship;
import com.project.weapons.Weapon;

public class BattleUI extends UI{
	private ImageHandler overlay;
	private static ImageHandler tooltipSeperator = new ImageHandler(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static Weapon[] weapons = new Weapon[4];
	private static String tooltipMenuSelection; 
	private static final int	tooltipButtonWidth  = 596;
	private static final int 	tooltipButtonHeight = 40;
	private static final int    xListOffset 		= Main.WIDTH  - (tooltipButtonWidth    + 40); 
	private static final int    yListOffset			= Main.HEIGHT - (tooltipButtonHeight*5 + 10);
	private static final int    tooltipBoxWidth 	= tooltipButtonWidth;
	private static final int    tooltipBoxHeight 	= tooltipButtonHeight*5;
	private static final String fontName 			= "Sevensegies";
	private static final int    fontStyle 			= Font.PLAIN;
	private static final int    fontSize 			= 50;
	private static final Color  fontColour 			= Color.WHITE;
	private static BattleScreen   bs;
	private static ScrollableList tooltipList;
	private Ship pShip;
	private Ship eShip;
	
	public BattleUI (Weapon[] weapons,BattleScreen battleScreen, Ship pShip, Ship eShip){
		updateWeapons(weapons);
		bs = battleScreen;
	}

	public static void changeTootlipSelection(Crew crew){
		if ((tooltipMenuSelection==null || !tooltipMenuSelection.equals(crew.getLocationOnShip())) && crew != null ){ // only do stuff if the selcetion has changed
			tooltipMenuSelection = crew.getLocationOnShip();
			List<Button> tooltipButtons = new ArrayList<Button>();
			for(int i = 0;i<crew.getSpeechOptions().size(); i++){
				tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.Crew, i, true, crew.getSpeechOptions().get(i), fontName, fontStyle, fontSize, fontColour, bs));
			}
			
			if(tooltipMenuSelection.equals("weapons")){// weapons selected
				for(int i = 0;i<weapons.length;i++){
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleWeaponsChoice, i, true, weapons[i].getWeaponInfo(), fontName, fontStyle, fontSize, fontColour, bs));
				}		
			}
			else if(tooltipMenuSelection.equals("cockpit")) {
				for(int i = 0; i < 4;i++){
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.BattleEngineChoice, i, true, "Engine Choice "+i, fontName, fontStyle, fontSize, fontColour, bs));
				}
			}
			else if(tooltipMenuSelection.equals("teleporter")) {
				
			}
			else if(tooltipMenuSelection.equals("")) {
				
			}
			if(tooltipList != null){ScrollableList.delete(tooltipList);}
			tooltipList = new ScrollableList(tooltipButtons, xListOffset,yListOffset, tooltipBoxWidth, tooltipBoxHeight);
			
			
		}
	}
	public static void updateWeapons(Weapon[] weapons1){
		weapons = weapons1;
	}

	





}
