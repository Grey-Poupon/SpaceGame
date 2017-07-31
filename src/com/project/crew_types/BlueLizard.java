package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class BlueLizard extends Crew {

	
	private static byte statVariance = 5;
	
	
	public BlueLizard(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			char gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender, RaceID.blueLizard);
		generateRaceTable();
	}
	public BlueLizard(boolean random) {
		super(getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomStat(statVariance), getRandomGender(), RaceID.blueLizard);
		generateRaceTable();
	}
	public BlueLizard() {
		super(getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0, getRandomWeightedStat(statVariance,(byte)30), getRandomGender(), RaceID.blueLizard);
		generateRaceTable();
	}
	

	

	

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		if(gender=='m'||rand.nextBoolean()) {
			
			this.raceRelations.put(RaceID.bugBitch, 0.3f);
			this.raceRelations.put(RaceID.octoBitch, 1.8f);
			this.raceRelations.put(RaceID.ent, 1f);
			this.raceRelations.put(RaceID.blueLizard, 2f);
			this.raceRelations.put(RaceID.yellowLizard, 0.1f);
			this.raceRelations.put(RaceID.robot, 0.7f);
			this.raceRelations.put(RaceID.moleBitch,1.1f);
		}
		else {

			this.raceRelations.put(RaceID.bugBitch, 0.2f);
			this.raceRelations.put(RaceID.octoBitch, 0.5f);
			this.raceRelations.put(RaceID.ent, 1f);
			this.raceRelations.put(RaceID.blueLizard, 0.1f);
			this.raceRelations.put(RaceID.yellowLizard, 0.5f);
			this.raceRelations.put(RaceID.robot, 1.1f);
			this.raceRelations.put(RaceID.moleBitch,1.7f);
		}
		
	}

}
