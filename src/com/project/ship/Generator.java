package com.project.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.project.ActionCooldown;
import com.project.Actionable;
import com.project.Crew;
import com.project.CrewAction;
import com.project.CrewActionID;
import com.project.FuelTypeID;
import com.project.Graph;
import com.project.ImageHandler;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.rooms.GeneratorRoom;

public class Generator implements Actionable {
	private List<CrewAction> actions;
	private String name;
	private boolean canGenerate=false;
	private boolean exploded=false;
	private FuelTypeID fuelType;
	private Overclockable overclock;
	private Function<Double,Double> efficiencyFunction;
	private Graph efficiencyGraph; 
	private ImageHandler generatorImg;
	private ImageHandler backgroundImg;
	private GeneratorRoom room;
	
	public Generator(String name,Function<Double,Double> function,List<CrewAction> actions,ImageHandler generatorImg,ImageHandler backgroundImg) {
		this.name = name;
		this.actions = actions;
		this.efficiencyFunction = function;
		this.efficiencyGraph = new Graph(function,10,10,150,100,true);
		this.efficiencyGraph.setDraggable(false);
		this.generatorImg  = generatorImg;
		this.backgroundImg = backgroundImg;
	}

	public double getPower(double amountOfFuel) {
		double effiency = efficiencyFunction.apply(amountOfFuel);
		if (effiency<0) {
			explode();
			return -1;
		}
		return amountOfFuel * effiency;
	}
	
	public void explode() {
		exploded = true;
	}
	public int getSafetyCap() {
		return overclock.getSafetyCap();
	}

	public int getExplodeChance() {
		return overclock.getExplodeChance();
	}

	@Override
	public List<CrewAction> getActions() {
		// TODO Auto-generated method stub
		return actions;
	}


	public void generate() {
		setCanGenerate(true);
		this.room.ship.incEnergy(50);
	}

	private void dangerRollTable() {
		System.out.println("You blew up XD");
	}


	public Graph getEfficiencyGraph() {
		return efficiencyGraph;
	}

	public void setEfficiencyGraph(Graph efficiencyGraph) {
		this.efficiencyGraph = efficiencyGraph;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void doAction(Crew crew,CrewAction action, Ship ship, BattleScreen bs) {
	if(action.getActionType() == CrewActionID.Generate && action.isOffCooldown()) {
		generate();
	}
	if(action.getActionType()==CrewActionID.Overclock && action.isOffCooldown()) {
		
	}
	if(action.getActionType()==CrewActionID.Cooling && action.isOffCooldown()) {
		
	}
	action.updateCooldown();
	}

	public Generator copy() {
		return new Generator(name, efficiencyFunction, actions,generatorImg,backgroundImg);
	}



	public boolean canGenerate() {
		return canGenerate;
	}

	public void setCanGenerate(boolean bool) {
		this.canGenerate = bool;
	}

	public ImageHandler getCardBackground() {
		return backgroundImg.copy();
	}

	@Override
	public ImageHandler getCardImage() {
		return generatorImg.copy();
	}

	@Override
	public String getFlavorText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRoom(GeneratorRoom room) {
		this.room = room;
	}
}
