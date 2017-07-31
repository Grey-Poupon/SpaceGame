package com.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.project.crew_types.diseases.Disease;

public class Crew {
	private String gender;
	private Random rand;
	private ArrayList<Disease> diseases;
	private Map<String,Byte> stats;
	private Map<String,Float> statModifier;
	private RaceID race;
	
	public Crew(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			String gender, RaceID race) {
		this.gender = gender;
		this.race = race;
		stats = new HashMap<>();
		stats.put("social", (byte)social);
		stats.put("combat", (byte)combat);
		stats.put("gunner", (byte)gunner);
		stats.put("diplomacy", (byte)diplomacy);
		stats.put("stress", (byte)stress);
		stats.put("hunger", (byte)hunger);
		stats.put("teaching", (byte)teaching);
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
	
	
	
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
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
	
	
	

}
