package com.project;

public class BattleUI extends UI{
	private Entity overlay;
	private static Entity tooltipSeperator;
	public BattleUI (){
		overlay = new Entity(0,0,"res/Drawn UI.png",true,EntityID.UI);
	}
	public static void changeTootlipSelection(String room){
		if(room.equals("q")){
			tooltipSeperator.changeImage("res/TooltipSeperator_4Seperations.png");
		}
	}

}
