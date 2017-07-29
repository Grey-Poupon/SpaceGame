package com.project.crew_types;

import java.util.HashMap;
import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class OctoBitch extends Crew{

	private boolean proBot;
	
	public OctoBitch(byte social, byte combat, byte gunner, byte diplomacy, byte stress, byte hunger, byte teaching,
			String gender, Random rand) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.octoBitch);
		generateRaceTable();
	}
	
	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.1f);
		this.raceRelations.put(RaceID.octoBitch, 2f);
		this.raceRelations.put(RaceID.moleBitch,0.5f);
		this.raceRelations.put(RaceID.blueLizard, 1.8f);
		this.raceRelations.put(RaceID.yellowLizard, 0.5f);
		this.raceRelations.put(RaceID.robot, 0.5f);
		this.raceRelations.put(RaceID.ent, 0.9f);
	}


}
