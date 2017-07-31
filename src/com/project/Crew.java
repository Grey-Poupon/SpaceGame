package com.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.project.crew_types.diseases.Disease;

public class Crew implements Observer{
	protected char gender;
	protected static Random rand= new Random();
	private ArrayList<Disease> diseases;
	protected Map<String,Byte> stats;
	protected Map<String,Float> statModifier;
	private RaceID race;
	protected Map<RaceID,Float> raceRelations;
	
	public Crew(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender, RaceID race) {
		this.gender = gender;
		this.race = race;
		stats = new HashMap<>();
		stats.put("social", (byte)social);
		stats.put("combat", (byte)combat);
		stats.put("gunner", (byte)gunner);
		stats.put("engineering", (byte)engineering);
		stats.put("stress", (byte)stress);
		stats.put("hunger", (byte)hunger);
		stats.put("science", (byte)science);
		stats.put("pilot", (byte)pilot);
		statModifier = new HashMap<>();
		statModifier.put("social",  0f);
		statModifier.put("combat", 0f);
		statModifier.put("gunner", 0f);
		statModifier.put("diplomacy",0f);
		statModifier.put("stress", 0f);
		statModifier.put("hunger", 0f);
		statModifier.put("teaching", 0f);
		this.diseases = new ArrayList<Disease>();
	}
	
	public Crew() {
		stats = new HashMap<>();
		statModifier = new HashMap<>();
		statModifier.put("social",  0f);
		statModifier.put("combat", 0f);
		statModifier.put("gunner", 0f);
		statModifier.put("diplomacy",0f);
		statModifier.put("stress", 0f);
		statModifier.put("hunger", 0f);
		statModifier.put("teaching", 0f);
		this.diseases = new ArrayList<Disease>();
		
	}
	
	
	
	
	
	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}
	public void giveDisease(Disease disease){
		disease.infect(this);
	}
	public void cureDisease(Disease disease) {
		disease.cure(this);
	}
	public void setStat(String string,byte num) {
		stats.replace(string, num);
	}
	public void setStatModifiers(String string, float num) {
		statModifier.replace(string, num);
	}
	public byte getStat(String string) {
		return (byte) (stats.get(string)*statModifier.get(string));
	}
	public void interactSocially(Crew crew) {
		
	}
	protected static byte getRandomStat(float statVariance) {
		byte stat = (byte)0;
		while(((stat<0)) || ((stat>100))) {
			stat = (byte) (30+rand.nextGaussian()*statVariance);
		}
		
		return stat;
	}
	
	public void setRaceID(RaceID id) {
		this.race =id;
	}
	
	protected static byte getRandomWeightedStat(float statVariance,byte mean) {
		byte stat = (byte)0;
		while(((stat<0)) || ((stat>100))) {
			stat = (byte) (mean+rand.nextGaussian()*statVariance);
		}
		
		return stat;
	}
	
	protected static char getRandomGender() {
		char gender = 't';
		if(rand.nextBoolean()) {
			gender = 'm';
		}
		else {
			gender = 'f';
		}
		return gender;
		
	}




	@Override
	public void update(Observable o, Object arg) {
		if(arg == ButtonID.Crew) {
			
		}
		
	}
	
	
	

}
