package com.project.phase2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.project.Crew;
import com.project.Handleable;
import com.project.ImageHandler;
import com.project.Text;
import com.project.ship.ResourcesID;

public class ShopMenu implements Handleable{
	int x = 200;
	int y = 100; 
	ArrayList<ShopItem> inventory = new ArrayList<>();
	ImageHandler shopBack = new ImageHandler(x,y,"res/shopFront.png", true, null);
	Crew shopKeep;
	ImageHandler shopKeepImg;
	ArrayList<Rectangle> ShopButtons ;
	ArrayList<Rectangle> TalkButtons;
	MapShip playerShip;
	boolean talking;
	Text text;
	public ShopMenu() {
		text = new Text("Eww, what kind of space hybrid are you? You look like a Borquad and a Sinbusdroll had a hideous love child",true,0,0);
		text.changeMask(x, y, shopBack.getWidth(), shopBack.getHeight());
		talking = true;
		TalkButtons = new ArrayList<Rectangle>();
		ShopButtons = new ArrayList<Rectangle>();
		for(int i =0;i<4;i++) {		
			inventory.add(new ShopItem());
		}
		shopKeep = Crew.generateRandomCrew(true);
		shopKeepImg = new ImageHandler(x+20,y+20,shopKeep.getPortrait().getImg(),true,null);
		ShopButtons.add(new Rectangle(x+shopBack.getWidth()-40,y+20,20,20));
		for(int i =0; i<inventory.size();i++) {
			ShopButtons.add(new Rectangle(x+100,y+50*(1+i),20,20));
			}
		for(int i = 0; i<4;i++) {
			TalkButtons.add(new Rectangle(x+100,y+200+50*(1+i),20,20));
		}
		text.move(x+100, y+100);
		
	}
	public void setShip(MapShip playerShip) {
		this.playerShip = playerShip;
	}
	
	
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		shopBack.render(g);
		shopKeepImg.render(g);
		if(talking) {
			text.render(g);
			for(int i =0;i<TalkButtons.size();i++ ){
				g.drawString("Impetulant fool!",(int)( TalkButtons.get(i).x+TalkButtons.get(i).getWidth()),(int)(TalkButtons.get(i).y+TalkButtons.get(i).getHeight()));
				g2d.draw(TalkButtons.get(i));
			}
			
		}
		else {
			for(int i = 0; i <inventory.size();i++) {
				g.drawString("Name: "+inventory.get(i).name + " Cost: "+inventory.get(i).cost,(int)( ShopButtons.get(i+1).x+ShopButtons.get(i+1).getWidth()),(int)(ShopButtons.get(i+1).y+ShopButtons.get(i+1).getHeight()));
			}
			g.drawString("Money: "+playerShip.getMoney(),x+shopBack.getWidth()-150,y+40);
			for(int i = 0;i<ShopButtons.size();i++){
				g2d.draw(ShopButtons.get(i));
			}
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

	public void mouseInteract(Phase2 p2, int i) {
		if(i == 0&&!talking) {
			p2.leaveShop();
		}
		else if(talking) {
			talking=false;
		}
		else  if(canAfford(playerShip,inventory.get(i-1))){
			System.out.println("YOU PAID £"+inventory.get(i-1).cost+" for a "+inventory.get(i-1).name+" enjoy you gross man.");
			playerShip.incrementMoney(-inventory.get(i-1).cost);
			playerShip.getShip().getInventory().add(inventory.get(i-1));
		}
		else {
			System.out.println("Soz m8 you can't afford this");
		}
	}
	
	
	public boolean canAfford(MapShip playerShip,ShopItem item) {
		if(playerShip.getShip().getResource(ResourcesID.Money)>item.getCost()) {		
			return true;
		}
		return false;
	}

}
