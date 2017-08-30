package com.project;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.NoSuchElementException;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.project.ship.Ship;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;
import com.project.weapons.weapon_types.FireableWeapon;

public class ResourceLoader {
	private static Map<String,BufferedImage> images;
	private static Map<String,Weapon> shipWeapons; 
	private static Map<String,Ship> ships; 
	private static Map<String,Animation> animations;
	private static Map<String,Crew> crew;
	public ResourceLoader() {
		images	   = new HashMap<String,BufferedImage>();
		animations = new HashMap<String,Animation>();
		shipWeapons= new HashMap<String,Weapon>();
		crew	   = new HashMap<String,Crew>();
		ships	   = new HashMap<String,Ship>();

		loadImages();
		loadFont();
		loadAnimations();
		loadShipWeapons();
		loadCrew();
		loadShip();
	}
	
	private void loadShip() {
		ships.put("defaultPlayer", new Ship (0,0,50f,2f,"res/matron",true,EntityID.ship,50,3.5f,true, false));
		ships.put("defaultEnemy" , new Ship (0,0,50f,2f,"res/matron",true,EntityID.ship,50,3.5f,true, true));
	}

	private void loadCrew() {
		// TODO Auto-generated method stub
		
	}

	private void loadShipWeapons() {
		shipWeapons.put("default",new FireableWeapon(1, 1, 3, 1f, "Laser Mark I",DamageType.Laser, 0, ResourceLoader.animations.get("missileWithExplosion"),false,null,150,animations.get("octoidMissileLauncher")));		

	}

	private void loadAnimations() {
		//moving
		//String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int xStartGap, int yStartGap, int xGap, int yGap, int frameRate, float scale, float xStart, float xEnd, float yStart, float yEnd, float xVel, Rectangle2D mask, boolean firstAnimation, AdjustmentID align, List<Animation> followingAnims

		animations.put("missile", new Animation("res/missileSpritesheet.png"           , 87,14,2,2,0,0,0,0,5,1,0,0,0,0,0,new Rectangle2D.Double(0,0,0,0), false,AdjustmentID.None));
		animations.put("octoidMissileProjectile", new Animation("res/octoidMissileProjectile.png", 32,32,3,2,0,0,0,0,5,1,0,0,0,0,0,new Rectangle2D.Double(0,0,0,0), false,AdjustmentID.None));
		//stationary
		//String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int xStartGap, int yStartGp, int xGap, int yGap, int frameRate, float xCoordinate, float yCoordinate, float scale, int NoOfloops, boolean firstAnimation, AdjustmentID align, List<Animation> followingAnims
		animations.put("octoidMissileLauncher", new Animation("res/octoidMissileLauncher.png", 64,20,3,2,0,0,0,0,5,1,1,2,1, false,AdjustmentID.None));
		animations.put("missileExplosion", new Animation("res/explosionSpritesheet.png", 18,20,3,3,0,0,0,0,5,1,1,5,1, false,AdjustmentID.MidUp));
		
		// combined
		animations.put("missileWithExplosion",new Animation(animations.get("octoidMissileProjectile"), new Animation[] {animations.get("missileExplosion")},false));
		
		
		
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
	
	public void put(Map<String,BufferedImage> tm,String path) {
		tm.put(path,loadImage(path));
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
	public void loadImages() {
		put(images,"res/drawnUi2.png");
		put(images,"res/loadingScreen.png");
		put(images,"res/healthseg.png");
		put(images,"res/tooltipSeparation4Sections.png");
		put(images,"res/octiodLazer1Anim.png");
		put(images,"res/mousePointer.png");
		put(images,"res/missileSpritesheet.png");
		put(images,"res/octoidMissileLauncher.png");
		put(images,"res/octoidMissileProjectile.png");
		put(images,"res/healthUncertainty.png");
		put(images,"res/appIcon.png");
		put(images,"res/explosionSpritesheet.png");
		put(images,"res/attackMousePointer.png");
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
	
}
