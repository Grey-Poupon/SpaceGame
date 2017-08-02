package com.project;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.project.button.Button;

public class Handler {
	public static ArrayList<Entity> entitiesLowPriority = new ArrayList<Entity>();// tick last, render on bottom
	public static ArrayList<Entity> entitiesHighPriority = new ArrayList<Entity>(); // tick first, render on top
	private static Entity mousePointer = new Entity(0,0, "res/mousepointer.png",true, EntityID.UI,"high");
	public static ArrayList<Button> buttons = new ArrayList<Button>(); 
	public static ArrayList<Animation> anims = new ArrayList<Animation>();
	public static ArrayList<Text> texts = new ArrayList<Text>();

	public void tick(UI ui){
		for(int i =0; i<anims.size();i++){
			anims.get(i).tick();
		}
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
		for(int i =0; i<anims.size();i++){
			anims.get(i).render(g);
		}
		for(int i =0; i<texts.size();i++){
			texts.get(i).render(g);
		}
		for(int i =0; i<buttons.size();i++){
			buttons.get(i).render(g);
		}
		for(int i =0; i<entitiesHighPriority.size();i++){
			entitiesHighPriority.get(i).render(g);
		}


	}
	public void checkButtons(int x,int y){
		for(int i =0; i<buttons.size();i++){
			if(buttons.get(i)!=null && buttons.get(i).isInside(x, y)){
				buttons.get(i).click();
			}
		}		
	}
	public void checkClick(int x, int y) {
		checkButtons(x, y);
	}
	public void updateMouse(int x, int y){
		mousePointer.setxCoordinate(x);
		mousePointer.setyCoordinate(y);
	}
	public static void addLowPriorityEntity(Entity entity) {
		entitiesLowPriority.add(entity);
		
	}
	public static void addHighPriorityEntity(Entity entity) {
		entitiesHighPriority.add(entity);
		
	}
	public static void addButton(Button btn) {
		buttons.add(btn);
	}
	public static void addText(Text text) {
		texts.add(text);
		
	}
	public static void addAnimation(Animation anim) {
		anims.add(anim);
		
	}

	
}