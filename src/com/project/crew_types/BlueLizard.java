package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class BlueLizard extends Crew {

	public BlueLizard(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			String gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender, RaceID.blueLizard);
		generateRaceTable();
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.3f);
		this.raceRelations.put(RaceID.octoBitch, 1.8f);
		this.raceRelations.put(RaceID.ent, 1f);
		this.raceRelations.put(RaceID.blueLizard, 2f);
		this.raceRelations.put(RaceID.yellowLizard, 0.1f);
		this.raceRelations.put(RaceID.robot, 0.7f);
		this.raceRelations.put(RaceID.moleBitch,1.1f);
	}

}
