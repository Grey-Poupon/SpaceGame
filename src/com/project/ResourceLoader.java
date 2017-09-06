package com.project;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import com.project.engines.Engine;
import com.project.ship.Generator;
import com.project.ship.Ship;
import com.project.weapons.Weapon;
import com.project.weapons.weapon_types.FireableWeapon;

public class ResourceLoader {
	private static Map<String,BufferedImage> images;
	private static Map<String,Weapon> shipWeapons; 
	private static Map<String,Ship> ships; 
	private static Map<String,Animation> animations;
	private static Map<String,Crew> crew;
	private static Map<String,Engine> shipEngines;
	private static HashMap<String, Generator> shipGenerators;
	public ResourceLoader() {
		images	   = new HashMap<String,BufferedImage>();
		animations = new HashMap<String,Animation>();
		shipWeapons= new HashMap<String,Weapon>();
		shipEngines = new HashMap<String, Engine>();
		crew	   = new HashMap<String,Crew>();
		ships	   = new HashMap<String,Ship>();
		shipGenerators = new HashMap<String, Generator>();
		loadImages();
		loadFont();
		loadAnimations();
		loadShipWeapons();
		loadCrew();
		loadEngines();
		loadGenerators();
		loadShip();
		
	}
	
	private void loadEngines() {
		List<CrewAction> actions = Arrays.asList(new CrewAction[] {new CrewAction("Generate",CrewActionID.Generate,StatID.engineering, 100, 10,10000),new CrewAction("Manoeuvre",CrewActionID.Manoeuvre,StatID.engineering, 100,10,10000)});

		shipEngines.put("octoidEngine", new Engine(animations.get("octoidEngine"),"MKII Octoid Thruster",new Graph(MathFunctions.square,10,10,200,200,true),actions,null));
	}
	
	private void loadGenerators() {
		List<CrewAction> actions = Arrays.asList( new CrewAction[] {new CrewAction("Generate",CrewActionID.Generate,StatID.engineering, 10, 10,10000),new CrewAction("Manoeuvre",CrewActionID.Manoeuvre,StatID.engineering, 10,10,10000)});
		shipGenerators.put("default", new Generator("Reactor2",MathFunctions.square,actions));
	}
	

	public static Map<String, Engine> getShipEngines() {
		return shipEngines;
	}

	public static void setShipEngines(Map<String, Engine> shipEngines) {
		ResourceLoader.shipEngines = shipEngines;
	}

	private void loadShip() {
		ships.put("defaultPlayer", new Ship (0,0,50f,2f,"res/matron",true,EntityID.ship,50,3.5f,false, false));
		ships.put("defaultEnemy" , new Ship (0,0,50f,2f,"res/matron",true,EntityID.ship,50,3.5f,false, true));
	}

	private void loadCrew() {
		// TODO Auto-generated method stub
	}

	private void loadShipWeapons() {
		List<CrewAction> actions = Arrays.asList(new CrewAction[] {new CrewAction("Fire",CrewActionID.Fire, StatID.gunner, 1,10,10),new CrewAction("Reload",CrewActionID.Reload,StatID.gunner, 1,10,10)});


		shipWeapons.put("default",new FireableWeapon(1, 1, 3, 1f, "Laser Mark I",DamageType.Laser, 0, ResourceLoader.animations.get("missileWithExplosion"),false,null,150,animations.get("octoidMissileLauncher"),actions,null));		

	}

	private void loadAnimations() {
		//moving
		//String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int xStartGap, int yStartGap, int xGap, int yGap, int frameRate, float scale, float xStart, float xEnd, float yStart, float yEnd, float xVel, Rectangle2D mask, boolean firstAnimation, AdjustmentID align, List<Animation> followingAnims

		animations.put("missile", new Animation("res/missileSpritesheet.png"           , 87,14,2,2,0,0,0,0,5,1,0,0,0,0,0,new Rectangle2D.Double(0,0,0,0), false,AdjustmentID.None));
		animations.put("octoidMissileProjectile", new Animation("res/octoidMissileProjectile.png", 19,11,3,2,0,0,0,0,5,1,0,0,0,0,0,new Rectangle2D.Double(0,0,0,0), false,AdjustmentID.None));
		//stationary
		//String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int xStartGap, int yStartGp, int xGap, int yGap, int frameRate, float xCoordinate, float yCoordinate, float scale, int NoOfloops, boolean firstAnimation, AdjustmentID align, List<Animation> followingAnims
		animations.put("octoidMissileLauncher", new Animation("res/octoidMissileLauncher.png", 64,20,3,2,0,0,0,0,5,1,1,1,1, false,AdjustmentID.None));
		animations.put("missileExplosion", new Animation("res/explosionSpritesheet.png", 18,20,3,3,0,0,0,0,5,1,1,5,1, false,AdjustmentID.MidUp));
		animations.put("octoidEngine", new Animation("res/octoidEngine.png",48,26,5,2,0,0,0,0,5,0,0,1,-1,false,AdjustmentID.None));
		
		
		// combined
		animations.put("missileWithExplosion",new Animation(animations.get("octoidMissileProjectile"), new Animation[] {animations.get("missileExplosion")},false));
		
		
		
	}

	public void loadImages() {
		put(images,"res/roomIcons/cockpitIcon.png");
		put(images,"res/roomIcons/weaponsRoomIcon.png");
		put(images,"res/roomIcons/generatorRoomIcon.png");
		put(images,"res/drawnUi2.png");
		put(images,"res/loadingScreen.png");
		put(images,"res/healthseg.png");
		put(images,"res/tooltipSeparation4Sections.png");
		put(images,"res/octiodLazer1Anim.png");
		put(images,"res/mousePointer.png");
		put(images,"res/missileSpritesheet.png");
		put(images,"res/octoidMissileLauncher.png");
		put(images,"res/octoidMissileProjectile.png");
		put(images,"res/octoidEngine.png");
		put(images,"res/healthUncertainty.png");
		put(images,"res/appIcon.png");
		put(images,"res/explosionSpritesheet.png");
		put(images,"res/attackMousePointer.png");
		put(images,"res/actionBox.png");
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
		return animations.get(key);
	}

	public static void putImage(String key, BufferedImage value) {
		images.put(key, value);
	}
	public static BufferedImage getImage(String key) {
		if(!images.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return images.get(key);
	}

	public static void putShipWeapon(String key, Weapon value) {
		shipWeapons.put(key, value);
	}
	public static Weapon getShipWeapon(String key) {
		if(!shipWeapons.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return shipWeapons.get(key);
	}

	public static void put(String key, Crew value) {
		crew.put(key, value);
	}
	public static Crew get(String key) {
		if(!crew.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return crew.get(key);
	}

	public static void putShip(String key, Ship value) {
		ships.put(key, value);
	}
	public static Ship getShip(String key) {
		if(!ships.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return ships.get(key);
	}

	public static Engine getShipEngine(String key) {
		if(!shipEngines.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return shipEngines.get(key);
	}
	
	public static Generator getShipGenerator(String key) {
		if(!shipGenerators.containsKey(key)) {
			throw new NoSuchElementException();
		}
		return shipGenerators.get(key);
	}
	
}
