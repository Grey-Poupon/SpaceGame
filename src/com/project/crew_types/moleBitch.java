package com.project.crew_types;

import java.util.HashMap;
import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class MoleBitch extends Crew {

	public MoleBitch(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			String gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.moleBitch);
		generateRaceTable();
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.2f);
		this.raceRelations.put(RaceID.octoBitch, 0.5f);
		this.raceRelations.put(RaceID.ent, 1f);
		this.raceRelations.put(RaceID.blueLizard, 1.1f);
		this.raceRelations.put(RaceID.yellowLizard, 1.7f);
		this.raceRelations.put(RaceID.robot, 1.1f);
		this.raceRelations.put(RaceID.moleBitch,2f);		
	}
	
	
	
}

