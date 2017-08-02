package com.project.crew_types;

import java.util.HashMap;
import com.project.Crew;
import com.project.RaceID;

public class MoleBitch extends Crew {
	
	private static byte statVariance = 5;
	private static float raceRelationVariance = 0.2f;
	public static String[] names = {"Sandy","Sam","Jesse","Ste","Frank","Charlie"};
	
	public MoleBitch(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender,RaceID.moleBitch);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
	}
	public MoleBitch(boolean random) {
		super(getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomGender(), RaceID.moleBitch);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
		
	}
	public MoleBitch() {
		super(getRandomWeightedStat(statVariance,(byte)35),getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)5), getRandomWeightedStat(statVariance,(byte)40), getRandomWeightedStat(statVariance,(byte)30), getRandomWeightedStat(statVariance,(byte)30), 0, 0,getRandomGender(), RaceID.moleBitch);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, getRandomWeightedRaceRelation(raceRelationVariance,0.2f));
		this.raceRelations.put(RaceID.octoBitch, getRandomWeightedRaceRelation(raceRelationVariance,0.5f));
		this.raceRelations.put(RaceID.ent, getRandomWeightedRaceRelation(raceRelationVariance,1f));
		this.raceRelations.put(RaceID.blueLizard, getRandomWeightedRaceRelation(raceRelationVariance,1.1f));
		this.raceRelations.put(RaceID.yellowLizard,getRandomWeightedRaceRelation(raceRelationVariance, 1.7f));
		this.raceRelations.put(RaceID.robot,getRandomWeightedRaceRelation(raceRelationVariance, 1.1f));
		this.raceRelations.put(RaceID.moleBitch,getRandomWeightedRaceRelation(raceRelationVariance,2f));		
	}
	
	
	
}

