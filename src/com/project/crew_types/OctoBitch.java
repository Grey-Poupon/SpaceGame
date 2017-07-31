package com.project.crew_types;

import java.util.HashMap;
import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class OctoBitch extends Crew{

	private static byte statVariance = 3;
	
	public OctoBitch(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			char gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.octoBitch);
		
	}
	public OctoBitch(boolean random) {
		super(getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomStat(statVariance), getRandomGender(), RaceID.octoBitch);
		generateRaceTable();
	}
	public OctoBitch() {
		super(getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0, getRandomWeightedStat(statVariance,(byte)30), getRandomGender(), RaceID.octoBitch);
		generateRaceTable();
	}
	
	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.1f);
		this.raceRelations.put(RaceID.octoBitch, 2f);
		this.raceRelations.put(RaceID.ent, 0.9f);
		this.raceRelations.put(RaceID.blueLizard, 1.8f);
		this.raceRelations.put(RaceID.yellowLizard, 0.5f);
		this.raceRelations.put(RaceID.robot, 0.5f);
		this.raceRelations.put(RaceID.moleBitch,0.5f);
	}

}
