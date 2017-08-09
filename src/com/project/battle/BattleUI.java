package com.project.battle;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.project.Crew;
import com.project.EntityID;
import com.project.ImageHandler;
import com.project.Ship;
import com.project.Text;
import com.project.UI;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.weapons.Weapon;

public class BattleUI extends UI{
	private ImageHandler overlay;
	private static ImageHandler tooltipSeperator = new ImageHandler(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static Weapon[] weapons = new Weapon[4];
	private static List<Text> texts = new ArrayList<Text>();
	private static String tooltipMenuSelection;
	private static final int xButtonOffset = 602; 
	private static final int yButtonOffset = 588; 
	private static final int textSpacing = 80; 
	private static final int tooltipBoxWidth = 596;
	private static final int tooltipBoxHeight = 77;
	private static final int fontStyle = Font.PLAIN;
	private static final int fontSize = 50;
	private static final Color fontColour = Color.WHITE;
	private static BattleScreen bs;
	private Ship pShip;
	private Ship eShip;
	
	public BattleUI (Weapon[] weapons,BattleScreen bs, Ship pShip, Ship eShip){
		//generateCrewButtons(pShip.getCrew(), bs);
		updateWeapons(weapons);
		this.bs = bs;
	}
	private static void clearText() {
		for(int i = 0;i<texts.size();i++) {
			Text.delete(texts.get(i));
		}
	}
	public static void changeTootlipSelection(Crew crew){
		if ((tooltipMenuSelection==null || !tooltipMenuSelection.equals(crew.getLocationOnShip())) && crew != null ){ // only do stuff if the selcetion has changed
			tooltipMenuSelection = crew.getLocationOnShip();
			clearTooltipButtons();
			clearText();
			int speechOptionsSize = crew.getSpeechOptions().size();
			if(tooltipMenuSelection.equals("weapons")){// weapons selected
				
				tooltipSeperator.changeImage("res/TooltipSeparation_4Sections.png",true);
				renderTooltipCrewOptions(crew);
				for(int i=speechOptionsSize;i<weapons.length+speechOptionsSize;i++){
					generateTooltipButton(i, weapons[i-speechOptionsSize].getWeaponInfo(),ButtonID.BattleWeaponsChoice);
				}
			}
			else if(tooltipMenuSelection.equals("cockpit")) {
				tooltipSeperator.changeImage("res/TooltipSeparation_4Sections.png",true);
				renderTooltipCrewOptions(crew);
				for(int i = speechOptionsSize;i<4+speechOptionsSize;i++) {
					generateTooltipButton(i, "Engine Choice "+i,ButtonID.BattleEngineChoice);

				}
			}
			else if(tooltipMenuSelection.equals("teleporter")) {
				
			}
			else if(tooltipMenuSelection.equals("")) {
				
			}
			else{
				tooltipSeperator.setVisible(false);
				for(int i =0;i<texts.size();i++){
					texts.get(i).setVisible(false);
				}
			}
		}
	}
	public void updateWeapons(Weapon[] weapons){
		this.weapons = weapons;
	}
	private static void generateTooltipButton(int i, String text,ButtonID id) {
			Text text1 = new Text(text,true,xButtonOffset,yButtonOffset+(textSpacing*(i+1)),"Sevensegies", fontStyle, fontSize,fontColour);
			if(i>=texts.size()) {texts.add(text1);}
			else {texts.set(i, text1);}
			addTooltipButton(new Button(xButtonOffset,yButtonOffset+(textSpacing*(i)),tooltipBoxWidth,tooltipBoxHeight,id,i,true,bs));

		}
	
	private static void renderTooltipCrewOptions(Crew crew) {
		for(int i = 0;i<crew.getSpeechOptions().size();i++) {
			generateTooltipButton(i, crew.getSpeechOptions().get(i),ButtonID.Crew);

		}
	}



}
