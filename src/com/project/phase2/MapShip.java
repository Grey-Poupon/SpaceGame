package com.project.phase2;

public class MapShip extends MapObject{

	public boolean isPlayerShip = false;
	
	public MapShip(MapTile c,boolean b) {
		super(c);
		isPlayerShip = true;
		objImg.setImg("res/matron3/mergedimage.png");
	}
	
	public MapShip(MapTile mt) {
		super(mt);
		objImg.setImg("res/ships/insectoid.png");
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
				if(!mt.objects.get(i).tileContained.containsPlayer())mt.objects.get(i).interact(this);
			}
		}
		super.moveTile(mt);
	}
	
	public void interact(MapShip ship) {
		if(!this.isPlayerShip) {
			Phase2.battle();
		}
	}
	
}
