package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class Ent extends Crew {
	
	private static byte statVariance =0;
	public static String[] names = {"Sandy","Sam","Jesse","Ste","Frank","Charlie"};
	private static float raceRelationVariance = 0.2f;
	public Ent(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender, RaceID.ent);
		generateRaceTable();
	}
	public Ent(boolean random){
		super(getRandomStat(statVariance),getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance),getRandomStat(statVariance) ,0, 0 , '?', RaceID.ent);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
	}
	public Ent() {
		super(getRandomWeightedStat(statVariance,(byte)30),getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0, getRandomGender(), RaceID.ent);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch,getRandomWeightedRaceRelation(raceRelationVariance, 0.5f));
		this.raceRelations.put(RaceID.octoBitch,getRandomWeightedRaceRelation(raceRelationVariance, 0.9f));
		this.raceRelations.put(RaceID.ent, getRandomWeightedRaceRelation(raceRelationVariance,1f));
		this.raceRelations.put(RaceID.blueLizard, getRandomWeightedRaceRelation(raceRelationVariance,1f));
		this.raceRelations.put(RaceID.yellowLizard,getRandomWeightedRaceRelation(raceRelationVariance, 1f));
		this.raceRelations.put(RaceID.robot, getRandomWeightedRaceRelation(raceRelationVariance,1.5f));
		this.raceRelations.put(RaceID.moleBitch,getRandomWeightedRaceRelation(raceRelationVariance,1f));
	}

}
