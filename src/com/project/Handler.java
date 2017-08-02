package com.project;

import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {
	public static ArrayList<ImageHandler> entitiesLowPriority = new ArrayList<ImageHandler>();// tick last, render on bottom
	public static ArrayList<ImageHandler> entitiesHighPriority = new ArrayList<ImageHandler>(); // tick first, render on top
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
		
		for(int i =0; i<texts.size();i++){
			texts.get(i).render(g);
		}

		for(int i =0; i<entitiesLowPriority.size();i++){
			entitiesLowPriority.get(i).render(g);
		}
		for(int i =0; i<anims.size();i++){
			anims.get(i).render(g);
		}
		for(int i =0; i<entitiesHighPriority.size();i++){
			entitiesHighPriority.get(i).render(g);
		}


	}
	
	public static void addLowPriorityEntity(ImageHandler entity) {
		entitiesLowPriority.add(entity);
		
	}
	public static void addHighPriorityEntity(ImageHandler entity) {
		entitiesHighPriority.add(entity);
		
	}
	
	public static void addText(Text text) {
		texts.add(text);
		
	}
	public static void addAnimation(Animation anim) {
		anims.add(anim);
		
	}

	
}