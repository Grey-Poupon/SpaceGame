package crew_types;

import java.util.Random;

import com.project.Crew;
import com.project.RaceID;

public class OctoBitch extends Crew{

	public OctoBitch(byte social, byte combat, byte gunner, byte diplomacy, byte stress, byte hunger, byte teaching,
			String gender, Random rand) {
		super(social, combat, gunner, diplomacy, stress, hunger, teaching, gender,RaceID.octoBitch);
		
	}


}
