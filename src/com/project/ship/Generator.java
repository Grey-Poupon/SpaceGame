package com.project.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
		double effiency = overclock.getEffiency(amountOfFuel);
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
		if(efficiencyGraph.inDangerZone()) {
			dangerRollTable();
		}
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
	public void doAction(Crew crew,CrewAction action, BattleScreen bs) {
		Ship ship = bs.playerIsChaser() ? bs.chaserShip:bs.chasedShip;
		//System.out.println(action.getActionType().toString());
		if(action.getActionType() == CrewActionID.Generate) {
			setCanGenerate(true);
		}
		if(action.getActionType()==CrewActionID.Fix) {
			
		}
		if(action.getActionType()==CrewActionID.Overclock) {
			
		}
		if(action.getActionType()==CrewActionID.Cooling) {
			
		}
		
		
	}

	public Generator copy() {
		return new Generator(name, efficiencyFunction, actions,generatorImg,backgroundImg);
	}

	@Override
	public List<Button> getInfoButtons(int width, int height, BattleScreen bs) {
		List<Button> buttons = new ArrayList<>();
		buttons.add(new Button(0,0,width, height, ButtonID.Info, 0,false,"Name: "+this.name,bs,true));
		return buttons;
	}

	public boolean canGenerate() {
		return canGenerate;
	}

	public void setCanGenerate(boolean canGenerate) {
		this.canGenerate = canGenerate;
	}

	@Override
	public ImageHandler getCardBackground() {
		return backgroundImg.copy();
	}

	@Override
	public ImageHandler getCardImage() {
		return generatorImg.copy();
	}
}
