package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class Robot extends Crew {

	private static byte statVariance = 1;
	private byte generation;
	public static String[] names = {"Sandy","Sam","Jesse","Ste","Frank","Charlie"};
	private static float raceRelationVariance = 0.2f;
	
	public Robot(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender,  RaceID.robot);
		generateRaceTable();
		this.name=names[rand.nextInt(names.length)];
	}
	public Robot(boolean random){
		super(getRandomStat(statVariance),getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, '?', RaceID.robot);
		generateRaceTable();
		this.name=names[rand.nextInt(names.length)];
	}
	public Robot() {
		super();
		this.setGender('?');
		this.generation=getRandomGeneration();
		if(this.getGeneration()==2) {
			stats.put("social", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("combat", getRandomWeightedStat(statVariance,(byte)15));
			stats.put("pilot", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("engineering", getRandomWeightedStat(statVariance,(byte)40));
			stats.put("gunner", getRandomWeightedStat(statVariance,(byte)30));
			stats.put("science", getRandomWeightedStat(statVariance,(byte)40));			
			stats.put("stress", (byte)0);
			stats.put("hunger", (byte)0);
		}
		else {
			stats.put("social", getRandomWeightedStat(statVariance,(byte)10));
			stats.put("combat", getRandomWeightedStat(statVariance,(byte)45));
			stats.put("pilot", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("engineering", getRandomWeightedStat(statVariance,(byte)20));
			stats.put("gunner", getRandomWeightedStat(statVariance,(byte)30));
			stats.put("science", getRandomWeightedStat(statVariance,(byte)20));			
			stats.put("stress", (byte)0);
			stats.put("hunger", (byte)0);
		}
		this.setRaceID(RaceID.robot);
		generateRaceTable();
		this.name=names[rand.nextInt(names.length)];
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch,getRandomWeightedRaceRelation(raceRelationVariance, 0.3f));
		this.raceRelations.put(RaceID.octoBitch,getRandomWeightedRaceRelation(raceRelationVariance, 0.5f));
		this.raceRelations.put(RaceID.ent, getRandomWeightedRaceRelation(raceRelationVariance,1.5f));
		this.raceRelations.put(RaceID.blueLizard, getRandomWeightedRaceRelation(raceRelationVariance,0.7f));
		this.raceRelations.put(RaceID.yellowLizard,getRandomWeightedRaceRelation(raceRelationVariance, 1.1f));
		this.raceRelations.put(RaceID.robot, getRandomWeightedRaceRelation(raceRelationVariance,2f));
		this.raceRelations.put(RaceID.moleBitch,getRandomWeightedRaceRelation(raceRelationVariance,1.1f));
	}

	private byte getRandomGeneration() {
		return (byte)(rand.nextInt(1)+2);
	}
	private byte getGeneration() {
		return generation;
	}
	
}
