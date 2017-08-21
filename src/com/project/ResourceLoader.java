package com.project;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import com.project.weapons.Weapon;

public class ResourceLoader {
	public static Map<String,BufferedImage> images;
	public static Map<String,Weapon> shipWeapon; 
	public static Map<String,Animation> animations;
	public static Map<String,Crew> crew;
	public ResourceLoader() {
		images = new TreeMap<String,BufferedImage>(String.CASE_INSENSITIVE_ORDER);
		put(images,"res/drawnUi2.png");
		put(images,"res/loadingScreen.png");
		put(images,"res/healthseg.png");
		put(images,"res/TooltipSeparation4Sections.png");
		put(images,"res/octiodLazer1Anim.png");
		put(images,"res/mousePointer.png");
		put(images,"res/missileSpritesheet.png");
		put(images,"res/healthUncertainty.png");
		put(images,"res/appicon.png");
		put(images,"res/explosionSpritesheet.png");
		put(images,"res/attackMousePointer.png");
		for(RaceID race : RaceID.values()) {
			if(race!=RaceID.robot) {
				put(images,"res/racePortraits/"+race.toString().toLowerCase()+".png");
			}
		}

		put(images,"res/racePortraits/gen2.png");
		put(images,"res/racePortraits/gen3.png");
		for(int i =0; i<9;i++) {
			put(images,"res/matron/data/layer"+Integer.toString(i)+".png");
		}
		
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/res/Sevensegies-Regular.ttf")));
		} catch (IOException|FontFormatException e) {
		     e.printStackTrace();
		}
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
	
}
