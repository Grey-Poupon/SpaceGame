package com.project.ship;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.project.Crew;
import com.project.CrewAction;
import com.project.EntityID;
import com.project.Handleable;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.LayeredImage;
import com.project.ResourceLoader;
import com.project.RoomSize;
import com.project.Recreation.RecreationalItem;
import com.project.battle.BattleScreen;
import com.project.battle.BattleUI;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.rooms.Cockpit;
import com.project.ship.rooms.GeneratorRoom;
import com.project.ship.rooms.SensorRoom;
import com.project.ship.rooms.StaffRoom;
import com.project.ship.rooms.WeaponsRoom;
import com.project.thrusters.Thruster;
import com.project.weapons.Buffer;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;

public class Ship implements Handleable{
	private BattleScreen bs;
	private LayeredImage lImage;
	private int health;
	private int maxHealth;
	private int currHealth;
	private Rectangle clip;
	private int speed         = 200;
	private int tempSpeed     = 0;
	private int distanceToEnd = 250;//for distance system
	private int power         = 0;
	private int speedChange;
	private Engine2 engine;
	private ArrayList<String> flavourTexts = new ArrayList<String>();
	private List<Room> shipRooms 		   = new ArrayList<Room>();
	private List<Slot> shipBackSlots       = new ArrayList<Slot>();
	private List<Slot> shipFrontSlots      = new ArrayList<Slot>();
	private List<Crew> allCrew             = new ArrayList<Crew>();
	private List<Crew> unassignedCrew      = new ArrayList<Crew>();
	private List<Handleable> sprites 	   = new ArrayList<Handleable>();
	private boolean isChased;
	private HashMap<ResourcesID,Integer> resources = new HashMap<>();
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
		if(getGenerator().canGenerate()) {
			updatePowerConsumption();
			getGenerator().generate();
		}
	}

	public void setChased(boolean isChased) {
		this.isChased = isChased;
	}

	Map<Boolean,Double> damageTakenModifier = new HashMap<Boolean,Double>();
	Map<Boolean,Double> damageDealtModifier = new HashMap<Boolean,Double>();
	private boolean beingSensed=false;

	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale, boolean visibleCrew,boolean isChased){
		lImage = new LayeredImage(x, y, path,  z,zPerLayer,scale);
		this.currHealth  = health; 
		shipBackSlots    = lImage.getBackSlots();
		shipFrontSlots   = lImage.getFrontSlots();
		this.isChased    = isChased;
		this.visible     = visible;
		this.visibleCrew = visibleCrew;
		this.entityID    = id;
		
		generateFlavourText();
		Weapon   defaultWeapon = ResourceLoader.getShipWeapon("default");
		Thruster defaultEngine = ResourceLoader.getShipEngine("octoidEngine");
		damageTakenModifier.put(true,  1d);
		damageTakenModifier.put(false, 1d);
		damageDealtModifier.put(true,  1d);
		damageDealtModifier.put(false, 1d);
		//locks the ships in their respective screen.
		if(isChased) {
			this.clip = new Rectangle(660,15,590,435);
		}
		else {
			this.clip = new Rectangle(29,8,603,449);
		}
		
		for(int i=0;i<shipBackSlots.size();i++){
			if(i==0){shipBackSlots.get(i).setSlotItem(defaultEngine.copy());}
			else{
				shipBackSlots.get(i).setSlotItem(defaultWeapon.copy());
			}
		}
		for(int i=0;i<shipFrontSlots.size();i++){
			shipFrontSlots.get(i).setSlotItem(defaultWeapon.copy());
		}
		generateRooms();
		setMaxHealth();
		generateResources();
		sortSprites();
		
		for(int i =0; i<15;i++) {
			Crew crewie = Crew.generateRandomCrew(visibleCrew);
			crewie.setShip(this);
			//crewie.setRoomIn(rooms.get(Crew.getRand().nextInt(rooms.size())));
			allCrew.add(crewie);
			unassignedCrew.add(crewie);
		}	
		randomlyFillRooms();
	}
	public void doDamage(Object[] effects, Object[][] damageInfo,boolean targetSelf,Point click){
		/**get targeted ship**/
		Ship ship;
		if(targetSelf){
			ship = this;
		}
		else{
			ship = isPlayer? bs.getEnemyShip():bs.getPlayerShip();	
		}
		
		int rateOfFire         = (int)     damageInfo[effects.length-1][0];
		float[] accuracy       = (float[]) damageInfo[effects.length-1][1];
		int damagePerShot      = (int)     damageInfo[effects.length-1][2];
		boolean isPhysical     = (boolean) damageInfo[effects.length-1][3];
		int areaOfEffectRadius = (int)     damageInfo[effects.length-1][4];
		
					
		/**loop through the different effects**/
		for(int i = 0;i<effects.length;i++){
			
			/**Do destructive effects**/
			if(effects[i] instanceof Destructive){
					List<Room> rooms = ship.getRoomsHit(click,areaOfEffectRadius);
					System.out.println("Number of hit rooms: "+rooms.size());
					
					/**loop through rooms that get hit**/
					for(int k = 0;k < rooms.size();k++){
						
						/**Room takes damage**/
						int roomTableRoll = rooms.get(k).takeDamage(damagePerShot);
						
						/**Rooms Roll Table**/
						doRollTableEffect(roomTableRoll,rooms.get(k));//Unimplemented

						/**If its physical, damage crew in room**/
						if(isPhysical){
							for(Crew crew: rooms.get(k).crewInRoom){
								
								/**Crew takes damage**/
								int crewTableRoll = crew.takeDamage(damagePerShot);
								
								/**Crews Roll Table**/
								doRollTableEffect(crewTableRoll, crew);//Unimplemented
							}
						}
					}
				}
			/**Do buffer effects**/
			if(effects[i] instanceof Buffer){
				// do buffer effect
			}
		}
	}
	
	private void doRollTableEffect(int rollTableRoll, Crew crew) {
	
	}

	private void doRollTableEffect(int rollTableroll, Room room) {
	
	}

	private List<Room> getRoomsHit(Point click, int areaOfEffectRadius) {
		List<Room> returnableRooms = new ArrayList<Room>();
		/**Make coordinates relative to ship**/
		float xRelToShip = (float) (click.getX() - lImage.getLargestLayer().getxCoordinate());
		float yRelToShip = (float) (click.getY() - lImage.getLargestLayer().getyCoordinate());
		/**Make coordinates original scale**/
		float xScaled = xRelToShip/lImage.getLargestLayer().getxScale();
		float yScaled = yRelToShip/lImage.getLargestLayer().getyScale();
		
	
		/**check room intersection with damaged area**/
		for(int i = 0;i<shipRooms.size();i++){
			Room room = shipRooms.get(i);
			if(checkIfCirclesTouch(xScaled,yScaled,areaOfEffectRadius,room.getLocation().x,room.getLocation().y,room.getDamageableRadius())){
				returnableRooms.add(room);
				System.out.println(room.getRoomName());
			}
		}
		
		return returnableRooms;
	}

	private boolean checkIfCirclesTouch(float x1, float y1, float r1, float x2, float y2, float r2) {
		
		double xSquare = Math.pow(x1-x2, 2);
		double ySquare = Math.pow(y1-y2, 2);
		double gap     = Math.sqrt(xSquare+ySquare);
		
		if(gap > r1+r2){
			return false;
		}
		return true;
	}

	private void generateResources() {
		resources.put(ResourcesID.Fuel, 500);
		resources.put(ResourcesID.Missiles, 500);
		resources.put(ResourcesID.Power, 0);
	}

	public void apply(Weapon w) {	
	}
	
	private void randomlyFillRooms() {
		Random rand = new Random();
		int index;
		for(Room room : shipRooms) {			
			index = rand.nextInt(unassignedCrew.size());
			room.setRoomLeader(unassignedCrew.get(index));
			unassignedCrew.get(index).setRoomIn(room);
			unassignedCrew.remove(index);
		}
		getWeaponRoom().addCrew(unassignedCrew.get(0));
		unassignedCrew.remove(0);
		for(Crew crew:unassignedCrew) {
			index = rand.nextInt(shipRooms.size());
			if(shipRooms.get(index).getCrewInRoom().size()>=shipRooms.get(index).getSize().getMaxPopulation()) {
				if(index==shipRooms.size()-1) index--;
				else index++;
			}
			shipRooms.get(index).addCrew(crew);
		}
		unassignedCrew.clear();	
	}

	private void generateRooms() {
		/**Magic numbers here for room stats**/
		shipRooms.add(new SensorRoom(new Sensor(0.8f), "Sensor Room", 20, 3,10,RoomSize.Small,this));

		shipRooms.add(new WeaponsRoom(getFrontWeapons(),getBackWeapons(),"Weapons Room",20, 8,10,RoomSize.Large,this));

		List<CrewAction> manoeuvres = Arrays.asList(new CrewAction[] {ResourceLoader.getCrewAction("basicDodge"),ResourceLoader.getCrewAction("basicSwitch"),ResourceLoader.getCrewAction("basicDodge"),ResourceLoader.getCrewAction("basicSwitch"),ResourceLoader.getCrewAction("basicDodge"),ResourceLoader.getCrewAction("basicSwitch"),ResourceLoader.getCrewAction("basicDodge"),ResourceLoader.getCrewAction("basicSwitch"),ResourceLoader.getCrewAction("basicDodge")});
		shipRooms.add(new Cockpit(manoeuvres,"Cockpit",20, 10,RoomSize.Medium,this));
		shipRooms.add(new GeneratorRoom(ResourceLoader.getShipGenerator("default").copy(),"Generator Room",20,9, 10,RoomSize.Large,this));
		ArrayList<RecreationalItem> items = new ArrayList<>();
		items.add(new RecreationalItem("ArmChair",4));
		shipRooms.add(new StaffRoom(items,"Staff Room", 20, 9, 10,RoomSize.Medium,this));
		setRoomPositions();
	}
	
	
	
	public Room getCockpit() {
		for(int i = 0; i<shipRooms.size();i++) {
			if(shipRooms.get(i) instanceof Cockpit) {
				return shipRooms.get(i); 
			}
		}
		return null;
	}
	
	
	public GeneratorRoom getGeneratorRoom() {
		for(int i = 0; i<shipRooms.size();i++) {
			if(shipRooms.get(i) instanceof GeneratorRoom) {
				return (GeneratorRoom) shipRooms.get(i); 
			}
		}
		return null;
	}
	public Generator getGenerator() {
		return getGeneratorRoom().getGenerator();
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
	
	public void useEngine(int amountOfFuel) {
		double thrust = engine.getThrust(amountOfFuel);
		if(thrust<0) {System.out.println("your engine exploded, gg boyo");}//engine exploded
		speed = (int) thrust;
	}
	
	public void generatePower(int amountOfFuel) {
		double power = getGenerator().getPower(amountOfFuel);
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
			if (distanceToRoom < room.getDamageableRadius()) {
				dmg  = (1/distanceToRoom) * room.getDamageMod();
			}
		}
		return dmg;
	}
	public Room getClosestRoom(int x, int y) {
		if(shipRooms.size()<1) {return null;}
		Room closestRoom = shipRooms.get(0);
		Point closest = closestRoom.getLocation();
		for(Room r :shipRooms) {
			Point n=r.getLocation();
			if(closest.x+closest.y>n.x+n.y && closest.x*closest.y > n.x*n.y) {
				closest = n;
				closestRoom=r;
			}
		}
		return closestRoom;
	}
	public List<Room> getRooms() {
		return shipRooms;
	}
	public List<Crew> getRoomLeaders(){
		List<Crew> leaders = new ArrayList<Crew>();
		for(Room room: shipRooms) {
			leaders.add(room.getRoomLeader());
		}
		return leaders;
	}
	public List<Button> getLeaderButtons(Handler handler,BattleScreen bs){
		List<Crew> leaders = getRoomLeaders();
		List<Button> buttons = new ArrayList<Button>();
		for(int i = 0;i<leaders.size();i++) {
			Crew crew = leaders.get(i);
			ImageHandler leaderPortrait = Crew.getLeaderPortrait(crew);
			leaderPortrait.setVisible(true);
			leaderPortrait.start(handler,false);
			buttons.add(new Button(0, 0, 50, 50, ButtonID.Crew, i, true,leaderPortrait , bs));
		}
		return buttons;
	}

	public void setRooms(List<Room> rooms) {
		this.shipRooms = rooms;
	}


	public Sensor getSensor() {
		for(int i = 0;i<shipRooms.size();i++){
			if(shipRooms.get(i) instanceof SensorRoom) {
				return ((SensorRoom)shipRooms.get(i)).getSensor();
			}
		}
		return null;
		
	}


	public void setSensor(Sensor sensor) {
		for(int i = 0;i<shipRooms.size();i++){
			if(shipRooms.get(i) instanceof SensorRoom) {
				((SensorRoom)shipRooms.get(i)).setSensor(sensor);
				break;
			}
		}
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

	public void setMaxHealth(){
		int maxHealth = 0;
		for(Room room:shipRooms){
			maxHealth+= room.getMaxHealth();
		}
		this.maxHealth = maxHealth;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public int getCurrHealth(){
		int currHealth = 0;
		for(Room room:shipRooms){
			currHealth+= room.getHealth();
		}
		return currHealth;
	}
	public void takeDamage(int damage, boolean isPhysical){
		this.currHealth-=damage*damageTakenModifier.get(isPhysical);
	}
	public Double getDamageTakenModifier(boolean dt) {
		return damageTakenModifier.get(dt);
	}
	public void setDamageTakenModifier(boolean dt, Double double1) {
		damageDealtModifier.put(dt, Double.valueOf(double1));
	}
	public Double getDamageDealtModifier(boolean dt) {
		return damageDealtModifier.get(dt);
	}
	public void setDamageDealtModifier(boolean dt, int mod) {
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

	public void render(Graphics g1) {
		Graphics g = g1.create();
		g.setClip(clip);
		//perform graphics calculations
		lImage.preRender();
		//render the weapons/engines
		for(int i = 0; i<sprites.size();i++) {
			if(sprites.get(i) instanceof Slot) {
				((Slot) sprites.get(i)).getSlotItem().render(g,(Slot) sprites.get(i));
			}
			if(sprites.get(i) instanceof ImageHandler) {
				sprites.get(i).render(g);
			}
		}
		if(beingSensed) {
			getGeneratorRoom().renderSensorSpheres(g, this);
		}
		
		//Render the rooms square
		Graphics2D g2d = (Graphics2D)g.create();
		for(int i = 0;i<shipRooms.size();i++) {
			
			Room r = shipRooms.get(i);
			g2d.setColor(Color.red);
			int rectangleSize = r.getSize().getLength();
			int recX = (int)(lImage.getLargestLayer().getxCoordinate()+lImage.getLargestLayer().getxScale()*r.getLocation().x);
			int recY = (int)(lImage.getLargestLayer().getyCoordinate()+ lImage.getLargestLayer().getyScale()*r.getLocation().y);
			g2d.drawRect(recX,recY, rectangleSize, rectangleSize);
			g2d.drawImage(r.getIcon(), (int)(lImage.getLargestLayer().getxCoordinate()+lImage.getLargestLayer().getxScale()*r.getLocation().x), (int)(lImage.getLargestLayer().getyCoordinate()+ lImage.getLargestLayer().getyScale()*r.getLocation().y), null);
			
			r.renderHealthBars(recX,recY,rectangleSize,g2d);
			r.renderCrewOnShip(g2d, this);
		}
		
	}


	public void generateSensorSpheres(Sensor sensor) {
		for(int i = 0; i<shipRooms.size();i++) {
			shipRooms.get(i).generateSensorSpheres(sensor);
		}
	}
	
	public void tick() {
		lImage.tick();
		for(int i=0;i<shipRooms.size();i++) {
			shipRooms.get(i).tick();
		}
		//update slots
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
	
	
	
	public void updatePowerConsumption() {
		incResource(ResourcesID.Fuel, -(int)getGenerator().getEfficiencyGraph().getxInput());
		getGenerator().getEfficiencyGraph().setGraphPoint(0);
		if(isPlayer) {BattleUI.updateResources(this);}	
	}
	
	public void tempUpdatePowerConsumption(int cost) {
		float temp = (float) (getGenerator().getEfficiencyGraph().getyInput()+cost);
		getGenerator().getEfficiencyGraph().setGraphPoint((int)temp);
	}

	public int getPower() {
		return power;
	}
	
	public StaffRoom getStaffRoom() {
		for(int i=0;i<shipRooms.size();i++) {
			if(shipRooms.get(i) instanceof StaffRoom) {
				return (StaffRoom) shipRooms.get(i);
			}
		}
		return null;
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
	
	public void setTempSpeed(int speed) {
		if(tempSpeed !=speed) {
			tempUpdatePowerConsumption((speed-tempSpeed)*mass);
			tempSpeed = speed;
		}
		
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


	
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}


	public HashMap<ResourcesID,Integer> getResources() {
		return resources;
	}


	public void setResources(HashMap<ResourcesID,Integer> resources) {
		this.resources = resources;
	}
	
	public int  getResource(ResourcesID key) {
		return resources.get(key);
	}
	public void setResource(ResourcesID key,int val) {
		resources.replace(key, val);
	}
	public void incResource(ResourcesID key,int inc) {
		resources.replace(key, resources.get(key)+inc);
	}


	public Room getWeaponRoom() {
		for(Room room: shipRooms) {
			if(room instanceof WeaponsRoom) {
				return room;
			}
		}
		return null;
	}

	public void accelerate() {
		incSpeed(endSpeed);
		endSpeed =0;
		//formula to decide how much power turns into how speed 
	}
	public void setEndSpeed(int speed) {
		endSpeed = speed;
		//formula to decide how much power turns into how speed 
	}


	private void incSpeed(int speed) {
		setSpeed(speed+getSpeed());
	}

	public Ship copy() {
		return new Ship(lImage.getX(), lImage.getY(), lImage.getZ(), lImage.getzPerLayer(), lImage.getPath(), this.visible,this.entityID , maxHealth, lImage.getScale(), this.visibleCrew, isChased);
	}

	public List<Button> getPhaseLeaderButtons(Handler handler,BattleScreen bs) {
		List<Crew> leaders = new ArrayList<>();
		leaders.add(getGeneratorRoom().getRoomLeader());
		leaders.add(getWeaponRoom().getRoomLeader());
		leaders.add(captain);
		List<Button> buttons = new ArrayList<Button>();
		for(int i = 0;i<leaders.size();i++) {
			Crew crew = leaders.get(i);
			
			ImageHandler leaderPortrait = Crew.getLeaderPortrait(crew);
//			if(crew.getRoomLeading() == this.getWeaponRoom()){
//				leaderPortrait.addImageFrame(ResourceLoader.getImage("res/portraitFrameWeapons.png"),6,6);
//			}
//			else if(crew.getRoomLeading() == this.getGeneratorRoom()){
//				leaderPortrait.addImageFrame(ResourceLoader.getImage("res/portraitFrameEngines.png"),6,6);
//
//			}
//			else {
//				leaderPortrait.addImageFrame(ResourceLoader.getImage("res/portraitFrameCockpit.png"),6,6);
//			}
			leaderPortrait.setVisible(true);
			leaderPortrait.start(handler, false);
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
		int tolerance = 10;
		int rectTolerance = 2;
		Random rand = new Random();
		ImageHandler image  = lImage.getLargestLayer();
		
		while(!complete) {
			num = 0;
			ArrayList<Rectangle> roomRects = new ArrayList<>();
			for(int i = 0; i<shipRooms.size();i++) {
				// Create randomly placed Room
				int rectX  = rand.nextInt((int) (image.getOnScreenWidth()-rectTolerance*2-shipRooms.get(i).getSize().getLength()/lImage.getScale()));
				int rectY  = rand.nextInt((int) (image.getOnScreenHeight()-rectTolerance*2-shipRooms.get(i).getSize().getLength()/lImage.getScale()));
				int length = (int) (shipRooms.get(i).getSize().getLength()/lImage.getScale())+rectTolerance*2;
				
				Rectangle roomRect = new Rectangle(rectX,rectY,length,length);
				
				// If it intersects wipe all rooms and start again
				boolean rectFits = true;
				for(int k =0;k<roomRects.size();k++) {
						if(roomRects.get(k).intersects(roomRect)) {
							rectFits= false;
							break;
						}
				}
				if(!rectFits) {break;}
				
				// Check if room sits on transparent pixel
				int numFails = 0;			
				for(int x = 0;x<roomRect.width;x++) {
					for(int y= 0;y<roomRect.height;y++) {
						if((new Color(image.getImg().getRGB(rectX+x, rectY+y),true)).getAlpha()==0) {
							numFails++;
							if(numFails>tolerance) {
								rectFits =false;
								break;
							}
						}
					}
					if(!rectFits){
						break;
					}
				}
				// if rectangle fits make it else, start again
				if(rectFits) {
					num++;
					shipRooms.get(i).setLocation(new Point(rectX+rectTolerance,rectY+rectTolerance));
					roomRects.add(roomRect);
				}
				else{
					break;
				}
			}
			if(num==shipRooms.size()) {complete = true;}	
		}	
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}

	public boolean isBeingSensed() {
		return beingSensed;
	}

	public void setBeingSensed(boolean beingSensed) {
		this.beingSensed = beingSensed;
	}
	
	public Shape getClip() {
		return lImage.getClip();
	}
	public void setBs(BattleScreen bs) {
		this.bs = bs;
	}

	public void removeFuel(int i) {
		// TODO Auto-generated method stub
		
	}
}
