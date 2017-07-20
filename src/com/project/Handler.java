package com.project;

import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static ArrayList<Text> texts = new ArrayList<Text>();

	public void tick(UI ui){

		for(int i =0; i<entities.size();i++){
			entities.get(i).tick();
		}

	}
	
	public void render(Graphics g){
		for(int i =0; i<texts.size();i++){
			texts.get(i).render(g);
		}
		for(int i =0; i<entities.size();i++){
			entities.get(i).render(g);
		}

	}
	
	public static void addEntity(Entity entity) {
		entities.add(entity);
		
	}
	public static void addText(Text text) {
		texts.add(text);
		
	}

	
}