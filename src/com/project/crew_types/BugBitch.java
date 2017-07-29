package com.project.crew_types;

import java.util.HashMap;
import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class BugBitch extends Crew {

	private Random rand;
	
	public BugBitch(byte social, byte combat, byte gunner, byte diplomacy, byte stress, byte hunger, byte teaching,
			String gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.bugBitch);
		generateRaceTable();
		
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 1f);
		this.raceRelations.put(RaceID.octoBitch, 0.1f);
		this.raceRelations.put(RaceID.moleBitch,0.2f);
		this.raceRelations.put(RaceID.blueLizard, 0.3f);
		this.raceRelations.put(RaceID.yellowLizard, 0.2f);
		this.raceRelations.put(RaceID.robot, 0.3f);
		this.raceRelations.put(RaceID.ent, 0.5f);
		
	}

}
