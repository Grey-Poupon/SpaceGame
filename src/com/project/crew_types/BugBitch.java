package com.project.crew_types;

import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class BugBitch extends Crew {

	private static byte statVariance = 10;
	
	
	public BugBitch(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			char gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.bugBitch);
	}
	public BugBitch(boolean random) {
		super(getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomStat(statVariance), getRandomGender(), RaceID.bugBitch);
		generateRaceTable();
	}
	public BugBitch() {
		super(getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0, getRandomWeightedStat(statVariance,(byte)30), getRandomGender(), RaceID.bugBitch);
		generateRaceTable();
	}
	private void generateRaceTable() {
		
		
	}

}
