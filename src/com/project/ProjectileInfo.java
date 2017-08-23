package com.project;

public class ProjectileInfo {
	private int waitCounter;
	private int turn;
	private int damage;
	private DamageType damageType;
	private Boolean isChaser;
	public ProjectileInfo(int waitCounter, int turn, int damage, DamageType damageType, Boolean isChaser) {
		this.waitCounter = waitCounter;
		this.turn = turn;
		this.damage = damage;
		this.damageType = damageType;
		this.isChaser = isChaser;
	}
	public int getWaitCounter() {
		return waitCounter;
	}
	public void setWaitCounter(int waitCounter) {
		this.waitCounter = waitCounter;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public DamageType getDamageType() {
		return damageType;
	}
	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}
	public Boolean getIsChaser() {
		return isChaser;
	}
	public void setIsChaser(Boolean isChaser) {
		this.isChaser = isChaser;
	}
}
