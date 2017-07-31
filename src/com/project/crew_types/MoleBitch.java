package com.project.crew_types;

import java.util.HashMap;
import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class MoleBitch extends Crew {
	
	private static byte statVariance = 5;

	public MoleBitch(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			char gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.moleBitch);
		generateRaceTable();
	}
	public MoleBitch(boolean random) {
		super(getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomStat(statVariance), getRandomGender(), RaceID.moleBitch);
		generateRaceTable();
		
	}
	public MoleBitch() {
		super(getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0, getRandomWeightedStat(statVariance,(byte)30),getRandomGender(), RaceID.moleBitch);
		generateRaceTable();
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.2f);
		this.raceRelations.put(RaceID.octoBitch, 0.5f);
		this.raceRelations.put(RaceID.ent, 1f);
		this.raceRelations.put(RaceID.blueLizard, 1.1f);
		this.raceRelations.put(RaceID.yellowLizard, 1.7f);
		this.raceRelations.put(RaceID.robot, 1.1f);
		this.raceRelations.put(RaceID.moleBitch,2f);		
	}
	
	
	
}

