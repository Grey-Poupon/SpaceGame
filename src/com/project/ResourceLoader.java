package com.project;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import com.project.battle.BattleHandler;
import com.project.battle.BattleScreen;
import com.project.ship.Generator;
import com.project.ship.Ship;
import com.project.thrusters.Thruster;
import com.project.weapons.Weapon;

public class ResourceLoader {
	
	private static Map<String,BufferedImage> images;
	private static Map<String,Weapon> shipWeapons;
	private static Map<String,Ship> ships;
	private static Map<String,Animation> animations;
	private static Map<String,Crew> crew;
	private static Map<String,Thruster> shipThrusters;
	private static HashMap<String, Generator> shipGenerators;
	private static HashMap<String, CrewAction> crewActions;
	
	public ResourceLoader(){
		images	       = new HashMap<String, BufferedImage>();
		animations     = new HashMap<String, Animation>();
		shipWeapons    = new HashMap<String, Weapon>();
		shipThrusters  = new HashMap<String, Thruster>();
		crew	       = new HashMap<String, Crew>();
		ships	       = new HashMap<String, Ship>();
		shipGenerators = new HashMap<String, Generator>();
		crewActions    = new HashMap<String, CrewAction>();
		loadImages();
		BattleScreen.handler = new BattleHandler();
		loadFont();
		loadAnimations();
		loadCrewActions();
		loadShipWeapons();
		loadCrew();
		loadThrusters();
		loadGenerators();
		loadShip();
//		loadAudio();
	}
	
	public void loadAudio() {
		Sound music = new Sound("res/audio/music.wav") ;
		music.loop();	
	}
	
	private void loadCrewActions(){
		List<CrewAction> empty = new ArrayList<CrewAction>();
		List<CrewAction> fireActions = new ArrayList<CrewAction>();
		crewActions.put("basicGenerate" ,new CrewAction("Generate"  ,CrewActionID.Generate ,StatID.engineering,empty ,100,10,10000));
		crewActions.put("basicOverclock",new CrewAction("Overclock" ,CrewActionID.Overclock,StatID.engineering,empty ,1  ,10,10000));
		crewActions.put("basicFix"      ,new CrewAction("Fix"       ,CrewActionID.Fix      ,StatID.engineering,empty ,1  ,10,10000));
		crewActions.put("basicGenerate" ,new CrewAction("Generate"  ,CrewActionID.Generate ,StatID.engineering,empty ,1  ,10,10000));
		crewActions.put("basicManoeuvre",new CrewAction("Manoeuvre" ,CrewActionID.Manoeuvre,StatID.engineering,empty ,1  ,10,10000));
		crewActions.put("basicReload"   ,new CrewAction("Reload"    ,CrewActionID.Reload   ,StatID.gunner     ,empty ,1  ,10,10));
		crewActions.get("basicReload").setActionImg(images.get("res/ui/reload.png"));
		fireActions.add(crewActions.get("basicReload"));
		crewActions.put("basicFire"     ,new CrewAction("Fire"      ,CrewActionID.Fire     ,StatID.gunner,fireActions,1  ,10,10));
		crewActions.get("basicFire").setActionImg(images.get("res/ui/fire.png"));
		crewActions.put("basicSwitch"   ,new CrewAction("Switch"    ,CrewActionID.Manoeuvre,StatID.pilot ,empty      ,0  ,0,0));
		crewActions.put("basicDodge"    ,new CrewAction("Dodge"     ,CrewActionID.Manoeuvre,StatID.pilot ,empty		 ,0  ,0,0));
		crewActions.put("move"          ,new CrewAction(""          ,CrewActionID.Move     ,StatID.social,empty		 ,0  ,0,0));

	}
	private void loadThrusters() {
		List<CrewAction> actions2 = new ArrayList<CrewAction>();
		actions2.add(crewActions.get("basicGenerate"));
		
		ImageHandler background = new ImageHandler(0, 0, "res/moleCardBackground.png", true, null);
		ImageHandler portrait   = new ImageHandler(0, 0, "res/genericItemPortrait.png", true, null);
		
		shipThrusters.put("octoidEngine", new Thruster(animations.get("octoidEngine"),"MKII Octoid Thruster",new Graph(MathFunctions.square,10,10,200,200,true),actions2,null,portrait,background));
	}
	
	private void loadGenerators() {		
		List<CrewAction> actions2 = new ArrayList<CrewAction>();
		actions2.add(crewActions.get("basicGenerate"));
		actions2.add(crewActions.get("basicOverclock"));
		actions2.add(crewActions.get("basicFix"));
		
		ImageHandler background = new ImageHandler(0, 0, "res/ui/engineCard.png", true, null);
		ImageHandler portrait   = new ImageHandler(0, 0, "res/ui/generatorImage.png", true, null);

		
		shipGenerators.put("default", new Generator("Octoid Generator",MathFunctions.square,actions2,portrait,background));
	}
	

	public static Map<String, Thruster> getShipEngines() {
		return shipThrusters;
	}

	public static void setShipEngines(Map<String, Thruster> shipEngines) {
		ResourceLoader.shipThrusters = shipEngines;
	}

	private void loadShip() {
		ships.put("defaultPlayer", new Ship (0,0,50f,2f,"res/matron",true,EntityID.ship,50,3.5f,false, false));
		ships.put("defaultEnemy" , new Ship (0,0,50f,2f,"res/matron",true,EntityID.ship,50,3.5f,false, true));
	}

	private void loadCrew() {
		// TODO Auto-generated method stub
		
	}

	private void loadShipWeapons(){
		List<CrewAction> actions = new ArrayList<CrewAction>();
		actions.add(crewActions.get("basicReload"));
		actions.add(crewActions.get("basicFire"));
		ImageHandler background = new ImageHandler(0, 0, "res/ui/physicalCard.png", true, null);
		ImageHandler portrait   = new ImageHandler(0, 0, "res/ui/missileArt.png", true, null);
		shipWeapons.put("default",new Weapon(10,1, 1, 30, 1f, "Octoid Missile",true, ResourceLoader.animations.get("missileWithExplosion"),false,null,150,animations.get("octoidMissileLauncher"),actions,null,background,portrait));
	}


	private void loadAnimations() {
		//moving
		
		//String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int xStartGap, int yStartGap, int xGap, int yGap, int frameRate, float scale, float xStart, float xEnd, float yStart, float yEnd, float xVel, Rectangle2D mask, boolean firstAnimation, AdjustmentID align, List<Animation> followingAnims
		animations.put("missile", new Animation(BattleScreen.handler,"res/missileSpritesheet.png"           , 87,14,2,2,0,0,0,0,5,2.5f,0,0,0,0,0,new Rectangle2D.Double(0,0,0,0), false,AdjustmentID.None));
		animations.put("octoidMissileProjectile", new Animation(BattleScreen.handler,"res/octoidMissileProjectile.png", 19,11,3,2,0,0,0,0,5,2.5f,0,0,0,0,0,new Rectangle2D.Double(0,0,0,0), false,AdjustmentID.None));
		//stationary
		//String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int xStartGap, int yStartGp, int xGap, int yGap, int frameRate, float xCoordinate, float yCoordinate, float scale, int NoOfloops, boolean firstAnimation, AdjustmentID align, List<Animation> followingAnims
		animations.put("octoidMissileLauncher", new Animation(BattleScreen.handler,"res/octoidMissileLauncher.png", 64,20,3,2,0,0,0,0,10,1,1,0.75f,1, false,AdjustmentID.None));
		animations.put("missileExplosion", new Animation(BattleScreen.handler,"res/explosionSpritesheet.png", 18,20,3,3,0,0,0,0,5,1,1,5,1, false,AdjustmentID.MidUp_MidLeft));
		animations.put("octoidEngine", new Animation(BattleScreen.handler,"res/octoidEngine.png",48,26,5,2,0,0,0,0,5,0,0,0.75f,-1,false,AdjustmentID.None));
		//combined
		animations.put("missileWithExplosion",new Animation(BattleScreen.handler,animations.get("octoidMissileProjectile"), new Animation[] {animations.get("missileExplosion")},false));	
	}

	public void loadImages() {
		
		put(images,"res/portalGate.png");
		put(images,"res/ships/insectoid.png");
		put(images,"res/matron3/mergedimage.png");
		put(images,"res/ui/slider.png");
		put(images,"res/planet.png");
		put(images,"res/ship.png");
		put(images,"res/ui/enginesTab.png");
		put(images,"res/ui/weaponsTab.png");
		put(images,"res/ui/movementTab.png");
		put(images,"res/ui/fire.png");
		put(images,"res/ui/generatorImage.png");
		put(images,"res/ui/graphBox.png");
		put(images,"res/ui/ui.png");
		put(images,"res/ui/laserArt.png");
		put(images,"res/ui/missileArt.png");
		put(images,"res/ui/monitor1.png");
		put(images,"res/ui/monitor2.png");
		put(images,"res/ui/piloting.png");
		put(images,"res/ui/reload.png");
		put(images,"res/ui/engineCard.png");
		put(images,"res/ui/energyCard.png");
		put(images,"res/ui/physicalCard.png");
		put(images,"res/ui/concept.png");
		put(images,"res/portraitFrameEngines.png");
		put(images,"res/portraitFrameWeapons.png");
		put(images,"res/portraitFrameCockpit.png");
		put(images,"res/genericBackground.png");
		put(images,"res/genericItemPortrait.png");
		put(images,"res/roomIcons/cockpitIcon.png");
		put(images,"res/roomIcons/captain.png");
		put(images,"res/roomIcons/staffRoomIcon.png");
		put(images,"res/roomIcons/sensorRoomIcon.png");
		put(images,"res/info.png");
		put(images,"res/speedometer.png");
		put(images,"res/spaceRock.png");
		put(images,"res/moleCardBackground.png");
		put(images,"res/roomIcons/weaponsRoomIcon.png");
		put(images,"res/roomIcons/generatorRoomIcon.png");
		put(images,"res/drawnUi2.png");
		put(images,"res/manoeuvreTab.png");
		put(images,"res/speedometerTab.png");
		put(images,"res/loadingScreen.png");
		put(images,"res/healthseg.png");
		put(images,"res/tooltipSeparation4Sections.png");
		put(images,"res/octiodLazer1Anim.png");
		put(images,"res/mousePointer.png");
		put(images,"res/mouseAimingIcon1.png");
		put(images,"res/mouseAimingIcon2.png");
		put(images,"res/mouseAimingIcon3.png");
		put(images,"res/missileSpritesheet.png");
		put(images,"res/octoidMissileLauncher.png");
		put(images,"res/octoidMissileProjectile.png");
		put(images,"res/octoidEngine.png");
		put(images,"res/healthUncertainty.png");
		put(images,"res/appIcon.png");
		put(images,"res/explosionSpritesheet.png");
		put(images,"res/actionBox.png");
		put(images,"res/brokenActionBox.png");
		put(images,"res/actionBoxEmpty.png");
		put(images,"res/walkingIcon.png");
		for(RaceID race : RaceID.values()) {
			if(race!=RaceID.robot) {
				put(images,"res/racePortraits/"+race.toString()+".png");
			}
		}
		put(images,"res/racePortraits/gen2.png");
		put(images,"res/racePortraits/gen3.png");
		for(int i =0; i<8;i++) {
			put(images,"res/matron/data/layer"+Integer.toString(i)+".png");
		}
	}
	public void put(Map<String,BufferedImage> tm,String path) {
		tm.put(path,loadImage(path));
	}
	
	public BufferedImage loadImage(String path) {
		BufferedImage img =null;
		try {
			img = ImageIO.read(getClass().getResource("/"+path));
		} 
		catch (IOException e){
			e.printStackTrace();
		};
		return img;
	}
	
	public void loadFont() {
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/res/Sevensegies-Regular.ttf")));
		} catch (IOException|FontFormatException e) {
		     e.printStackTrace();
		}
	}
	
	public static void putAnimation(String key, Animation value) {
		animations.put(key, value);
	}
	
	public static Animation getAnimation(String key) {
		if(!animations.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return animations.get(key).copy();
	}

	public static void putImage(String key, BufferedImage value) {
		images.put(key, value);
	}
	public static BufferedImage getImage(String key) {
		if(!images.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return copyBufferedImage(images.get(key));
	}

	public static void putShipWeapon(String key, Weapon value) {
		shipWeapons.put(key, value);
	}
	public static Weapon getShipWeapon(String key) {
		if(!shipWeapons.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return shipWeapons.get(key).copy();
	}

	public static void put(String key, Crew value) {
		crew.put(key, value);
	}
	public static Crew get(String key) {
		if(!crew.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return crew.get(key).copy();
	}

	public static void putShip(String key, Ship value) {
		ships.put(key, value);
	}
	public static Ship getShip(String key) {
		if(!ships.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return ships.get(key).copy();
	}
	
	
	

	public static Thruster getShipEngine(String key) {
		if(!shipThrusters.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return shipThrusters.get(key).copy();
	}
	
	public static Generator getShipGenerator(String key) {
		if(!shipGenerators.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return shipGenerators.get(key).copy();
	}
	public static BufferedImage copyBufferedImage(BufferedImage img) {
		 ColorModel cm = img.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = img.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public static CrewAction getCrewAction(String key) {
		if(!crewActions.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return crewActions.get(key).copy();
	}
}
