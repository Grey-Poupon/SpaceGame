package com.project;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.project.battle.BattleUI;
import com.project.button.ButtonID;
import com.project.crew_types.BlueLizard;
import com.project.crew_types.BugBitch;
import com.project.crew_types.Ent;
import com.project.crew_types.MoleBitch;
import com.project.crew_types.OctoBitch;
import com.project.crew_types.Robot;
import com.project.crew_types.YellowLizard;
import com.project.crew_types.diseases.Disease;
import com.project.ship.Room;
import com.project.ship.Ship;

public class Crew implements Observer{
	protected char gender;
	protected static Random rand = new Random();
	private ArrayList<Disease> diseases;
	protected Map<String,Byte> stats;
	protected Map<String,Float> statModifier;
	protected Map<String, Byte> statModifierInc;
	private RaceID race;
	protected Map<RaceID,Float> raceRelations;
	protected List<String> speechOptions = new ArrayList<String>();
	private String name;
	private boolean visible;
	protected Room room;

	public static String[] statNames = {"social","combat","gunner","engineering","science","pilot","stress","hunger"};
	protected ImageHandler portrait;
	
	public Crew(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender, RaceID race,boolean visible) {
		
		this.gender = gender;
		this.race = race;
		stats = new HashMap<>();
		stats.put("social", (byte)social);
		stats.put("combat", (byte)combat);
		stats.put("gunner", (byte)gunner);
		stats.put("engineering", (byte)engineering);
		stats.put("stress", (byte)stress);
		stats.put("hunger", (byte)hunger);
		stats.put("science", (byte)science);
		stats.put("pilot", (byte)pilot);
		statModifier = new HashMap<>();
		statModifier.put("social", 1f);
		statModifier.put("combat", 1f);
		statModifier.put("gunner", 1f);
		statModifier.put("engineering", 1f);
		statModifier.put("stress", 1f);
		statModifier.put("hunger", 1f);
		statModifier.put("science", 1f);
		statModifier.put("pilot", 1f);
		statModifierInc = new HashMap<>();
		statModifierInc.put("social", (byte)0);
		statModifierInc.put("combat", (byte)0);
		statModifierInc.put("gunner", (byte)0);
		statModifierInc.put("engineering", (byte)0);
		statModifierInc.put("stress", (byte)0);
		statModifierInc.put("hunger", (byte)0);
		statModifierInc.put("science", (byte)0);
		statModifierInc.put("pilot", (byte)0);
		this.diseases = new ArrayList<Disease>();
		this.visible = visible;
		getSpeechOptions().add("Talk");
		loadPortrait();
	}
	
	public Crew(RaceID race,boolean visible) {
		this.race = race;
		stats = new HashMap<>();
		statModifier = new HashMap<>();
		statModifier.put("social", 1f);
		statModifier.put("combat", 1f);
		statModifier.put("gunner", 1f);
		statModifier.put("engineering", 1f);
		statModifier.put("stress", 1f);
		statModifier.put("hunger", 1f);
		statModifier.put("science", 1f);
		statModifier.put("pilot", 1f);
		statModifierInc = new HashMap<>();
		statModifierInc.put("social", (byte)0);
		statModifierInc.put("combat", (byte)0);
		statModifierInc.put("gunner", (byte)0);
		statModifierInc.put("engineering", (byte)0);
		statModifierInc.put("stress", (byte)0);
		statModifierInc.put("hunger", (byte)0);
		statModifierInc.put("science", (byte)0);
		statModifierInc.put("pilot", (byte)0);
		
		this.diseases = new ArrayList<Disease>();
		this.visible = visible;
		getSpeechOptions().add("Talk");
		loadPortrait();

	}
	

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	
	public void giveDisease(Disease disease){
		disease.infect(this);
	}
	
	public RaceID getRaceID() {
		return race;
	}
	
	public void cureDisease(Disease disease) {
		disease.cure(this);
	}
	
	public void setStat(String string,byte num) {
		stats.replace(string, num);
	}
	
	public void setStatModifiers(String string, float num) {
		statModifier.replace(string, num);
	}
	public void setStatModifiersInc(String string, byte num) {
		statModifierInc.replace(string, num);
	}
	public float getStatModifier(String string) {
		return statModifier.get(string);
	}
	public Map<String,Float> getStatModifiers(){
		return statModifier;
	}
	public byte getStatModifierInc(String string) {
		return statModifierInc.get(string);
	}
	public Map<String,Byte> getStatModifiersInc(){
		return statModifierInc;
	}
	
	public byte getStat(String string) {
		return (byte) (statModifierInc.get(string) +stats.get(string)*statModifier.get(string));
	}
	public HashMap<String,Byte> getStats(){
		HashMap<String,Byte> temp = new HashMap<>();
		temp.put("social", getStat("social"));
		temp.put("combat", getStat("combat"));
		temp.put("gunner", getStat("gunner"));
		temp.put("engineering", getStat("engineering"));
		temp.put("stress", getStat("stress"));
		temp.put("hunger", getStat("hunger"));
		temp.put("science", getStat("science"));
		temp.put("pilot", getStat("pilot"));
		return temp;
	}	
	public void interactSocially(Crew crew) {
		
	}
	
	protected static byte getRandomStat(float statVariance) {
		byte stat = (byte)0;
		while(((stat<0)) || ((stat>100))) {
			stat = (byte) (30+rand.nextGaussian()*statVariance);
		}
		
		return stat;
	}
	
	public void setRaceID(RaceID id) {
		this.race =id;
	}
	
	protected static byte getRandomWeightedStat(float statVariance,byte mean) {
		byte stat = (byte)0;
		while(((stat<=0)) || ((stat>100))) {
			stat = (byte) (mean+rand.nextGaussian()*statVariance);
		}
		
		return stat;
	}
	
	protected static float getRandomWeightedRaceRelation(float statVariance,float mean) {
		float raceRelation=0;
		while(((raceRelation<0)) || ((raceRelation>2))) {
			raceRelation = (float) (mean+rand.nextGaussian()*statVariance);
		}
		
		return raceRelation;
	}
	
	
	protected static char getRandomGender() {
		char gender = 't';
		if(rand.nextBoolean()) {
			gender = 'm';
		}
		else {
			gender = 'f';
		}
		return gender;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(arg == ButtonID.Crew) {
			BattleUI.generateRoomButtons(this,TooltipSelectionID.Room);
		}
		
	}



	public List<String> getSpeechOptions() {
		return speechOptions;
	}

	public void setSpeechOptions(List<String> speechOptions) {
		this.speechOptions = speechOptions;
	}
	
	public static Crew generateRandomCrew(boolean visible) {
		Crew crew;
		int t = rand.nextInt(7);
		switch (t) {
		   case 0:  crew = new BlueLizard(visible);
					break;
           case 1:  crew = new BugBitch(visible);
                    break;
           case 2:  crew = new Ent(visible);
                    break;
           case 3:  crew = new MoleBitch(visible);
                    break;
           case 4:  crew = new OctoBitch(visible);
                    break;
           case 5:  crew = new Robot(visible);
                    break;
           case 6:  crew = new YellowLizard(visible);
                    break;
           default: crew = new BlueLizard(visible);
                    break;
       }
		return crew;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	protected void loadPortrait() {
		if(this.race!=RaceID.robot){
			this.portrait = new ImageHandler(0, 60,"res/racePortraits/"+this.race.toString()+".png", true,1,1, EntityID.crew);
			portrait.setVisible(visible);
			if(visible) {Handler.addHighPriorityEntity(portrait);}
			randomisePortrait();
		}
	}
	protected void loadPortrait(byte Gen) {
		this.portrait = (new ImageHandler(0, 60,"res/racePortraits/gen"+Byte.toString(Gen)+".png", true,1,1, EntityID.crew));
		portrait.setVisible(visible);
		if(visible) {Handler.addHighPriorityEntity(portrait);}
		randomisePortrait();
	}
	public ImageHandler getPortrait() {
		return portrait;
	}
	
	private void randomisePortrait() {
		BufferedImage img = new BufferedImage(this.getPortrait().getImg().getWidth(),this.portrait.getImg().getHeight(),BufferedImage.TYPE_4BYTE_ABGR);

		float shader  = (float)(rand.nextGaussian()*0.5 +1);
		float shadeg  = (float)(rand.nextGaussian()*0.5 +1);
		float shadeb  = (float)(rand.nextGaussian()*0.5 +1);
		for(int x =0;x< this.portrait.getImg().getWidth();x++) {
			for(int y=0;y<this.portrait.getImg().getHeight();y++) {
				
				Color col = new Color(this.portrait.getImg().getRGB(x,y),true);

				int a = (int)(col.getAlpha());
				if(a==0) {continue;}
				int r = (int) (col.getRed()*(shader));
				int g = (int) (col.getGreen()*(shadeg));
				int b = (int) (col.getBlue()*(shadeb));
				
				int rgba = (a << 24) | (r << 16) | (g << 8) | b;
				
				img.setRGB(x,y,rgba);
			}
		}
		this.portrait.setImg(img);
	}
	public static Random getRand() {
		// TODO Auto-generated method stub
		return rand;
	}
	public void setRoomIn(Room room) {
		this.room = room;
	}
	public Room getRoomIn() {
		return room;
	}
	
}
