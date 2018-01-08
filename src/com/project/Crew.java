package com.project;

import java.awt.Color;
import java.awt.Point;
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
	private int health;
	protected char gender;
	protected static Random rand = new Random();
	private ArrayList<Disease> diseases;
	protected Map<StatID,Byte> stats;
	protected Map<StatID,Float> statModifier;
	protected Map<StatID, Byte> statModifierInc;
	private RaceID race;
	private float lvlBoost = 1;
	protected Map<RaceID,Float> raceRelations;
	protected List<String> speechOptions = new ArrayList<String>();
	private String name;
	private boolean visible;
	private boolean moving = false;
	private Room movingTo;
	protected Room roomIn;
	private Ship ship;
	private boolean roomLeader;
	private Room roomLeading;
	private Point locationInRoom = new Point(0,0);
	public static String[] statNames = {"social","combat","gunner","engineering","science","pilot","stress","hunger"};
	protected ImageHandler portrait;
	protected ImageHandler cleanPortrait;
	private boolean isCaptain=false;
	
	public Crew(int social, int combat, int pilot, int engineering,int gunner,int science, int stress, int hunger,
			char gender, RaceID race,boolean visible,int health) {
		
		this.health = health;
		this.gender = gender;
		this.race = race;
		stats = new HashMap<>();
		//starts the stats for the crew
		stats.put(StatID.social, (byte)social);
		stats.put(StatID.combat, (byte)combat);
		stats.put(StatID.gunner, (byte)gunner);
		stats.put(StatID.engineering, (byte)engineering);
		stats.put(StatID.stress, (byte)stress);
		stats.put(StatID.hunger, (byte)hunger);
		stats.put(StatID.science, (byte)science);
		stats.put(StatID.pilot, (byte)pilot);
		statModifier = new HashMap<>();
		statModifier.put(StatID.social, 1f);
		statModifier.put(StatID.combat, 1f);
		statModifier.put(StatID.gunner, 1f);
		statModifier.put(StatID.engineering, 1f);
		statModifier.put(StatID.stress, 1f);
		statModifier.put(StatID.hunger, 1f);
		statModifier.put(StatID.science, 1f);
		statModifier.put(StatID.pilot, 1f);
		statModifierInc = new HashMap<>();
		statModifierInc.put(StatID.social, (byte)0);
		statModifierInc.put(StatID.combat, (byte)0);
		statModifierInc.put(StatID.gunner, (byte)0);
		statModifierInc.put(StatID.engineering, (byte)0);
		statModifierInc.put(StatID.stress, (byte)0);
		statModifierInc.put(StatID.hunger, (byte)0);
		statModifierInc.put(StatID.science, (byte)0);
		statModifierInc.put(StatID.pilot, (byte)0);
		this.diseases = new ArrayList<Disease>();
		this.visible = visible;
		getSpeechOptions().add("Talk");
		loadPortrait();
	}	
	
	public Crew(RaceID race,boolean visible, int health) {
		this.health = health;
		this.race = race;
		stats = new HashMap<>();
		statModifier = new HashMap<>();
		statModifier.put(StatID.social, 1f);
		statModifier.put(StatID.combat, 1f);
		statModifier.put(StatID.gunner, 1f);
		statModifier.put(StatID.engineering, 1f);
		statModifier.put(StatID.stress, 1f);
		statModifier.put(StatID.hunger, 1f);
		statModifier.put(StatID.science, 1f);
		statModifier.put(StatID.pilot, 1f);
		statModifierInc = new HashMap<>();
		statModifierInc.put(StatID.social, (byte)0);
		statModifierInc.put(StatID.combat, (byte)0);
		statModifierInc.put(StatID.gunner, (byte)0);
		statModifierInc.put(StatID.engineering, (byte)0);
		statModifierInc.put(StatID.stress, (byte)0);
		statModifierInc.put(StatID.hunger, (byte)0);
		statModifierInc.put(StatID.science, (byte)0);
		statModifierInc.put(StatID.pilot, (byte)0);
		
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
	
	public void setStat(StatID stat,byte num) {
		stats.replace(stat, num);
	}
	
	public void setStatModifiers(StatID stat, float num) {
		statModifier.replace(stat, num);
	}
	public void setStatModifiersInc(StatID stat, byte num) {
		statModifierInc.replace(stat, num);
	}
	public float getStatModifier(StatID stat) {
		return statModifier.get(stat);
	}
	public Map<StatID,Float> getStatModifiers(){
		return statModifier;
	}
	public byte getStatModifierInc(StatID stat) {
		return statModifierInc.get(stat);
	}
	public Map<StatID,Byte> getStatModifiersInc(){
		return statModifierInc;
	}
	
	public byte getStat(StatID stat) {
		return (byte) (statModifierInc.get(stat) +stats.get(stat)*statModifier.get(stat));
	}
	public HashMap<StatID,Byte> getStats(){
		HashMap<StatID,Byte> temp = new HashMap<>();
		temp.put(StatID.social, getStat(StatID.social));
		temp.put(StatID.combat, getStat(StatID.combat));
		temp.put(StatID.gunner, getStat(StatID.gunner));
		temp.put(StatID.engineering, getStat(StatID.engineering));
		temp.put(StatID.stress, getStat(StatID.stress));
		temp.put(StatID.hunger, getStat(StatID.hunger));
		temp.put(StatID.science, getStat(StatID.science));
		temp.put(StatID.pilot, getStat(StatID.pilot));
		return temp;
	}	
	public void interactSocially(Crew crew) {
		
	}
	
	public void attemptLevelUp(StatID key) {
		if(rand.nextInt(stats.get(key))/lvlBoost==1 &&stats.get(key)<255) {
			stats.replace(key, (byte) (stats.get(key)+1));
		}
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
	/**Magic number here for health**/
	public static Crew generateRandomCrew(boolean visible) {
		Crew crew;
		int t = rand.nextInt(7);
		switch (t) {
		   case 0:  crew = new BlueLizard(visible,100);
					break;
           case 1:  crew = new BugBitch(visible,100);
                    break;
           case 2:  crew = new Ent(visible,100);
                    break;
           case 3:  crew = new MoleBitch(visible,100);
                    break;
           case 4:  crew = new OctoBitch(visible,100);
                    break;
           case 5:  crew = new Robot(visible,100);
                    break;
           case 6:  crew = new YellowLizard(visible,100);
                    break;
           default: crew = new BlueLizard(visible,100);
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
			this.portrait      = new ImageHandler(0, 60,"res/racePortraits/"+this.race.toString()+".png", true,1,1, EntityID.crew);
			this.cleanPortrait = new ImageHandler(0, 60,"res/racePortraits/"+this.race.toString()+".png", true,1,1, EntityID.crew);

			portrait.setVisible(visible);
			randomisePortrait();
		}
	}
	protected void loadPortrait(byte Gen) {
		this.portrait      = (new ImageHandler(0, 60,"res/racePortraits/gen"+Byte.toString(Gen)+".png", true,1,1, EntityID.crew));
		this.cleanPortrait = (new ImageHandler(0, 60,"res/racePortraits/gen"+Byte.toString(Gen)+".png", true,1,1, EntityID.crew));

		portrait.setVisible(visible);
		randomisePortrait();
	}
	public ImageHandler getPortrait() {
		return portrait.copy();
	}
	
	private void randomisePortrait() {
		//changes the colour of the portraits
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
		this.cleanPortrait.setImg(ImageHandler.copyBufferedImage(img));
		this.portrait.setImg(img);
	}
	public static Random getRand() {
		// TODO Auto-generated method stub
		return rand;
	}
	public void setRoomIn(Room room) {
		this.roomIn = room;
	}
	public Room getRoomIn() {
		return roomIn;
	}


	public static ImageHandler getLeaderPortrait(Crew crew) {
		if(crew.isCaptain) {return crew.getPortrait();}
		BufferedImage img = crew.getPortrait().getImg();
		BufferedImage roomIcon = crew.getRoomIn().getIcon();
		attachIcon(img, roomIcon,1);
		
		return new ImageHandler(0,0,img,true,EntityID.crew);
	}
	
	
	public static void attachIcon(BufferedImage img,BufferedImage roomIcon, int corner) {
		// corner
		//  0  1
		//   XX
		//   XX
		//  2  3

		int xOffset =0;
		int yOffset=0;
		// apply bitwise mask
		int top   = corner & 1;
		int right = corner & 2;
		// set offset
		if(top  ==1) {xOffset = img.getWidth() -roomIcon.getWidth();}
		if(right==2) {yOffset = img.getHeight()-roomIcon.getHeight();}
		
		for(int x =0;x<roomIcon.getWidth();x++) {
			for(int y =0;y<roomIcon.getHeight();y++) {
				Color col = new Color(roomIcon.getRGB(x, y),true);
				img.setRGB(xOffset+x, y+yOffset, col.getRGB());
			}
		}
	}
	public void setCaptain() {
		isCaptain = true;
		BufferedImage img = getPortrait().getImg();
		BufferedImage roomIcon = ResourceLoader.getImage("res/roomIcons/captain.png");
		attachIcon(img, roomIcon,1);	
		getPortrait().setImg(img);
	}
	

	public Crew copy() {
		return new Crew(stats.get(StatID.social), stats.get(StatID.combat), stats.get(StatID.pilot), stats.get(StatID.engineering), stats.get(StatID.gunner), stats.get(StatID.science), stats.get(StatID.stress), stats.get(StatID.hunger), gender, race, visible,health);
	}

	public boolean isCaptain() {
		return isCaptain;
	}

	public void setCaptain(boolean isCaptain) {
		this.isCaptain = isCaptain;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
		if(moving) {
			attachIcon(getPortrait().getImg(),ResourceLoader.getImage("res/walkingIcon.png") , 3);
			}
		else {
			removeExtraIcons();	
		}
	}

	public boolean isRoomLeader() {
		return roomLeader;
	}

	public void setRoomLeader(boolean roomLeader, Room room) {
		this.roomLeader = roomLeader;
		if(roomLeader) {
			roomLeading = room;
		}
		else {
			roomLeading = null;
		}
	}

	public Room getRoomLeading() {
		return roomLeading;
	}

	public Room getRoomMovingFrom() {
		return roomIn;
	}
	
	public Room getRoomMovingTo() {
		return movingTo;
	}
	public void removeExtraIcons() {
		portrait.setImg(getCleanPortrait().getImgCopy());
		if(isRoomLeader()) {
			attachIcon(getPortrait().getImg(),roomLeading.getIcon() , 1);
		}
	}

	private ImageHandler getCleanPortrait() {
		return cleanPortrait.copy();
	}

	public void setRoomMovingTo(Room room) {
		movingTo = room;
		
	}

	public int takeDamage(int damage) {
		this.health-=damage;
		//
		// Do Roll Table stuff
		//
		return 1;
	}

	public Point getLocationInRoom() {
		return locationInRoom;
	}

	public void setLocationInRoom(Point locationInRoom) {
		this.locationInRoom = locationInRoom;
	}
	public void incLocationInRoom(Point inc) {
		this.locationInRoom.x+=inc.x;
		this.locationInRoom.y+=inc.y;
	}


}
