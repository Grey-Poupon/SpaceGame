package com.project.phase2;

import java.util.Objects;

import com.project.battle.BattleScreen;

public class MapShip extends MapObject{

	public boolean isPlayerShip = false;
	
	
	
	public MapShip(MapTile c,boolean b) {
		super(c);
		isPlayerShip = true;
		objImg.setImg("res/appIcon.png");
	}
	
	public MapShip(MapTile mt) {
		super(mt);
		objImg.setImg("res/ship.png");
	}
	
	public boolean isPlayerShip() {
	 return isPlayerShip;
	}
	public void setIsPlayerShip(boolean b) {
		isPlayerShip = b;
	}
	public void moveTile(MapTile mt) {
		if(this.isPlayerShip()){
			for(int i =0;i<mt.objects.size();i++) {
				if(mt.objects.get(i) instanceof MapShip && mt.objects.get(i)!=this) {
					Phase2.battle();
				}
			}
		}
		super.moveTile(mt);
	}
	
	
}
