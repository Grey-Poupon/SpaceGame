package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class Robot extends Crew {

	public Robot(byte social, byte combat, byte gunner, byte diplomacy, byte stress, byte hunger, byte teaching,
			String gender, RaceID race) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender, race);
		generateRaceTable();
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.3f);
		this.raceRelations.put(RaceID.octoBitch, 0.5f);
		this.raceRelations.put(RaceID.ent, 1.5f);
		this.raceRelations.put(RaceID.blueLizard, 0.7f);
		this.raceRelations.put(RaceID.yellowLizard, 1.1f);
		this.raceRelations.put(RaceID.robot, 2f);
		this.raceRelations.put(RaceID.moleBitch,1.1f);
	}

}
