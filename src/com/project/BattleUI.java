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

	public static AttackButton[] buttons = new AttackButton[4];
	private BattleScreen bs;
	private Ship pShip;
	private Ship eShip;
	
	public BattleUI (Weapon[] weapons,BattleScreen bs, Ship pShip, Ship eShip){

		overlay = new Entity(0,0,"res/Drawn UI.png",true,EntityID.UI);
		updateWeapons(weapons);
	}
	public static void changeTootlipSelection(String room){
		tooltipMenuSelection = room;
		
		if(room.equals("q")){
			
			tooltipSeperator.changeImage("res/TooltipSepration_4Sections.png",true);
			for(int i=0;i<weapons.length;i++){
				Weapon currentWeapon = weapons[i];
				texts[i] = new Text(currentWeapon.getWeaponInfo(),true,Main.WIDTH-591+10,Main.HEIGHT-309-40+(78*(i+1)),"Sevensegies", Font.PLAIN, 40,Color.BLACK);
				buttons[i] = new AttackButton(605,585+i*85,500,85,currentWeapon);
			}
		}
		else{
			tooltipSeperator.setVisible(false);
			for(int i =0;i<texts.length;i++){
				texts[i].setVisible(false);
			}
		}
	}
	public void updateWeapons(Weapon[] weapons){
		this.weapons = weapons;
	}

	public static void checkButtons(int x,int y){
		for(int i =0; i<buttons.length;i++){
			if(buttons[i].isInside(x, y)){
				buttons[i].activated();
			}
		}		
	}

}
