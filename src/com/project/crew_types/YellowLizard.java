package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class YellowLizard extends Crew {
	
	private static byte statVariance = 3;

	public YellowLizard(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender, RaceID.yellowLizard);
		generateRaceTable();
	}
	public YellowLizard(boolean random) {
		super(getRandomStat(statVariance),getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomStat(statVariance), 'm', RaceID.yellowLizard);
		generateRaceTable();
	}
	public YellowLizard() {
		super(getRandomWeightedStat(statVariance,(byte)30),getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0, getRandomWeightedStat(statVariance,(byte)30),getRandomGender(), RaceID.yellowLizard);
		generateRaceTable();
	}
	

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.2f);
		this.raceRelations.put(RaceID.octoBitch, 0.5f);
		this.raceRelations.put(RaceID.ent, 1f);
		this.raceRelations.put(RaceID.blueLizard, 0.1f);
		this.raceRelations.put(RaceID.yellowLizard, 0.5f);
		this.raceRelations.put(RaceID.robot, 1.1f);
		this.raceRelations.put(RaceID.moleBitch,1.7f);
	}

}
