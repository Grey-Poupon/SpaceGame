package com.project;

import java.util.List;
import java.util.Observer;

import com.project.weapons.Weapon;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class BattleUI extends UI{
	private Entity overlay;
	private static Entity tooltipSeperator = new Entity(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static Weapon[] weapons = new Weapon[4];
	private static List<Text> texts = new ArrayList<Text>();;
	private static String tooltipMenuSelection;
	
	private static final int xTextOffset = 619; 
	private static final int yTextOffset = 561; 
	private static final int textSpacing = 79; 
	private static final int tooltipBoxWidth = 591;
	private static final int tooltipBoxHeight = 75;
	private static final int fontStyle = Font.PLAIN;
	private static final int fontSize = 50;
	private static final int xCrewButtonOffset = 2;
	private static final int yCrewButtonOffset = 125;
	private static final int crewButtonHeight = 120;
	private static final int crewButtonWidth = 120;
	private static final int crewButtonSpacing = 123;

	private static final Color fontColour = Color.WHITE;

	
	private static BattleScreen bs;
	private Ship pShip;
	private Ship eShip;
	
	public BattleUI (Weapon[] weapons,BattleScreen bs, Ship pShip, Ship eShip){
		generateCrewButtons(pShip.getCrew(), bs);
		updateWeapons(weapons);
		this.bs = bs;
	}
	private static void clearText() {
		for(int i = 0;i<texts.size();i++) {
			Text.delete(texts.get(i));
		}
	}
	public static void changeTootlipSelection(Crew crew){
		if ((tooltipMenuSelection==null || !tooltipMenuSelection.equals(crew.locationOnShip)) && crew != null ){ // only do stuff if the selcetion has changed
			tooltipMenuSelection = crew.locationOnShip;
			clearTooltipButtons();
			clearText();
			int speechOptionsSize = crew.speechOptions.size();
			if(tooltipMenuSelection.equals("weapons")){// weapons selected
				
				tooltipSeperator.changeImage("res/TooltipSepration_4Sections.png",true);
				renderTooltipCrewOptions(crew);
				for(int i=speechOptionsSize;i<weapons.length+speechOptionsSize;i++){
					generateTooltipButton(i, weapons[i-speechOptionsSize].getWeaponInfo());
				}
			}
			else if(tooltipMenuSelection.equals("cockpit")) {
				tooltipSeperator.changeImage("res/TooltipSepration_4Sections.png",true);
				renderTooltipCrewOptions(crew);
				for(int i = speechOptionsSize;i<4+speechOptionsSize;i++) {
					generateTooltipButton(i, "Engine Choice "+i);

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
	public static void generateCrewButtons(List<Crew> crew, Observer obs) {
		for(int i = 0; i < crew.size() ; i++) {
			addCrewButton(new Button(xCrewButtonOffset, yCrewButtonOffset+(i*crewButtonSpacing), crewButtonWidth, crewButtonHeight, ButtonID.Crew,i, obs));
		}
	}
	public void updateWeapons(Weapon[] weapons){
		this.weapons = weapons;
	}
	private static void generateTooltipButton(int i, String text) {
			Text text1 = new Text(text,true,xTextOffset,yTextOffset+(textSpacing*(i+1)),"Sevensegies", fontStyle, fontSize,fontColour);
			if(i>=texts.size()) {texts.add(text1);}
			else {texts.set(i, text1);}
			addTooltipButton(new Button(xTextOffset,yTextOffset+(textSpacing*(i)),tooltipBoxWidth,tooltipBoxHeight,ButtonID.BattleEngineChoice,i,bs));

		}
	
	private static void renderTooltipCrewOptions(Crew crew) {
		for(int i = 0;i<crew.speechOptions.size();i++) {
			generateTooltipButton(i, crew.speechOptions.get(i));

		}
	}



}
