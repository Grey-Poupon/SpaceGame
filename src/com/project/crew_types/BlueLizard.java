package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class BlueLizard extends Crew {

	
	private static byte statVariance = 5;
	public static String[] names = {"Sandy","Sam","Jesse","Ste","Frank","Charlie"};
	private static float raceRelationVariance = 0.2f;
	
	public BlueLizard(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender, RaceID.blueLizard);
		generateRaceTable();
	}
	public BlueLizard(boolean random) {
		super(getRandomStat(statVariance), getRandomStat(statVariance),getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance),getRandomStat(statVariance), 0, 0,  getRandomGender(), RaceID.blueLizard);
		generateRaceTable();
		this.name=names[rand.nextInt(names.length)];
	}
	public BlueLizard() {
		super();
		this.setGender(getRandomGender());
		if(this.getGender()=='m') {
			stats.put("social", getRandomWeightedStat(statVariance,(byte)45));
			stats.put("combat", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("gunner", getRandomWeightedStat(statVariance,(byte)30));
			stats.put("engineering", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("science", getRandomWeightedStat(statVariance,(byte)30));
			stats.put("pilot", getRandomWeightedStat(statVariance,(byte)35));
			stats.put("stress", (byte)0);
			stats.put("hunger", (byte)0);
		}
		else {
			stats.put("social", getRandomWeightedStat(statVariance,(byte)30));
			stats.put("combat", getRandomWeightedStat(statVariance,(byte)25));
			stats.put("pilot", getRandomWeightedStat(statVariance,(byte)35));
			stats.put("engineering", getRandomWeightedStat(statVariance,(byte)30));
			stats.put("gunner", getRandomWeightedStat(statVariance,(byte)35));
			stats.put("science", getRandomWeightedStat(statVariance,(byte)30));			
			stats.put("stress", (byte)0);
			stats.put("hunger", (byte)0);
		}
		this.setRaceID(RaceID.blueLizard);
		generateRaceTable();
		this.name=names[rand.nextInt(names.length)];
	}
	

	

	

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		if(gender=='m'||rand.nextBoolean()) {
			
			this.raceRelations.put(RaceID.bugBitch, getRandomWeightedRaceRelation(raceRelationVariance,0.3f));
			this.raceRelations.put(RaceID.octoBitch,getRandomWeightedRaceRelation(raceRelationVariance, 1.8f));
			this.raceRelations.put(RaceID.ent,getRandomWeightedRaceRelation(raceRelationVariance, 1f));
			this.raceRelations.put(RaceID.blueLizard,getRandomWeightedRaceRelation(raceRelationVariance, 2f));
			this.raceRelations.put(RaceID.yellowLizard,getRandomWeightedRaceRelation(raceRelationVariance, 0.1f));
			this.raceRelations.put(RaceID.robot,getRandomWeightedRaceRelation(raceRelationVariance, 0.7f));
			this.raceRelations.put(RaceID.moleBitch,getRandomWeightedRaceRelation(raceRelationVariance,1.1f));
		}
		else {

			this.raceRelations.put(RaceID.bugBitch, getRandomWeightedRaceRelation(raceRelationVariance,0.2f));
			this.raceRelations.put(RaceID.octoBitch,getRandomWeightedRaceRelation(raceRelationVariance, 0.5f));
			this.raceRelations.put(RaceID.ent,getRandomWeightedRaceRelation(raceRelationVariance, 1f));
			this.raceRelations.put(RaceID.blueLizard,getRandomWeightedRaceRelation(raceRelationVariance, 0.1f));
			this.raceRelations.put(RaceID.yellowLizard, getRandomWeightedRaceRelation(raceRelationVariance,0.5f));
			this.raceRelations.put(RaceID.robot, getRandomWeightedRaceRelation(raceRelationVariance,1.1f));
			this.raceRelations.put(RaceID.moleBitch,getRandomWeightedRaceRelation(raceRelationVariance,1.7f));
		}
		
	}

}
