package com.project.crew_types;

import java.util.HashMap;

import com.project.Crew;
import com.project.RaceID;

public class YellowLizard extends Crew {

	public YellowLizard(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			String gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender, RaceID.yellowLizard);
		generateRaceTable();
	}

	private void generateRaceTable() {
		this.raceRelations = new HashMap<>();
		this.raceRelations.put(RaceID.bugBitch, 0.2f);
		this.raceRelations.put(RaceID.octoBitch, 0.5f);
		this.raceRelations.put(RaceID.ent, 1f);
		this.raceRelations.put(RaceID.blueLizard, 0.1f);
		this.raceRelations.put(RaceID.yellowLizard, 0.5f);
		this.raceRelations.put(RaceID.robot, 1.1f);
		this.raceRelations.put(RaceID.moleBitch,1.7f);
	}

}
