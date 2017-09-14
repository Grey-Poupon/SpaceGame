package com.project.battle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.project.ActionBox;
import com.project.Actionable;
import com.project.Crew;
import com.project.CrewAction;
import com.project.DraggableIcon;
import com.project.EntityID;
import com.project.Graph;
import com.project.ImageHandler;
import com.project.Main;
import com.project.MathFunctions;
import com.project.ResourceLoader;
import com.project.ScrollableList;
import com.project.StatID;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.UI;
import com.project.Recreation.RecreationalItem;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Generator;
import com.project.ship.Room;
import com.project.ship.Ship;
import com.project.ship.rooms.Cockpit;
import com.project.ship.rooms.GeneratorRoom;
import com.project.ship.rooms.StaffRoom;
import com.project.ship.rooms.WeaponsRoom;
import com.project.thrusters.Thruster;
import com.project.weapons.Weapon;

public class BattleUI extends UI{
	private ImageHandler overlay;
	//private static ImageHandler buttonTooltipUI = new ImageHandler(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static List<Weapon> weapons = new ArrayList<Weapon>();
	
	
	//vars to do back
	private static List<? extends Actionable> lastActionables;
	private static Room lastRoom;
	
  	private static TooltipSelectionID tooltipMenuSelection; 
	private static final int 	tooltipButtonWidth   = 524;
	private static final int 	tooltipButtonHeight  = 40;
	private static final int	genericButtonWidth   = 150;
	private static final int 	genericButtonHeight  = 50;
	private static final int 	tooltipButtonHeightRight = 200;
	private static final int 	lineWidth            = 3;

	private static final int    halfListWidth      = 534;
	private static final int    fullListWidth      = 965;

	private static final int    xListOffset          = 104; 
	private static final int    yListOffset		     = Main.HEIGHT - 208;
	private static final int    xRightListOffset     = Main.WIDTH  - 208; 
	private static final int    yRightListOffset	 = Main.HEIGHT - 208;

	private static final int    tableTitleHeight     = 40;
	private static final int 	tableColumnWidth     = 965/6;
	
	private static int 		    listWidth	 	     = 965;
	private static int  	    listHeight 		     = tooltipButtonHeight*5;
	private static final int    rightListHeight	     = 208;
	private static final int    rightListWidth	     = 208;

	private static final int    titleGap             = 50;
	private static final int    boxGap           	 = 20;
	private static final String fontName 	   	 	 = "Sevensegies";
	private static final int    fontStyle 	 		 = Font.PLAIN;
	private static final int    fontSize 			 = 50;
	private static final Color  fontColour 			 = Color.WHITE;
	private static ScrollableList rightHandList;
	private static BattleScreen   bs;
	private static ScrollableList tooltipList;
	private static ScrollableList graphList;

	private static List<Button> flavourTexts;

	private static List<DraggableIcon> actionIcons = new ArrayList<DraggableIcon>();
	public  static List<ActionBox>     actionBoxes = new ArrayList<ActionBox>();
	private static List<DraggableIcon> manoeuvreActionIcons = new ArrayList<DraggableIcon>();
	public  static List<ActionBox>     manoeuvreActionBoxes = new ArrayList<ActionBox>();
	
	
	private static List<Button>        miscButtons = new ArrayList<Button>();
	private static List<Text>          actionTableTitleText = new ArrayList<Text>();
	private static List<Button> actionTableTitleInfoButtons = new ArrayList<>();
	private static Button resourcesButton;
	public  static Button speedInput;
	private static Ship playerShip;




	public BattleUI (BattleScreen battleScreen, Ship pShip, Ship eShip){

		bs = battleScreen;

		 flavourTexts = new ArrayList<Button>();
//		for(int i =0;i<eShip.getFlavourTexts().size();i++) {
//			flavourTexts.add(new Button(0,0,tooltipButtonWidth,5*tooltipBoxHeight,ButtonID.EnemyShip,i,true,eShip.getFlavourTexts().get(i),fontName,fontStyle,fontSize,fontColour,bs,false));
		String resources = "Resources:";
		for(String key: pShip.getResources().keySet()) {
			resources= resources +" "+key+":"+pShip.getResource(key); 
		}
		resourcesButton = new Button(xRightListOffset-2*rightListWidth,yRightListOffset-50,2*rightListWidth,bs.getGraphics().getFontMetrics().getHeight()*3,ButtonID.UI, 0,false,resources,bs,false);

		Button graph =new Button(0,0,pShip.getGenerator().getEfficiencyGraph().getWidth(), pShip.getGenerator().getEfficiencyGraph().getHeight(), ButtonID.BattleThrusterGraph, true,pShip.getGenerator().getEfficiencyGraph(),bs);
		graph.setDraggable(true);
		List<Button> graphEnd = new ArrayList<Button>();
		graphEnd.add(graph);
		//GO BUTTON
		graphEnd.add(new Button(0, 0, pShip.getGenerator().getEfficiencyGraph().getWidth(), pShip.getGenerator().getEfficiencyGraph().getHeight(), ButtonID.EndPhase, graphEnd.size(),true,"GO","sevensegies",Font.PLAIN,30,Color.WHITE,new ImageHandler(0,0,"res/appIcon.png",true,EntityID.UI), bs));
		graphList = new ScrollableList(graphEnd,xRightListOffset+rightListWidth-pShip.getGenerator().getEfficiencyGraph().getWidth(),yRightListOffset,pShip.getGenerator().getEfficiencyGraph().getWidth(),2*pShip.getGenerator().getEfficiencyGraph().getHeight());

	}

	public static void generateRoomButtons(Crew crew, TooltipSelectionID option){
		boolean clickable = true;
		if(bs.playerIsChaser()) {
			playerShip = bs.getChaserShip();
		}else {playerShip = bs.getChasedShip();}
		
			List<Button> tooltipButtons = new ArrayList<Button>();
			List<Button> rightTooltipButtons = new ArrayList<Button>();

			
			if(crew.getRoomIn() instanceof WeaponsRoom) {	
				
				List<Weapon> weapons = playerShip.getFrontWeapons();
				Room         room    = crew.getRoomIn();
				
				BattleUI.generateActionList(weapons, room);
			}
			else if(crew.getRoomIn() instanceof GeneratorRoom) {
				
				GeneratorRoom room = (GeneratorRoom) playerShip.getGeneratorRoom();
				List<Generator> generator = new ArrayList<Generator>();
				generator.add(room.getGenerator());
				
				BattleUI.generateActionList(generator, room);
				
				ImageHandler img1 = new ImageHandler(0, 0, ResourceLoader.getImage("res/manoeuvreTab.png"), true, EntityID.UI);
				miscButtons.add(new Button(xListOffset+halfListWidth-img1.getWidth(),yListOffset                 ,50,50,ButtonID.Manoeuvres,0,true,img1,bs));
				
				ImageHandler img2 = new ImageHandler(0, 0, ResourceLoader.getImage("res/speedometerTab.png"), true, EntityID.UI);
				miscButtons.add(new Button(xListOffset+halfListWidth-img2.getWidth(),yListOffset+img2.getHeight(),50,50,ButtonID.SpeedInput,0,true,img2,bs));
			
				generateManoeuvreActionList((Cockpit)playerShip.getCockpit());

					

			}	
			else if(crew.isCaptain()) {
				StaffRoom room =  playerShip.getStaffRoom();
				generateActionList(room.getItems(),room);
			}


			if(tooltipMenuSelection == TooltipSelectionID.Stats) {
				for(int i = 0;i<crew.getStats().size();i++) {
					tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.Crew, i, false, Crew.statNames[i]+": "+Byte.toString(crew.getStat(StatID.values()[i])), fontName, fontStyle, fontSize, fontColour, bs,true));
				}
				clickable = false;
			}

			//clear 
			//clearTooltip();
			
			// setup scrollable lists
			tooltipList = new ScrollableList(tooltipButtons, xListOffset,yListOffset, listWidth,listHeight,tooltipButtonWidth,tooltipButtonHeight,clickable);
			
			if(rightTooltipButtons.size()>0) {
				rightHandList  = new ScrollableList(rightTooltipButtons,xListOffset+tooltipButtonWidth+13,yListOffset+2,listWidth,listHeight);
			}
			else {
				rightHandList = new ScrollableList(flavourTexts,xListOffset+tooltipButtonWidth+13,yListOffset+2,listWidth,listHeight);
			}

	}

	public static void generateActionList(List<? extends Actionable> actionables,Room room) {
		
		// wipe tooltip
		clearAllBoxes();
		lastActionables = actionables;
		lastRoom = room;
		// initalise variables
		if(actionables.get(0) instanceof Weapon) {
			listWidth = fullListWidth;
		}
		else {
			listWidth = halfListWidth;
		}
		HashMap<Crew,DraggableIcon> crewToIcon = new HashMap<>();
		List<CrewAction> actions = new ArrayList<CrewAction>();
		List<Crew>       crew    = room.getCrewInRoom();
		Text name;
		int column;
		int row;


		
		 // set crew pics
		for(int i = 0; i<crew.size();i++) {
			ImageHandler portrait = crew.get(i).getPortrait();
			portrait.start();
			column = (i % 3);
			row    = (i / 3) +1;
			portrait.setVisible(true);
			portrait.setxCoordinate(xListOffset+listWidth-(portrait.getWidth()*(3-column)));
			portrait.setyCoordinate(yListOffset+listHeight - (row*portrait.getHeight()));
			DraggableIcon icon = new DraggableIcon(portrait,crew.get(i), portrait.getxCoordinate(), portrait.getyCoordinate());
			actionIcons.add(icon);
			crewToIcon.put(crew.get(i), icon);
		}
		
		// wipe shared variables
		column = -1;
		row = -1;
		
		// set Action Table
		for(int j = 0; j<actionables.size();j++) {
			// tick variables
			column++;
			
			// populate variables
			row = -1;
			actions = (actionables.get(j)).getActions();

			name    = new Text(actionables.get(j).getName(), true, xListOffset+(column*tableColumnWidth), yListOffset,bs);
			
			ButtonID infoID = null;
			if(room instanceof WeaponsRoom) {
				infoID = ButtonID.WeaponInfo;
			}
			else if(room instanceof StaffRoom) {
				infoID = ButtonID.RecreationalInfo;
			}
			else if(room instanceof GeneratorRoom) {
				infoID = ButtonID.GeneratorInfo;
			}
			Button infoButton = new Button(xListOffset+(j*tableColumnWidth)+10+name.getOnScreenWidth(), yListOffset+name.getOnScreenHeight()/2-ResourceLoader.getImage("res/info.png").getHeight()/2, 20, 20,infoID , j, true,new ImageHandler(0, 0, "res/info.png", true, EntityID.UI), bs);


			// set column title
			actionTableTitleText.add(name);
			//set info text button
			actionTableTitleInfoButtons.add(infoButton);
			
			// set the action boxes
			for(int i = 0; i<actions.size();i++) {
				// tick variables
				if(row == 1) {column++; row = -1;}
				row++;

				BufferedImage img  = ResourceLoader.getImage("res/actionBox.png");

				ActionBox box = new ActionBox(img, xListOffset+(column*tableColumnWidth), yListOffset + titleGap +((img.getHeight()+ boxGap)*(row)), actions.get(i),room,bs);

				actionBoxes.add(box);
				
				// put crew back into their old positions
				if(box.getActor()!=null) {
					DraggableIcon icon = crewToIcon.get(box.getActor());
					icon.moveTo(box.getX(),box.getY());
					icon.setActionBox(box);
					box.setCrew(icon);
				}
			}
		}
	}
	public static void generateSpeedInput() {
		// clear
		clearRightBox();
		Graph speed  = new Graph(MathFunctions.speedInput,300, 50,300f);
		speed.setDraggable(true);
		Button speedbtn = new Button(xListOffset+halfListWidth+20, yListOffset+50, 300, 50, ButtonID.Graph, true, speed, bs);
		speedbtn.setDraggable(true);
		speedbtn.addSpeedImg(ResourceLoader.getImage("res/speedometer.png"),300f);
		speedInput = (speedbtn);
	}
	public static void generateManoeuvreActionList(Cockpit room) {
		// clear
		clearRightBox();
		// setup variables
		List<CrewAction> manoeuvres = room.getManoeuvres();
		Crew pilot = room.getRoomLeader();
		int row    = -1;
		int column = 0;
		
		// setup pilot pic
		ImageHandler portrait = pilot.getPortrait();
		portrait.start();
		portrait.setVisible(true);
		portrait.setxCoordinate(xListOffset+fullListWidth);
		portrait.setyCoordinate(yListOffset);
		DraggableIcon icon = new DraggableIcon(portrait,pilot, portrait.getxCoordinate(), portrait.getyCoordinate());
		manoeuvreActionIcons.add(icon);
		
		// setup manoeuvre actions
		for(int i = 0;i<manoeuvres.size();i++) {
			if(row == 2) {column++; row = -1;}
			row++;

			BufferedImage img  = ResourceLoader.getImage("res/actionBox.png");

			ActionBox box = new ActionBox(img, xListOffset+lineWidth+halfListWidth+(column*tableColumnWidth), yListOffset +((img.getHeight()+ boxGap)*(row)), manoeuvres.get(i),room,bs);



			manoeuvreActionBoxes.add(box);
			
		}
		
		
	}
	

	public static void back() {
		generateActionList(lastActionables,lastRoom);
		
		
	}
	
	
	public static void generateInfo(Actionable actionable) {
		clearAllBoxes();
		List<Button> tooltipButtons = actionable.getInfoButtons(fullListWidth,tooltipButtonHeight,bs);
		tooltipButtons.add(new Button(0,0,fullListWidth, tooltipButtonHeight, ButtonID.Back, tooltipButtons.size(),true,"Back",bs,true));
		tooltipList = new ScrollableList(tooltipButtons, xListOffset,yListOffset, fullListWidth,listHeight,fullListWidth,tooltipButtonHeight,true);;
	}

	
	public static void clearAllBoxes() {
		clearLeftBox();
		clearRightBox();
	}
	public static void clearLeftBox() {
		if(tooltipList  != null){tooltipList.clear();}
		if(speedInput   != null) {Button.delete(speedInput);}
		for(DraggableIcon img: actionIcons) 		  {DraggableIcon.delete(img);}
		for(ActionBox     box: actionBoxes)   		  {ActionBox.delete(box);}
		for(Button        btn: miscButtons)           {Button.delete(btn);}
		for(Text          txt: actionTableTitleText)  {Text.delete(txt);}
		for(Button btn:actionTableTitleInfoButtons) {Button.delete(btn);}

		actionIcons.clear();
		actionBoxes.clear();
		miscButtons.clear();
		actionTableTitleText.clear();
		//if(rightHandList!= null){rightHandList.clear();}
	}
	private static void clearRightBox() {
		for(ActionBox     box :manoeuvreActionBoxes) {ActionBox    .delete(box);}
		for(DraggableIcon icon:manoeuvreActionIcons) {DraggableIcon.delete(icon);}
		if(speedInput!=null) {Button.delete(speedInput);}

		manoeuvreActionBoxes.clear();
		manoeuvreActionIcons.clear();
		
	}
	
	
	public static void updateResources(Ship pShip) {
		String resources = "Resources:";
		for(String key: pShip.getResources().keySet()) {
			resources= resources +" "+key+":"+pShip.getResource(key); 
		}
		resourcesButton.getText().setText(resources);
	}
}
