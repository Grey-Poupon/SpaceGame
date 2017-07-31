package com.project;

import java.util.List;

import com.project.weapons.Weapon;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class BattleUI extends UI{
	private Entity overlay;
	private static Entity tooltipSeperator = new Entity(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static Weapon[] weapons = new Weapon[4];
	private static Text[] texts = new Text[4];
	private static String tooltipMenuSelection;

	private BattleScreen bs;
	private Ship pShip;
	private Ship eShip;
	
	public BattleUI (Weapon[] weapons,BattleScreen bs, Ship pShip, Ship eShip){

		updateWeapons(weapons);
		this.bs = bs;
	}
	private void clearText() {
		for(int i = 0;i<texts.length;i++) {
			Text.delete(texts[i]);
		}
	}
	public void changeTootlipSelection(String room){
		if (tooltipMenuSelection==null || !tooltipMenuSelection.equals(room)){ // only do stuff if the selcetion has changed
			tooltipMenuSelection = room;
			clearButtons();
			clearText();
			List<Button> newButtons = new ArrayList<Button>();

			if(room.equals("q")){// weapons selected
				tooltipSeperator.changeImage("res/TooltipSepration_4Sections.png",true);
				
				for(int i=0;i<weapons.length;i++){
					Weapon currentWeapon = weapons[i];
					texts[i] = new Text(currentWeapon.getWeaponInfo(),true,Main.WIDTH-591+10,Main.HEIGHT-309-30+(79*(i+1)),"Sevensegies", Font.PLAIN, 50,Color.WHITE);
					newButtons.add(new Button(Main.WIDTH-591,Main.HEIGHT-309+(78*(i)),591,75,ButtonID.battleWeapons[i],bs));
				}
			}
			else if(room.equals("w")) {
				for(int i = 0;i<4;i++) {
					texts[i] = new Text("Engine Choice "+i,true,Main.WIDTH-591+10,Main.HEIGHT-309-30+(78*(i+1)),"Sevensegies", Font.PLAIN, 50,Color.WHITE);

					newButtons.add(new Button(Main.WIDTH-591,Main.HEIGHT-309+(78*(i)),591,75,ButtonID.BattleEngineChoice,bs));

				}
			}
			else if(room.equals("e")) {
				
			}
			else if(room.equals("")) {
				
			}
			else{
				tooltipSeperator.setVisible(false);
				for(int i =0;i<texts.length;i++){
					texts[i].setVisible(false);
				}
			}
			addButtons(newButtons);
			
		}
	}
	public void updateWeapons(Weapon[] weapons){
		this.weapons = weapons;
	}




}
