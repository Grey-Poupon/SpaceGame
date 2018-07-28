package com.project.battle;

import java.awt.Color;
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
import com.project.Graph;
import com.project.ImageHandler;
import com.project.Main;
import com.project.MathFunctions;
import com.project.PilotCard;
import com.project.ResourceLoader;
import com.project.ScrollableList;
import com.project.ShipItemCard;
import com.project.StatID;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.UI;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Generator;
import com.project.ship.ResourcesID;
import com.project.ship.Room;
import com.project.ship.Ship;
import com.project.ship.rooms.Cockpit;
import com.project.ship.rooms.GeneratorRoom;
import com.project.ship.rooms.StaffRoom;
import com.project.ship.rooms.WeaponsRoom;
import com.project.slider.SliderID;
import com.project.slider.VerticalSlider;
import com.project.weapons.Weapon;

public class BattleUI extends UI {
	private ImageHandler overlay;
	// private static ImageHandler buttonTooltipUI = new
	// ImageHandler(BattleScreen.WIDTH-591-4,BattleScreen.HEIGHT-309,false,EntityID.UI);
	private static List<Weapon> weapons = new ArrayList<Weapon>();

	// vars to do back
	private static List<? extends Actionable> lastActionables;
	private static Room lastRoom;

	private static TooltipSelectionID tooltipMenuSelection;
	private static final int tooltipButtonWidth = 524;
	private static final int tooltipButtonHeight = 40;
	private static final int genericButtonWidth = 150;
	private static final int genericButtonHeight = 50;
	private static final int tooltipButtonHeightRight = 200;
	private static final int lineWidth = 3;

	private static final int cardGap = 20;

	private static final int halfListWidth = 586;
	private static final int fullListWidth = 1017;

	private static final int mainMonitorXOffset = 85;
	private static final int mainMonitorYOffset = Main.HEIGHT - 255;
	private static final int secondMonitorXOffset = Main.WIDTH - 513;
	private static final int secondMonitorYOffset = Main.HEIGHT - 230;
	public static final int graphMonitorXOffset = Main.WIDTH - 185;
	public static final int graphMonitorYOffset = Main.HEIGHT - 310;
	
	private static final int tableTitleHeight = 40;
	private static final int tableColumnWidth = 965 / 6;
	private static int listWidth = 965;
	private static int listHeight = tooltipButtonHeight * 5;
	private static final int rightListHeight = 208;
	private static final int rightListWidth = 208;
	private static final int titleGap = 50;
	private static final int boxGap = 20;
	private static final String fontName = "Sevensegies";
	private static final int fontStyle = Font.PLAIN;
	private static final int fontSize = 50;
	private static final Color fontColour = Color.WHITE;
	private static ScrollableList rightHandList;
	private static BattleScreen bs;
	private static ScrollableList tooltipList;
	public static ScrollableList graphList;

	private static List<Button> flavourTexts;

	private static List<DraggableIcon> actionIcons = new ArrayList<DraggableIcon>();
	public static List<ActionBox> actionBoxes = new ArrayList<ActionBox>();
	private static List<DraggableIcon> manoeuvreActionIcons = new ArrayList<DraggableIcon>();
	public static List<ActionBox> manoeuvreActionBoxes = new ArrayList<ActionBox>();
	public static List<ShipItemCard> shipCards = new ArrayList<ShipItemCard>();

	private static List<Button> miscButtons = new ArrayList<Button>();
	private static List<Text> tableTitleText = new ArrayList<Text>();
	private static List<Button> actionTableTitleInfoButtons = new ArrayList<>();
	private static Button resourcesButton;
	public static Button speedInput;
	private static Ship playerShip;
	


	public BattleUI(BattleScreen battleScreen, Ship pShip, Ship eShip) {

		bs = battleScreen;
		
		flavourTexts = new ArrayList<Button>();
		String resources = "Energy:"+":" + pShip.getEnergy();


		resourcesButton = new Button(secondMonitorXOffset - 2 * rightListWidth, secondMonitorYOffset - 50,
				2 * rightListWidth, bs.main.getGraphics().getFontMetrics().getHeight() * 3, ButtonID.UI, 0, false, resources,
				bs, false);
	}

	public static void generateRoomButtons(Crew crew, TooltipSelectionID option) {
		boolean clickable = true;

		if (bs.playerIsChaser()) {
			playerShip = bs.getChaserShip();
		} else {
			playerShip = bs.getChasedShip();
		}

		List<Button> tooltipButtons = new ArrayList<Button>();
		List<Button> rightTooltipButtons = new ArrayList<Button>();

		if (crew.getRoomLeading() instanceof WeaponsRoom) {
			List<Weapon> weapons = playerShip.getFrontWeapons();
			Room room = crew.getRoomIn();
			BattleUI.generateRoomCards(weapons, room);
			
		} else if (crew.getRoomLeading() instanceof GeneratorRoom) {

			GeneratorRoom room = (GeneratorRoom) playerShip.getGeneratorRoom();
			List<Generator> generator = new ArrayList<Generator>();
			generator.add(room.getGenerator());

			BattleUI.generateRoomCards(generator, room);
			generatePilotCard();
			
			/**Images for side bar tabs - manoeuvre and speedometer**/
//			ImageHandler img1 = new ImageHandler(0, 0, ResourceLoader.getImage("res/manoeuvreTab.png"), true,EntityID.UI);
//			miscButtons.add(new Button(secondMonitorXOffset - img1.getWidth(), secondMonitorYOffset, 50, 50, ButtonID.Manoeuvres, 0, true, img1, bs));
//
//			ImageHandler img2 = new ImageHandler(0, 0, ResourceLoader.getImage("res/speedometerTab.png"), true,	EntityID.UI);
//			miscButtons.add(new Button(secondMonitorXOffset - img1.getWidth(), secondMonitorYOffset + img2.getHeight(),	50, 50, ButtonID.SpeedInput, 0, true, img2, bs));

			//generateManoeuvreActionList((Cockpit) playerShip.getCockpit());

		} else if (crew.isCaptain()) {
			StaffRoom room = playerShip.getStaffRoom();
			generateCrewMovementList(playerShip);
		}
		if (tooltipMenuSelection == TooltipSelectionID.Stats) {
			for (int i = 0; i < crew.getStats().size(); i++) {
				tooltipButtons.add(new Button(0, 0, tooltipButtonWidth, tooltipButtonHeight, ButtonID.Crew, i, false,Crew.statNames[i] + ": " + Byte.toString(crew.getStat(StatID.values()[i])), fontName, fontStyle,fontSize, fontColour, bs, true));
			}
			clickable = false;
		}


		// clear
		// clearTooltip();

		// setup scrollable lists
		tooltipList = new ScrollableList(tooltipButtons, mainMonitorXOffset, mainMonitorYOffset, listWidth, listHeight,tooltipButtonWidth, tooltipButtonHeight, clickable);

		if (rightTooltipButtons.size() > 0) {
			rightHandList = new ScrollableList(rightTooltipButtons, mainMonitorXOffset + tooltipButtonWidth + 13,mainMonitorYOffset + 2, listWidth, listHeight);
		} else {
			rightHandList = new ScrollableList(flavourTexts, mainMonitorXOffset + tooltipButtonWidth + 13,mainMonitorYOffset + 2, listWidth, listHeight);
		}

	}

	public static void generateCrewMovementList(Ship ship) {
		// clear
		clearAllBoxes();
	}

	private static void generateRoomCards(List<? extends Actionable> actionables, Room room) {

		// wipe tooltip
		if(room == lastRoom) {
			return;
		}
		clearAllBoxes();
		lastActionables = actionables;
		lastRoom = room;
		// initalise variables
		if (actionables.get(0) instanceof Weapon) {
			listWidth = halfListWidth;
		} else {
			listWidth = halfListWidth;
		}
		HashMap<Crew, DraggableIcon> crewToIcon = new HashMap<>();
		List<CrewAction> actions = new ArrayList<CrewAction>();
		List<Crew> crew = room.getCrewInRoom();
		Text name;
		int column;
		int row;

		// set crew pics
		for (int i = 0; i < crew.size(); i++) {
			ImageHandler portrait = crew.get(i).getPortrait();
			portrait.start(BattleScreen.handler,true);
			column = (i % 3);
			row = (i / 3) + 1;
			portrait.setVisible(true);

			portrait.setxCoordinate(mainMonitorXOffset + listWidth -26 - (portrait.getWidth() * (1-column))+column*3);
			portrait.setyCoordinate(mainMonitorYOffset + 12+ row*3+((row-1) * (portrait.getHeight())));
			DraggableIcon icon = new DraggableIcon(portrait,BattleScreen.handler, crew.get(i), portrait.getxCoordinate(),portrait.getyCoordinate());
			
			actionIcons.add(icon);
			crewToIcon.put(crew.get(i), icon);
		}

		// wipe shared variables
		column = -1;
		row    = -1;

		// set Action Table
		for (int j = 0; j < actionables.size(); j++) {
			ShipItemCard card = new ShipItemCard(actionables.get(j), bs);
			card.assembleCard(mainMonitorXOffset +20+ (j * (card.getWidth()+10)), mainMonitorYOffset+5, crewToIcon);// +(j*(card.getWidth()+cardGap))
			shipCards.add(card);
		}
	}

	public static void generatePilotCard(){
		
		PilotCard card = new PilotCard((Cockpit) playerShip.getCockpit(),bs);
		card.assembleCard(mainMonitorXOffset+22+shipCards.get(0).getWidth(),mainMonitorYOffset+5);
		shipCards.add(card);
	}
	

	

	
	public static void back() {
		generateRoomCards(lastActionables, lastRoom);
	}



	private static void clearAllBoxes() {
		clearLeftBox();
		clearRightBox();

	}

	private static void clearLeftBox() {
		if (tooltipList != null) {
			tooltipList.clear();
		}
		if (speedInput != null) {
			Button.delete(speedInput);
		}
		for (DraggableIcon img : actionIcons) {
			DraggableIcon.delete(img);
		}
		for (ActionBox box : actionBoxes) {
			ActionBox.delete(box);
		}
		for (Button btn : miscButtons) {
			Button.delete(btn);
		}
		for (Text txt : tableTitleText) {
			Text.delete(txt);
		}
		for (Button btn : actionTableTitleInfoButtons) {
			Button.delete(btn);
		}
		for (ShipItemCard card : shipCards) {
			ShipItemCard.delete(card);
		}

		actionIcons.clear();
		actionBoxes.clear();
		miscButtons.clear();
		tableTitleText.clear();
		// if(rightHandList!= null){rightHandList.clear();}
	}

	private static void clearRightBox() {
		for (ActionBox box : manoeuvreActionBoxes) {
			ActionBox.delete(box);
		}
		for (DraggableIcon icon : manoeuvreActionIcons) {
			DraggableIcon.delete(icon);
		}
		if (speedInput != null) {
			Button.delete(speedInput);
		}

		manoeuvreActionBoxes.clear();
		manoeuvreActionIcons.clear();

	}

	public static void updateResources(Ship pShip) {
		String resources = "Energy:"+ ":" + pShip.getEnergy();
		resourcesButton.getText().setText(resources);
	}
	
	public static void refreshRoomUI(){
		Crew crew;
		if(lastRoom instanceof WeaponsRoom){
			crew = bs.getPlayerShip().getWeaponRoom().getRoomLeader();
		}
		else if(lastRoom instanceof GeneratorRoom){
			crew = bs.getPlayerShip().getGeneratorRoom().getRoomLeader();
		}
		else{
			 crew = bs.getPlayerShip().getCockpit().getRoomLeader();
		}
		generateRoomButtons(crew, TooltipSelectionID.Room);
	}
}
