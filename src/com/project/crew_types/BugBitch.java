package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class BugBitch extends Crew {

	private static byte statVariance = 10;
	public static String[] names = {"Sandy","Sam","Jesse","Ste","Frank","Charlie"};
	private static float raceRelationVariance = 0.2f;
	public BugBitch(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender,boolean visible) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender,RaceID.bugBitch,visible);
	}
	public BugBitch(boolean random,boolean visible) {
		super(getRandomStat(statVariance),getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, getRandomStat(statVariance), getRandomGender(), RaceID.bugBitch, visible);
		generateRaceTable();
	}
	public BugBitch(boolean visible) {
		super(RaceID.bugBitch,visible);
		this.setGender(getRandomGender());
		if(this.getGender()=='m') {
			stats.put("social", getRandomWeightedStat(statVariance,(byte)15));
			stats.put("combat", getRandomWeightedStat(statVariance,(byte)35));
			stats.put("gunner", getRandomWeightedStat(statVariance,(byte)30));
			stats.put("engineering", getRandomWeightedStat(statVariance,(byte)20));
			stats.put("science", getRandomWeightedStat(statVariance,(byte)15));
			stats.put("pilot", getRandomWeightedStat(statVariance,(byte)20));
			stats.put("stress", (byte)0);
			stats.put("hunger", (byte)0);
		}
		else {
			stats.put("social", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("combat", getRandomWeightedStat(statVariance,(byte)40));
			stats.put("pilot", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("engineering", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("gunner", getRandomWeightedStat(statVariance,(byte)35));
			stats.put("science", getRandomWeightedStat(statVariance,(byte)20));			
			stats.put("stress", (byte)0);
			stats.put("hunger", (byte)0);
		}
		this.setName(names[rand.nextInt(names.length)]);
		generateRaceTable();
	}
	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch,getRandomWeightedRaceRelation(raceRelationVariance, 1f));
		this.raceRelations.put(RaceID.octoBitch,getRandomWeightedRaceRelation(raceRelationVariance, 0.1f));
		this.raceRelations.put(RaceID.ent,getRandomWeightedRaceRelation(raceRelationVariance, 0.5f));
		this.raceRelations.put(RaceID.blueLizard, getRandomWeightedRaceRelation(raceRelationVariance,0.3f));
		this.raceRelations.put(RaceID.yellowLizard,getRandomWeightedRaceRelation(raceRelationVariance, 0.2f));
		this.raceRelations.put(RaceID.robot,getRandomWeightedRaceRelation(raceRelationVariance, 0.3f));
		this.raceRelations.put(RaceID.moleBitch,getRandomWeightedRaceRelation(raceRelationVariance,0.2f));
		
		
	}

}
