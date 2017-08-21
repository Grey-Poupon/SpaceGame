package com.project.battle;

import com.project.Handler;

public class BattleHandler extends Handler {
	private BattleScreen bs;

	
	public BattleHandler(BattleScreen bs) {
		this.bs=bs;
		
	}
	public boolean checkShip(int x , int y) {
		return bs.checkClick(x,y);
	}
	
	public int getLayerClicked(int x,int y) {
		return bs.getLayerClicked(x,y);
	}
	
	public boolean checkClick(int x, int y, int button) {
		if(checkButtons(x, y,button)){ return true;}
		return checkShip(x,y);
	}
	public void updateMouse(int x,int y) {
		super.updateMouse(x, y);
		if(checkShip(x,y)) {
//			int layerNo = getLayerClicked(x,y);
//			BufferedImage img = ResourceLoader.images.get("res/mousePointer.png");
//			ImageHandler shipLayer = bs.enemyShip.getLayeredImage().layers.get(layerNo);
//			for(int pixX= 0;pixX<13;pixX++) {
//				for(int pixY = 0 ;pixY<14;pixY++) {
//
//										
//					Color col = new Color(bs.enemyShip.getLayeredImage().layers.get(0).getImg().getRGB((int)((x-shipLayer.getxCoordinate())/shipLayer.getXScale()),(int)((y-shipLayer.getyCoordinate())/shipLayer.getYScale())));
//					if(col.getAlpha()==0) {continue;}
//					int a= col.getAlpha();
//					
//					int r = (int)(col.getRed()*0.8);
//					int g = (int)(col.getGreen()*0.8);
//					int b = (int)(col.getBlue()*0.8);
//					int rgba = (a << 24) | (r << 16) | (g << 8) | b;
//					//int rgba = 0; 
//					img.setRGB(pixX,pixY,rgba);
//					
//				}
//			}
//			
			mousePointer.setImg("res/attackMousePointer.png");
			
			
			
			
			
		}
		else {mousePointer.setImg("res/mousePointer.png");}
		
	}
	
}
