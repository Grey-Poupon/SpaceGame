package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;
import com.project.StatID;

public class Robot extends Crew {

	private static byte statVariance = 1;
	private byte generation;
	public static String[] names = {"Sandy","Sam","Jesse","Ste","Frank","Charlie"};
	private static float raceRelationVariance = 0.2f;
	
	public Robot(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender,byte gen,boolean visible,int health) {
		super(social, combat, pilot, engineering, gunner, science, stress,hunger, gender,  RaceID.robot, visible,health);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
		this.generation =gen;
		this.loadPortrait(gen);
	}
	public Robot(boolean random,boolean visible,int health){
		super(getRandomStat(statVariance),getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), getRandomStat(statVariance), 0, 0, '?', RaceID.robot, visible,health);
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
		this.generation = getRandomGeneration();
		this.loadPortrait(this.generation);
	}
	public Robot(boolean visible,int health) {
		super(RaceID.robot, visible,health);
		this.setGender('?');
		this.generation=getRandomGeneration();
		if(this.getGeneration()==2) {//25,15,25,40,30,40
			stats.put(StatID.social, getRandomWeightedStat(statVariance,(byte)25));
			stats.put(StatID.combat, getRandomWeightedStat(statVariance,(byte)15));
			stats.put(StatID.gunner, getRandomWeightedStat(statVariance,(byte)25));
			stats.put(StatID.engineering, getRandomWeightedStat(statVariance,(byte)40));
			stats.put(StatID.science, getRandomWeightedStat(statVariance,(byte)30));
			stats.put(StatID.pilot, getRandomWeightedStat(statVariance,(byte)40));
			stats.put(StatID.stress, (byte)0);
			stats.put(StatID.hunger, (byte)0);
		}
		else {//10,45,25,20,30,20
			stats.put(StatID.social, getRandomWeightedStat(statVariance,(byte)10));
			stats.put(StatID.combat, getRandomWeightedStat(statVariance,(byte)45));
			stats.put(StatID.pilot, getRandomWeightedStat(statVariance,(byte)25));
			stats.put(StatID.engineering, getRandomWeightedStat(statVariance,(byte)20));
			stats.put(StatID.gunner, getRandomWeightedStat(statVariance,(byte)30));
			stats.put(StatID.science, getRandomWeightedStat(statVariance,(byte)20));			
			stats.put(StatID.stress, (byte)0);
			stats.put(StatID.hunger, (byte)0);
		}
		generateRaceTable();
		this.setName(names[rand.nextInt(names.length)]);
		this.loadPortrait(this.generation);
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
