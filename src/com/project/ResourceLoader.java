package com.project;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.project.ship.Ship;
import com.project.weapons.Weapon;
import com.project.weapons.weapon_types.FireableWeapon;

public class ResourceLoader {
	public static Map<String,BufferedImage> images;
	public static Map<String,Weapon> shipWeapons; 
	public static Map<String,Ship> ships; 
	public static Map<String,Animation> animations;
	public static Map<String,Crew> crew;
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
		ships.put("defaultPlayer", new Ship (-0,0,0.05f,16f,"res/matron",true,EntityID.ship,50,3.5f,true));
		ships.put("defaultEnemy" , new Ship (-0,0,0.05f,16f,"res/matron",true,EntityID.ship,50,3.5f,false));
	}

	private void loadCrew() {
		// TODO Auto-generated method stub
		
	}

	private void loadShipWeapons() {
		List<Animation> weaponFiringAnimations = new ArrayList<Animation>();
		weaponFiringAnimations.add(ResourceLoader.animations.get("missile"));
		weaponFiringAnimations.add(ResourceLoader.animations.get("missileWithExplosion"));
		shipWeapons.put("Default",new FireableWeapon(1, 2, 3, 1, "Laser Mark I",DamageType.Laser, 0, 1.5f, weaponFiringAnimations));
		weaponFiringAnimations.clear();
		
	}

	private void loadAnimations() {
		//moving
		//String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int xStartGap, int yStartGap, int xGap, int yGap, int frameRate, float scale, float xStart, float xEnd, float yStart, float yEnd, float xVel, Rectangle2D mask, boolean firstAnimation, AdjustmentID align, List<Animation> followingAnims
		animations.put("missile", new Animation("res/missileSpritesheet.png"           , 87,14,2,2,0,0,0,0,5,1,0,0,0,0,0,new Rectangle2D.Double(0,0,0,0), false,AdjustmentID.None,new Animation[0]));
		
		//stationary
		//String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int xStartGap, int yStartGp, int xGap, int yGap, int frameRate, float xCoordinate, float yCoordinate, float scale, int NoOfloops, boolean firstAnimation, AdjustmentID align, List<Animation> followingAnims
		animations.put("missileExplosion", new Animation("res/explosionSpritesheet.png", 18,20,3,3,0,0,0,0,5,1,1,5,1, false,AdjustmentID.MidUp,new Animation[0]));
		
		// combined
		Animation[] followingAnims = new Animation[] {animations.get("missileExplosionAnimation")};
		animations.put("missileWithExplosion",new Animation(animations.get("missile"), followingAnims));
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
		for(int i =0; i<9;i++) {
			put(images,"res/matron/data/layer"+Integer.toString(i)+".png");
		}
	}
	
}
