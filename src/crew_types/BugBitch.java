package crew_types;

import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class BugBitch extends Crew {

	private Random rand;
	
	public BugBitch(byte social, byte combat, byte gunner, byte diplomacy, byte stress, byte hunger, byte teaching,
			String gender) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.bugBitch);
		
	}

}
