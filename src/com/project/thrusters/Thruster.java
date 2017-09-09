package com.project.thrusters;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.project.Actionable;
import com.project.Animation;
import com.project.CrewAction;
import com.project.Graph;
import com.project.Slottable;
import com.project.battle.BattleScreen;
import com.project.ship.Ship;
import com.project.ship.Slot;

public class Thruster implements Slottable,Actionable{

	private Animation engineBody;
	private Graph efficiencyGraph;
	private List<CrewAction> actions;
	private String name;
	private Slot slot;
	
	public Thruster(Animation engineBody,String name,Graph efficiencyGraph,List<CrewAction> actions,Slot slot) {
		this.engineBody = engineBody.copy();
		this.engineBody.start(false);
		this.efficiencyGraph = efficiencyGraph;
		this.actions = actions;
		this.name = name;
	}



	public void render(Graphics g, Slot slot) {
		engineBody.setxCoordinate(slot.getX());
		engineBody.setyCoordinate(slot.getY()+slot.getHeight()/2-engineBody.getTileHeight());
		if(!slot.isFront()) {
			engineBody.setxCoordinate(slot.getX()-slot.getWidth()/2);
			//wb.setxScale(-1);
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
		return new Thruster(engineBody,name,efficiencyGraph,actions,slot);
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



	public void doAction(int index,Ship ship) {
		CrewAction action = actions.get(index);
		ship.updatePowerConsumption(action);
		if(actions.get(index).getName()=="Generate") {
			ship.incResource("power", action.getPowerCost());
		}
		
	}



	public Slot getSlot() {
		return slot;
	}



	public void setSlot(Slot slot) {
		this.slot = slot;
	}



	@Override
	public void doAction(CrewAction action, BattleScreen bs) {
		// TODO Auto-generated method stub
		
	}




	
	


}
