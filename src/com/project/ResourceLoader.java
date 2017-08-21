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

public class ResourceLoader {
	public static  Map<String,BufferedImage> images;

	public ResourceLoader() {
		images = new TreeMap<String,BufferedImage>(String.CASE_INSENSITIVE_ORDER);
		put(images,"res/drawn ui 2.png");
		put(images,"res/loadingscreen.png");
		put(images,"res/healthseg.png");
		put(images,"res/TooltipSeparation_4Sections.png");
		put(images,"res/octiod_lazer_1_Anim.png");
		put(images,"res/mousePointer.png");
		put(images,"res/missile_spritesheet.png");
		put(images,"res/healthuncertainty.png");
		put(images,"res/appicon.png");
		put(images,"res/explosion_spritesheet.png");
		put(images,"res/attackMousePointer.png");
		for(RaceID race : RaceID.values()) {
			if(race!=RaceID.robot) {
				put(images,"res/race_portraits/"+race.toString().toLowerCase()+".png");
			}
		}
		put(images,"res/race_portraits/gen_2.png");
		put(images,"res/race_portraits/gen_3.png");
		for(int i =0; i<8;i++) {
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
