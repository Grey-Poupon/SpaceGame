package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;
import com.project.StatID;

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
		this.setGender(getRandomGender());//15,35,30,20,15,20,0,0
		if(this.getGender()=='m') {
			stats.put(StatID.social, getRandomWeightedStat(statVariance,(byte)15));
			stats.put(StatID.combat, getRandomWeightedStat(statVariance,(byte)35));
			stats.put(StatID.gunner, getRandomWeightedStat(statVariance,(byte)30));
			stats.put(StatID.engineering, getRandomWeightedStat(statVariance,(byte)20));
			stats.put(StatID.science, getRandomWeightedStat(statVariance,(byte)15));
			stats.put(StatID.pilot, getRandomWeightedStat(statVariance,(byte)20));
			stats.put(StatID.stress, (byte)0);
			stats.put(StatID.hunger, (byte)0);
		}
		else {//25,40,25,25,35,20,0,0
			stats.put(StatID.social, getRandomWeightedStat(statVariance,(byte)25));
			stats.put(StatID.combat, getRandomWeightedStat(statVariance,(byte)40));
			stats.put(StatID.pilot, getRandomWeightedStat(statVariance,(byte)25));
			stats.put(StatID.engineering, getRandomWeightedStat(statVariance,(byte)25));
			stats.put(StatID.gunner, getRandomWeightedStat(statVariance,(byte)35));
			stats.put(StatID.science, getRandomWeightedStat(statVariance,(byte)20));			
			stats.put(StatID.stress, (byte)0);
			stats.put(StatID.hunger, (byte)0);
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
