package com.project;

import com.project.ship.Ship;

public class Player {
	private int money;
	private RaceID raceID;
	private Crew playerCrew;
	private Ship ship;
	public Player(int money){
		this.money = money;
		this.playerCrew = Crew.generateRandomCrew(false);
		this.playerCrew.setCaptain();
		ship=ResourceLoader.getShip("defaultPlayer");
	}


	public int getMoney() {
		return money;
	}
	public RaceID getRaceID(){
		return raceID;
	}
	public void setMoney(int money){
		this.money = money;
	}
	public void increaseMoney(int amount){
		this.money+=amount;
	}
	public void decreaseMoney(int amount){
		this.money-=amount;
	}


	public Crew getPlayerCrew() {
		return playerCrew;
	}
	
	public Ship getShip() {
		return ship;
	}


	public void setPlayerCrew(Crew playerCrew) {
		this.playerCrew = playerCrew;
	}
	
}
