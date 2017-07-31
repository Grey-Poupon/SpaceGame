package com.project.crew_types;

import java.util.HashMap;
import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class MoleBitch extends Crew {
	
	private static byte statVariance = 5;

	public MoleBitch(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender,RaceID.moleBitch);
		generateRaceTable();
	}
	public MoleBitch(boolean random) {
		super(getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomStat(statVariance), getRandomGender(), RaceID.moleBitch);
		generateRaceTable();
		
	}
	public MoleBitch() {
		super(getRandomWeightedStat(statVariance,(byte)35),getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)5), getRandomWeightedStat(statVariance,(byte)40), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0,getRandomGender(), RaceID.moleBitch);
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

