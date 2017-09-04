package com.project.engines;

import java.awt.Graphics;
import java.util.List;

import com.project.Actionable;
import com.project.Animation;
import com.project.CrewAction;
import com.project.Graph;
import com.project.Slottable;
import com.project.ship.Slot;

public class Engine implements Slottable,Actionable{

	private Animation engineBody;
	private Graph efficiencyGraph;
	private List<CrewAction> actions;
	private String name;
	
	
	public Engine(Animation engineBody,String name,Graph efficiencyGraph,List<CrewAction> actions) {
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

	public Engine copy() {
		return new Engine(engineBody,name,efficiencyGraph,actions);
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
	
	


}
