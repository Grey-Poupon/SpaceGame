package com.project.thrusters;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.project.Actionable;
import com.project.Animation;
import com.project.Crew;
import com.project.CrewAction;
import com.project.Graph;
import com.project.ImageHandler;
import com.project.Slottable;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.ship.ResourcesID;
import com.project.ship.Ship;
import com.project.ship.Slot;

public class Thruster implements Slottable,Actionable{

	private Animation engineBody;
	private Graph efficiencyGraph;
	private List<CrewAction> actions;
	private String name;
	private Slot slot;
	private ImageHandler thrusterImg;
	private ImageHandler backgroundImg;

	
	public Thruster(Animation engineBody,String name,Graph efficiencyGraph,List<CrewAction> actions,Slot slot, ImageHandler thrusterImg, ImageHandler backgroundImg) {
		this.engineBody = engineBody.copy();
		this.engineBody.start(false);
		this.efficiencyGraph = efficiencyGraph;
		this.actions = actions;
		this.name = name;
		this.thrusterImg   = thrusterImg;
		this.backgroundImg = backgroundImg;
	}

	public void render(Graphics g, Slot slot) {
		engineBody.setxCoordinate(slot.getX());
		engineBody.setyCoordinate(slot.getY()+slot.getHeight()/2-engineBody.getTileHeight());
		if(!slot.isFront()) {
			engineBody.setxCoordinate((float)(slot.getX()-slot.getWidth()/2));
		}
		engineBody.render(g);
	}

	@Override
	public Animation getSlotItemBody() {
		// TODO Auto-generated method stub
		return engineBody;
	}

	public Animation getEngineBody() {
		return engineBody;
	}

	public void setEngineBody(Animation engineBody) {
		this.engineBody = engineBody;
	}

	public Graph getEfficiencyGraph() {
		return efficiencyGraph;
	}

	public void setEfficiencyGraph(Graph efficiencyGraph) {
		this.efficiencyGraph = efficiencyGraph;
	}

	public Thruster copy() {
		return new Thruster(engineBody,name,efficiencyGraph,actions,slot,thrusterImg,backgroundImg);
	}

	
	public List<CrewAction> getActions() {
		return actions;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Integer> getSpeeds(){
		ArrayList<Integer> speeds  = new ArrayList<Integer>();
		for(int i =1;i<6;i++) {
			speeds.add(i*100);
		}
		return speeds;
	}

	public Slot getSlot() {
		return slot;
	}



	public void setSlot(Slot slot) {
		this.slot = slot;
	}



	@Override
	public void doAction(Crew crew,CrewAction action, Ship ship, BattleScreen bs) {
		if(action.getName()=="Generate" && action.isOffCooldown()) {
			ship.incEnergy(-action.getPowerCost());
		}
		action.updateCooldown();	
		
	}




	public String getFlavorText() {
		// TODO Auto-generated method stub
		return null;
	}
	public ImageHandler getCardBackground() {
		// TODO Auto-generated method stub
		return backgroundImg.copy();
	}



	@Override
	public ImageHandler getCardImage() {
		return thrusterImg.copy();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}






	
	


}
