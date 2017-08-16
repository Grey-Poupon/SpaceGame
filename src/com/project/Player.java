package com.project;

public class Player {
	private int money;
	private RaceID raceID;
	
	
	public Player(int money, RaceID raceID){
		this.money = money;
		this.raceID = raceID;
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
	
}
