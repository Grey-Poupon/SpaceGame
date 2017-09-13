package com.project.ship;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.project.Crew;
import com.project.CrewAction;
import com.project.DamageType;
import com.project.EntityID;
import com.project.Handleable;
import com.project.ImageHandler;
import com.project.LayeredImage;
import com.project.ResourceLoader;
import com.project.battle.BattleScreen;
import com.project.battle.BattleUI;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.rooms.Cockpit;
import com.project.ship.rooms.GeneratorRoom;
import com.project.ship.rooms.WeaponsRoom;
import com.project.thrusters.Thruster;
import com.project.weapons.Weapon;

public class Ship implements Handleable{
	private LayeredImage lImage;
	private int health;
	private int maxHealth;
	private int currHealth;
	private int speed = 200;
	private int distanceToEnd = 250; // for distance system
	private int speedChange;
	private int power = 0;
	private Engine2 engine;
	private ArrayList<String> flavourTexts = new ArrayList<String>();
	private Generator generator;
	private List<Room> rooms = new ArrayList<Room>();
	private List<Slot>	   shipBackSlots       = new ArrayList<Slot>();
	private List<Slot>	   shipFrontSlots      = new ArrayList<Slot>();
	private List<Crew>     allCrew             = new ArrayList<Crew>();
	private List<Crew>     unassignedCrew      = new ArrayList<Crew>();
	private List<Handleable> sprites = new ArrayList<Handleable>();
	private Sensor sensor;
	private boolean isChased;
	private HashMap<String,Integer> resources = new HashMap<>();
	private int mass=200;
	private int endSpeed;
	private boolean visible;	
	private boolean visibleCrew;
	private EntityID entityID;
	private Crew captain;
	private boolean isPlayer=false;
	
	public boolean isChased() {
		return isChased;
	}
	
	public void generate() {
		incResource("fuel", -(int) getGenerator().getEfficiencyGraph().getxInput());
		getGenerator().generate();
	}

	
	public void setChased(boolean isChased) {
		this.isChased = isChased;
	}

	Map<DamageType,Double> damageTakenModifier = new HashMap<DamageType,Double>();
	Map<DamageType,Double> damageDealtModifier = new HashMap<DamageType,Double>();
	
	
	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale, boolean visibleCrew,boolean isChased){
		lImage = new LayeredImage(x, y, path,  z,zPerLayer,scale);
		this.currHealth = health; 
		this.maxHealth = health;
		shipBackSlots= lImage.getBackSlots();
		shipFrontSlots= lImage.getFrontSlots();
		this.isChased = isChased;
		this.visible = visible;
		this.visibleCrew = visibleCrew;
		this.entityID = id;
		setSensors();
		generateFlavourText();
		Weapon defaultWeapon = ResourceLoader.getShipWeapon("default");
		Thruster defaultEngine = ResourceLoader.getShipEngine("octoidEngine");
		for(DamageType dmg : DamageType.values()){
			damageTakenModifier.put(dmg, 1d);
			damageDealtModifier.put(dmg, 1d);
		}
		shipBackSlots.get(0).setSlotItem(defaultWeapon.copy());
		for(int i=1;i<shipBackSlots.size();i++){
			shipBackSlots.get(i).setSlotItem(defaultEngine.copy());
		}
		for(int i=0;i<shipFrontSlots.size();i++){
			shipFrontSlots.get(i).setSlotItem(defaultWeapon.copy());
		}
		generateRooms();
		generateResources();
		sortSprites();
		for(int i =0; i<10;i++) {
			Crew crewie = Crew.generateRandomCrew(visibleCrew);
			//crewie.setRoomIn(rooms.get(Crew.getRand().nextInt(rooms.size())));
			allCrew.add(crewie);
			unassignedCrew.add(crewie);
		}	
		randomlyFillRooms();

	}
	
	private void generateResources() {
		resources.put("fuel", 500);
		resources.put("missiles", 500);
		resources.put("power", 0);
	}


	private void randomlyFillRooms() {
		Random rand = new Random();
		int index;
		for(Room room : rooms) {			
			index = rand.nextInt(unassignedCrew.size());
			room.setRoomLeader(unassignedCrew.get(index));
			unassignedCrew.remove(index);
		}
		for(Crew crew:unassignedCrew) {
			index = rand.nextInt(rooms.size());
			rooms.get(index).addCrew(crew);
		}
		unassignedCrew.clear();
		
	}


	private void generateRooms() {
		rooms.add(new WeaponsRoom(getFrontWeapons(),getBackWeapons(), new Point(50,50)));
		rooms.add(new Cockpit(new Point(70,70)));
		rooms.add(new GeneratorRoom(new Point(20,20),ResourceLoader.getShipGenerator("default").copy()));
		setRoomPositions();
	}
	
	public void updatePowerConsumption(CrewAction action) {
		getGenerator().getEfficiencyGraph().setGraphPoint((int)getGenerator().getEfficiencyGraph().getyInput()+action.getPowerCost());
		incResource("fuel", -(int)getGenerator().getEfficiencyGraph().getxInput());
		setResource("power",(int)getGenerator().getEfficiencyGraph().getyInput());
		if(isPlayer) {BattleUI.updateResources(this);}
		
	}
	
	public Room getCockpit() {
		for(int i = 0; i<rooms.size();i++) {
			if(rooms.get(i) instanceof Cockpit) {
				return rooms.get(i); 
			}
		}
		return null;
	}
	
	
	public Room getGeneratorRoom() {
		for(int i = 0; i<rooms.size();i++) {
			if(rooms.get(i) instanceof GeneratorRoom) {
				return rooms.get(i); 
			}
		}
		return null;
	}
	
	
	private void sortSprites() {
		List<Handleable> temp = new ArrayList<Handleable>();
		List<Handleable> all = new ArrayList<Handleable>();
		all.addAll(shipBackSlots);
		all.addAll(lImage.getLayers());
		all.addAll(shipFrontSlots);
		int index = 0;
		float largestZ = all.get(0).getZ();
		while(all.size()>0) {
			largestZ = all.get(0).getZ();
			for(int i =0;i<all.size();i++) {
				if(all.get(i).getZ()>=largestZ) {
					index = i;
					largestZ = all.get(i).getZ();
				}
			}

			temp.add(all.get(index));
			all.remove(index);
		}
		sprites = temp;
	}




	private void generateFlavourText() {
		flavourTexts.add("THIS IS A TEST, To see whether or not text wrapping works it would sure be lovely if it did, though i wouldn't feel too bad as this is the first time ive tried it and you can't be too hard on yourself yanno, it reminds me of the time i was out fishing with my uncle and he accidentally fell into the lake and couldn't swim and i stared as he body turned from manic thrashing to stillness");
		
	}
//	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale,Weapon[] frontWeapons,Weapon[] backWeapons,Engine engine,Generator generator,List<Crew> crew){
//		lImage = new LayeredImage(x, y, path,  z,zPerLayer,scale);
//		shipBackSlots= lImage.getBackSlots();
//		shipFrontSlots= lImage.getFrontSlots();
//		setSensors();
//		generateFlavourText();
//		Weapon defaultWeapon = ResourceLoader.getShipWeapon("default");
//		for(int i=0;i<shipBackSlots.size();i++){
//			shipBackSlots.get(i).setSlotItem(defaultWeapon);
//
//		}
//		for(int i=0;i<shipFrontSlots.size();i++){
//			shipFrontSlots.get(i).setSlotItem(defaultWeapon);
//
//		}
//		generateRooms();
//		this.health = health;
//		for(DamageType dmg : DamageType.values()){
//			damageTakenModifier.put(dmg, 1d);
//			damageDealtModifier.put(dmg, 1d);
//		}
//		this.generator=generator;
//		this.engine=engine;
//		this.crew = crew;
//	}
	
	
	public void useEngine(int amountOfFuel) {
		double thrust = engine.getThrust(amountOfFuel);
		if(thrust<0) {System.out.println("your engine exploded, gg boyo");}//engine exploded
		speed = (int) thrust;
	}
	
	public void generatePower(int amountOfFuel) {
		double power = generator.getPower(amountOfFuel);
		if(power<0) {System.out.println("your generator exploded, gg boyo");}//engine exploded
		power = (int) power;
	}
	public List<Button> getCrewButtons(BattleScreen bs){
		List<Button> buttons = new ArrayList<Button>();
		for(int i = 0;i<allCrew.size();i++) {
			buttons.add(new Button(0, 0, 120, 120, ButtonID.Crew, i,true,allCrew.get(i).getName(),"sevensegies",Font.PLAIN,30,Color.WHITE,allCrew.get(i).getPortrait(), bs));
		}
		return buttons;
	}
	
	public int roomDamage(int x, int y) {
		int dmg =0;
		if(isShipClicked(x, y)) {
			Room room = getClosestRoom(x, y);
			if(room==null) {return 0;}
			int distanceToRoom = (int) (Math.pow(room.getLocation().getX(),2) + Math.pow(room.getLocation().getY(),2));
			if (distanceToRoom < room.getDamagableRadius()) {
				dmg  = (1/distanceToRoom) * room.getDamageMod();
			}
		}
		return dmg;
	}
	public Room getClosestRoom(int x, int y) {
		if(rooms.size()<1) {return null;}
		Room closestRoom = rooms.get(0);
		Point closest = closestRoom.getLocation();
		for(Room r :rooms) {
			Point n=r.getLocation();
			if(closest.x+closest.y>n.x+n.y && closest.x*closest.y > n.x*n.y) {
				closest = n;
				closestRoom=r;
			}
		}
		return closestRoom;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public List<Crew> getRoomLeaders(){
		List<Crew> leaders = new ArrayList<Crew>();
		for(Room room: rooms) {
			leaders.add(room.getRoomLeader());
		}
		return leaders;
	}
	public List<Button> getLeaderButtons(BattleScreen bs){
		List<Crew> leaders = getRoomLeaders();
		List<Button> buttons = new ArrayList<Button>();
		for(int i = 0;i<leaders.size();i++) {
			Crew crew = leaders.get(i);
			ImageHandler leaderPortrait = Crew.getLeaderPortrait(crew);
			leaderPortrait.setVisible(true);
			leaderPortrait.start();
			buttons.add(new Button(0, 0, 50, 50, ButtonID.Crew, i, true,leaderPortrait , bs));
		}
		return buttons;
	}



	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}


	public Sensor getSensor() {
		return sensor;
	}




	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}




	public boolean isShipClicked(int x, int y) {
		for(ImageHandler i : lImage.getLayers()) {
			if(i.isClicked(x, y)) {
				return true;
			}
		}
		return false;
	}
	public int getLayerClicked(int x, int y) {
		for(int i = 0; i<lImage.getLayers().size();i++) {
			if(lImage.getLayers().get(i).isClicked(x, y)) {
				return i;
			}
		}
		return -1;
	}
	
	
	public List<Crew> getAllCrew() {
		return allCrew;
	}
	public int getHealth() {
		return health;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public int getCurrHealth(){
		return currHealth;
	}
	public void takeDamage(int damage, DamageType type){
		this.currHealth-=damage*damageTakenModifier.get(type);
	}
	public Double getDamageTakenModifier(DamageType dt) {
		return damageTakenModifier.get(dt);
	}
	public void setDamageTakenModifier(DamageType dt, Double double1) {
		damageDealtModifier.put(dt, Double.valueOf(double1));
	}
	public Double getDamageDealtModifier(DamageType dt) {
		return damageDealtModifier.get(dt);
	}
	public void setDamageDealtModifier(DamageType dt, int mod) {
		damageDealtModifier.put(dt, Double.valueOf(mod));
	}
	
	public LayeredImage getLayeredImage() {
		return lImage;
	}
	public void tickLayers() {
		lImage.tick();
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speedChange = this.speed - speed;
		this.speed = speed;
	}
	public int getDistanceToEnd() {
		return distanceToEnd;
	}
	public void increaseDistanceToEnd(int distanceToEnd) {
		this.distanceToEnd += distanceToEnd;
	}
	public int getSpeedChange() {
		return speedChange;
	}
	public Slot getBackSlot(int position) {
		return lImage.getBackSlots().get(position);
	}
	public void setSensors() {
		sensor = new Sensor(0.8f);
	}
	public Sensor getSensors() {
		return sensor;
	}
	public ArrayList<String> getFlavourTexts(){
		return flavourTexts;
	}
	public void destruct() {
		lImage.destruct();
	}
	public void setX(int x) {
		lImage.setX(x);
	}
	public void setY(int y) {
		lImage.setY(y);
	}




	public void render(Graphics g) {
		
		for(int i = 0; i<sprites.size();i++) {
			if(sprites.get(i) instanceof Slot) {
				((Slot) sprites.get(i)).getSlotItem().render(g,(Slot) sprites.get(i));
			}
			if(sprites.get(i) instanceof ImageHandler) {
				sprites.get(i).render(g);
			}
		}
		
		
		
		//Render the rooms square
		Graphics2D g2d = (Graphics2D)g.create();
		for(int i = 0;i<rooms.size();i++) {
			Room r= rooms.get(i);
			g2d.setColor(Color.red);
			g2d.drawRect((int)(lImage.getLargestLayer().getxCoordinate()+lImage.getLargestLayer().getxScale()*r.getLocation().x),(int)(lImage.getLargestLayer().getyCoordinate()+ lImage.getLargestLayer().getyScale()*r.getLocation().y), (int)(lImage.getLargestLayer().getxScale()*r.getSize()), (int)(lImage.getLargestLayer().getyScale()*r.getSize()));
		}
		
		
//		for(int i =0;i<lImage.getNoLayers();i++) {
//			lImage.getLayers().get(i).render(g);
//		}
//		
//		for(int i=0;i<shipBackSlots.size();i++) {
//			shipBackSlots.get(i).getSlotItem().render(g,shipBackSlots.get(i));
//		}
//		for(int i=0;i<shipFrontSlots.size();i++) {
//			shipFrontSlots.get(i).getSlotItem().render(g,shipFrontSlots.get(i));
//		}
		
		
		
//		Graphics2D g2d = (Graphics2D)g.create();
//		for(int i=0;i<shipBackSlots.size();i++) {
//			Slot s = shipBackSlots.get(i);
////			System.out.print(s.getX());
////			System.out.print(",");
////			System.out.print(s.getY());
////			System.out.println();
//			g2d.setColor(Color.white);
//			g2d.fillRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
//		}
//		for(int i=0;i<shipFrontSlots.size();i++) {
//			Slot s = shipFrontSlots.get(i);
////			System.out.print(s.getX());
////			System.out.print(",");
////			System.out.print(s.getY());
////			System.out.println();
//			g2d.setColor(Color.white);
//			g2d.fillRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
//		}
	}




	@Override
	public void tick() {
		lImage.tick();
		for(int i = 0;i<shipBackSlots.size();i++) {
			shipBackSlots.get(i).tick();
			shipBackSlots.get(i).setX(lImage.getBackSlots().get(i).getX());
			shipBackSlots.get(i).setY(lImage.getBackSlots().get(i).getY());
			shipBackSlots.get(i).setWidth(lImage.getBackSlots().get(i).getWidth());
			shipBackSlots.get(i).setHeight(lImage.getBackSlots().get(i).getHeight());
		}
		for(int i = 0;i<shipFrontSlots.size();i++) {
			shipFrontSlots.get(i).tick();
			shipFrontSlots.get(i).setX(lImage.getFrontSlots().get(i).getX());
			shipFrontSlots.get(i).setY(lImage.getFrontSlots().get(i).getY());
			shipFrontSlots.get(i).setWidth(lImage.getFrontSlots().get(i).getWidth());
			shipFrontSlots.get(i).setHeight(lImage.getFrontSlots().get(i).getHeight());
		}
	}




	public int getPower() {
		return power;
	}




	public void setPower(int power) {
		this.power = power;
	}


	public List<Slot> getShipBackSlots() {
		return shipBackSlots;
	}


	public void setShipBackSlots(List<Slot> shipBackSlots) {
		this.shipBackSlots = shipBackSlots;
	}


	public List<Slot> getShipFrontSlots() {
		return shipFrontSlots;
	}


	public void setShipFrontSlots(List<Slot> shipFrontSlots) {
		this.shipFrontSlots = shipFrontSlots;
	}




	public List<Weapon> getFrontWeapons() {
		List<Weapon> weapons = new ArrayList<Weapon>();
		for(int i = 0; i<shipFrontSlots.size();i++) {
			if(shipFrontSlots.get(i).getSlotItem() instanceof Weapon) {
				weapons.add((Weapon)shipFrontSlots.get(i).getSlotItem());
			}
		}
		return weapons;
	}
	public List<Weapon> getBackWeapons() {
		List<Weapon> weapons = new ArrayList<Weapon>();
		for(int i = 0; i<shipBackSlots.size();i++) {
			if(shipBackSlots.get(i).getSlotItem() instanceof Weapon) {
				weapons.add((Weapon)shipBackSlots.get(i).getSlotItem());
				
			}
		}
		return weapons;
	}
	
	public List<Thruster> getThrusters(){
		List<Thruster> thrusters = new ArrayList<>();
		for(int i = 0; i<shipBackSlots.size();i++) {
			if(shipBackSlots.get(i).getSlotItem() instanceof Thruster) {
				thrusters.add((Thruster)shipBackSlots.get(i).getSlotItem());
				
			}
		}
		return thrusters;
	}
	


	public Slot getFrontSlot(int position) {
		return shipFrontSlots.get(position);
		
	}


	public List<Handleable> getSprites() {
		return sprites;
	}


	public void setSprites(List<Handleable> sprites) {
		this.sprites = sprites;
	}


	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}


	public HashMap<String,Integer> getResources() {
		return resources;
	}


	public void setResources(HashMap<String,Integer> resources) {
		this.resources = resources;
	}
	
	public int  getResource(String key) {
		return resources.get(key);
	}
	public void setResource(String key,int val) {
		resources.replace(key, val);
	}
	public void incResource(String key,int inc) {
		resources.replace(key, resources.get(key)+inc);
	}


	public Room getWeaponRoom() {
		for(Room room: rooms) {
			if(room instanceof WeaponsRoom) {
				return room;
			}
		}
		return null;
	}


	
	
	public Generator getGenerator() {
		return ((GeneratorRoom) getGeneratorRoom()).getGenerator();
	}


	public void setGenerator(Generator generator) {
		this.generator = generator;
	}


	public void accelerate() {
		incSpeed(endSpeed);
		endSpeed =0;
		//formula to decide how much power turns into how speed 
	}
	public void setEndSpeed(int speed) {
		updatePowerConsumption(new CrewAction(null, null, null, null, speed, speed, mass*speed));
		endSpeed = speed;
		//formula to decide how much power turns into how speed 
	}


	private void incSpeed(int speed) {
		setSpeed(speed+getSpeed());
		
	}


	public Ship copy() {
		return new Ship(lImage.getX(), lImage.getY(), lImage.getZ(), lImage.getzPerLayer(), lImage.getPath(), this.visible,this.entityID , maxHealth, lImage.getScale(), this.visibleCrew, isChased);
	}

	public List<Button> getPhaseLeaderButtons(BattleScreen bs) {
		List<Crew> leaders = new ArrayList<>();
		leaders.add(getGeneratorRoom().getRoomLeader());
		leaders.add(getWeaponRoom().getRoomLeader());
		leaders.add(captain);
		List<Button> buttons = new ArrayList<Button>();
		for(int i = 0;i<leaders.size();i++) {
			Crew crew = leaders.get(i);
			
			ImageHandler leaderPortrait = Crew.getLeaderPortrait(crew);
			leaderPortrait.setVisible(true);
			leaderPortrait.start();
			buttons.add(new Button(0, 0, 50, 50, ButtonID.Crew, i, true,leaderPortrait , bs));
		}
		return buttons;
	}

	
	public List<Crew> getPhaseLeaders(){
		List<Crew> leaders = new ArrayList<>();
		leaders.add(getGeneratorRoom().getRoomLeader());
		leaders.add(getWeaponRoom().getRoomLeader());
		leaders.add(captain);
		return leaders;
	}
	
	public void setCaptain(Crew captain) {
		this.captain = captain;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	
	public void setRoomPositions() {
		boolean complete = false;
		int num = 0;
		Random rand = new Random();
		ImageHandler image  = lImage.getLargestLayer();
		while(!complete) {
			num = 0;
			ArrayList<Rectangle> roomRects = new ArrayList<>();
			for(int i = 0; i<rooms.size();i++) {
				int rectX = rand.nextInt((int)image.getOnScreenWidth()-rooms.get(i).getSize());
				int rectY = rand.nextInt((int)image.getOnScreenHeight()-rooms.get(i).getSize());
				Rectangle roomRect = new Rectangle(rectX,rectY,rooms.get(i).getSize(),rooms.get(i).getSize());
				boolean rectFits = true;
				for(int k =0;k<roomRects.size();k++) {
						if(roomRects.get(k).intersects(roomRect)) {
							rectFits= false;
							break;
						}
				}
				if(!rectFits) {break;}
				int numFails = 0;
				for(int x = 0;x<roomRect.width;x++) {
					for(int y= 0;y<roomRect.height;y++) {
						if((new Color(image.getImg().getRGB(rectX+x, rectY+y),true)).getAlpha()==0) {
							numFails++;
							if(numFails>10) {
								rectFits =false;
								break;
							}
						}
					}
					if(numFails>10) {
						rectFits =false;
						break;
					}
					
				}
				if(rectFits) {
					num++;
					rooms.get(i).setLocation(new Point(rectX,rectY));
					roomRects.add(roomRect);
				}
			}
			if(num==rooms.size()) {complete = true;}
			
			
		}
		
		
		
	}
	




	
}
