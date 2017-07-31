package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class Robot extends Crew {

	private static byte statVariance = 1;
	
	
	public Robot(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender,  RaceID.robot);
		generateRaceTable();
	}
	public Robot(boolean random){
		super(getRandomStat(statVariance),getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomStat(statVariance), '?', RaceID.robot);
		generateRaceTable();
	}
	public Robot() {
		super(getRandomWeightedStat(statVariance,(byte)30),getRandomWeightedStat(statVariance,(byte)30),  getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0, getRandomWeightedStat(statVariance,(byte)30),getRandomGender(), RaceID.robot);
		generateRaceTable();
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.3f);
		this.raceRelations.put(RaceID.octoBitch, 0.5f);
		this.raceRelations.put(RaceID.ent, 1.5f);
		this.raceRelations.put(RaceID.blueLizard, 0.7f);
		this.raceRelations.put(RaceID.yellowLizard, 1.1f);
		this.raceRelations.put(RaceID.robot, 2f);
		this.raceRelations.put(RaceID.moleBitch,1.1f);
	}

}
