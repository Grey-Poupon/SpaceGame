package com.project.engines;

import java.awt.Graphics;

import com.project.Animation;
import com.project.Graph;
import com.project.Slottable;
import com.project.ship.Slot;

public class Engine implements Slottable{

	private Animation engineBody;
	private Graph efficiencyGraph;
	
	
	
	public Engine(Animation engineBody,Graph efficiencyGraph) {
		this.engineBody = engineBody.copy();
		this.engineBody.start(false);
		this.efficiencyGraph = efficiencyGraph;
	}

	@Override
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
		return new Engine(engineBody,efficiencyGraph);
	}
	
	


}
