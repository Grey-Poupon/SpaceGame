package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class YellowLizard extends Crew {
	
	private static byte statVariance = 3;
	public static String[] names = {"Sandy","Sam","Jesse","Ste","Frank","Charlie"};
	private static float raceRelationVariance = 0.2f;
	
	public YellowLizard(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender,boolean visible) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender, RaceID.yellowLizard, visible);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
	}
	public YellowLizard(boolean random,boolean visible) {
		super(getRandomStat(statVariance),getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, 'm', RaceID.yellowLizard, visible);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
	}
	public YellowLizard(boolean visible) {
		super(getRandomWeightedStat(statVariance,(byte)20),getRandomWeightedStat(statVariance,(byte)50), getRandomWeightedStat(statVariance,(byte)25), getRandomWeightedStat(statVariance,(byte)25), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)25), 0, 0,getRandomGender(), RaceID.yellowLizard, visible);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
	}
	

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, getRandomWeightedRaceRelation(raceRelationVariance,0.2f));
		this.raceRelations.put(RaceID.octoBitch, getRandomWeightedRaceRelation(raceRelationVariance,0.5f));
		this.raceRelations.put(RaceID.ent, getRandomWeightedRaceRelation(raceRelationVariance,1f));
		this.raceRelations.put(RaceID.blueLizard,getRandomWeightedRaceRelation(raceRelationVariance, 0.1f));
		this.raceRelations.put(RaceID.yellowLizard,getRandomWeightedRaceRelation(raceRelationVariance, 0.5f));
		this.raceRelations.put(RaceID.robot,getRandomWeightedRaceRelation(raceRelationVariance, 1.1f));
		this.raceRelations.put(RaceID.moleBitch,getRandomWeightedRaceRelation(raceRelationVariance,1.7f));
	}

}
