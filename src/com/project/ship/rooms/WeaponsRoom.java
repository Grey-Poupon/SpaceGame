package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.CrewAction;
import com.project.ResourceLoader;
import com.project.RoomSize;
import com.project.ship.Room;
import com.project.weapons.Weapon;

public class WeaponsRoom extends Room {

	/*back and front in one list*/
	private List<Weapon> weapons = new ArrayList<>();
	
	private List<Weapon> frontWeapons = new ArrayList<>();
	private List<Weapon> backWeapons = new ArrayList<>();

	public WeaponsRoom(Weapon[] we,String name,int actionHealth, int damageableRadius, RoomSize size) {
		super(name,actionHealth,getNoOfActions(we),size);
		this.setDamageableRadius(damageableRadius);
		weapons = (ArrayList<Weapon>) Arrays.asList(we);
		
	}
	public WeaponsRoom(List<Weapon> frontWeapons,List<Weapon> backWeapons,String name,int actionHealth, int noOfActions, int damageableRadius, RoomSize size) {
		super(name,actionHealth,getNoOfActions(frontWeapons)+getNoOfActions(backWeapons),size);
		this.frontWeapons=frontWeapons;
		this.backWeapons =backWeapons ;
		this.setDamageableRadius(damageableRadius);
		weapons.addAll(backWeapons);
		weapons.addAll(frontWeapons);

	}
	public static int getNoOfActions(Weapon[]  weapons){
		int noOfActions = 0;
		for(int i = 0;i<weapons.length;i++){
			noOfActions+=weapons[i].getActions().size();
		}
		return noOfActions;
	}
	
	public static int getNoOfActions(List<Weapon> weapons){
		int noOfActions = 0;
		for(int i = 0;i<weapons.size();i++){
			noOfActions+=weapons.get(i).getActions().size();
		}
		return noOfActions;
	}
	
	@Override
	protected CrewAction getLeastDependantAction() {
		
		/*consolidate all actions*/
		List<CrewAction> allActions = new ArrayList<CrewAction>();
		
		for(int i = 0;i<weapons.size();i++){
			for(CrewAction action : weapons.get(i).getActions()){
				if(!action.isBroken()){
					allActions.add(action);
				}
			}
		}
		
		/*Set smallest as null*/
		int smallest = Integer.MAX_VALUE; 
		CrewAction leastDependant = null;
		
		/*Loop through and search for the smallest*/
		for(CrewAction action : allActions){
			
			/*Update smallest value*/
			if(action.getActionsNeededAfterUse().size() < smallest){
				smallest = action.getActionsNeededAfterUse().size();
				leastDependant = action;
				
				/*Break on 0 as you can't get smaller than it*/
				if(smallest == 0){
					break;
				}
			}
			
		}
		
		return leastDependant;
	}
	
	@Override
	protected boolean ActionsLeft() {
		for(Weapon weapon : weapons){
			for(CrewAction action : weapon.getActions()){
				
				if(!action.isBroken()){
					return true;
				}
			}
			
		}
		return false;
	}
	
	public void UseRoom() {
		
	}
	
	
	
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/weaponsRoomIcon.png");
	}
	
	public ArrayList<String> getOptions() {
		for(int i = 0;i<weapons.size();i++) {
			
		}
		return null;
		
		
	}
	
	public List<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public List<Weapon> getFrontWeapons() {
		return frontWeapons;
	}

	public void setFrontWeapons(List<Weapon> frontWeapons) {
		this.frontWeapons = frontWeapons;
	}

	public List<Weapon> getBackWeapons() {
		return backWeapons;
	}

	public void setBackWeapons(List<Weapon> backWeapons) {
		this.backWeapons = backWeapons;
	}


	
	
	

}
