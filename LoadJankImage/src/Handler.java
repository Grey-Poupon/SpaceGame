

import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {
	public static ArrayList<Entity> entitiesLowPriority = new ArrayList<Entity>();// tick last, render on bottom
	public static ArrayList<Entity> entitiesHighPriority = new ArrayList<Entity>(); // tick first, render on top
	
	public void tick(){
		for(int i =0; i<entitiesHighPriority.size();i++){
			entitiesHighPriority.get(i).tick();
		}
		for(int i =0; i<entitiesLowPriority.size();i++){
			entitiesLowPriority.get(i).tick();
		}
	}
	
	public void render(Graphics g){
		for(int i =0; i<entitiesLowPriority.size();i++){
			entitiesLowPriority.get(i).render(g);
		}
		for(int i =0; i<entitiesHighPriority.size();i++){
			entitiesHighPriority.get(i).render(g);
		}
	}
	
	public static void addLowPriorityEntity(Entity entity) {
		entitiesLowPriority.add(entity);
		
	}
	public static void addHighPriorityEntity(Entity entity) {
		entitiesHighPriority.add(entity);
		
	}
	


	
}