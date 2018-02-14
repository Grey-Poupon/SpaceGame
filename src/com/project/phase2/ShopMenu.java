package com.project.phase2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.project.Crew;
import com.project.Handleable;
import com.project.ImageHandler;

public class ShopMenu implements Handleable{
	int x = 200;
	int y = 100; 
	ArrayList<ShopItem> inventory = new ArrayList<>();
	ImageHandler shopBack = new ImageHandler(x,y,"res/shopFront.png", true, null);
	Crew shopKeep;
	ImageHandler shopKeepImg;
	ArrayList<Rectangle> buttons ;
	
	public ShopMenu() {
		buttons = new ArrayList<Rectangle>();
		for(int i =0;i<4;i++) {		
			inventory.add(new ShopItem());
		}
		shopKeep = Crew.generateRandomCrew(true);
		shopKeepImg = new ImageHandler(x+20,y+20,shopKeep.getPortrait().getImg(),true,null);
		buttons.add(new Rectangle(x+shopBack.getWidth()-40,y+20,20,20));
		for(int i =0; i<inventory.size();i++) {
			buttons.add(new Rectangle(x+100,y+50*(1+i),10,10));
			}
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		shopBack.render(g);
		shopKeepImg.render(g);
		for(int i = 0; i <inventory.size();i++) {
			g.drawString("Name: "+inventory.get(i).name + " Cost: "+inventory.get(i).cost,(int)( buttons.get(i+1).x+buttons.get(i+1).getWidth()),(int)(buttons.get(i+1).y+buttons.get(i+1).getHeight()));
		}
		for(int i = 0;i<buttons.size();i++){
			g2d.draw(buttons.get(i));
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

}
