package com.project.crew_types;

import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class BugBitch extends Crew {

	private Random rand;
	
	public BugBitch(int social, int combat, int gunner, int diplomacy, int stress, int hunger, int teaching,
			String gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.bugBitch);
		
	}

}
