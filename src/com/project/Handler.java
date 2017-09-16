package com.project;

import java.awt.Graphics;
import java.util.ArrayList;
import com.project.button.Button;

public class Handler {
	public static ArrayList<Handleable> stars = new ArrayList<Handleable>();
	public static ArrayList<Handleable> entitiesLowPriority = new ArrayList<Handleable>();// tick last, render on bottom
	public static ArrayList<Handleable> entitiesHighPriority = new ArrayList<Handleable>(); // tick first, render on top
	protected static ImageHandler mousePointer = new ImageHandler(0,0, "res/mousePointer.png",true, EntityID.UI,"high");
	public static ArrayList<Button> buttons = new ArrayList<Button>(); 
	public static ArrayList<Animation> anims = new ArrayList<Animation>();
	public static ArrayList<Text> texts = new ArrayList<Text>();
	public static ArrayList<DraggableIcon> icons = new ArrayList<DraggableIcon>();
	protected Object dragging = null;

	public void tick(UI ui){
		
		for(int i = 0; i<stars.size();i++) {
			stars.get(i).tick();
		}
		for(int i =0; i<anims.size();i++){
			anims.get(i).tick();
		}
		for(int i =0; i<entitiesHighPriority.size();i++){
			entitiesHighPriority.get(i).tick();
		}
		for(int i =0; i<entitiesLowPriority.size();i++){
			entitiesLowPriority.get(i).tick();
		}
		mousePointer.tick();

	}
	
	public void render(Graphics g){
		
		for(int i = 0; i<stars.size();i++) {
			stars.get(i).render(g);
		}
		
		for(int i =0; i<entitiesLowPriority.size();i++){
			entitiesLowPriority.get(i).render(g.create());
		}
		for(int i =0; i<anims.size();i++){
			anims.get(i).render(g);
		}
		for(int i =0; i<buttons.size();i++){
			buttons.get(i).render(g);
		}
		
		for(int i =0; i<entitiesHighPriority.size();i++){
			entitiesHighPriority.get(i).render(g.create());
		}
		for(int i =0; i<texts.size();i++){
			texts.get(i).render(g.create());
		}
		mousePointer.render(g);
	}
	public boolean checkButtons(int x,int y, int button){
		for(int i =0; i<buttons.size();i++){
			if(buttons.get(i)!=null && buttons.get(i).isInside(x, y)){
				buttons.get(i).click(button);
				return true;
			}
		}	
		return false;
	}

	public boolean checkLeftClick(int x, int y, int button) {
		return checkButtons(x, y,button);
	}
	public void updateMouse(int x, int y){
		mousePointer.setxCoordinate(x);
		mousePointer.setyCoordinate(y);
	}
	public static void addLowPriorityEntity(Handleable entity) {
		entitiesLowPriority.add(entity);
		
	}
	public static void addHighPriorityEntity(Handleable entity) {
		entitiesHighPriority.add(entity);
	}
	public static void addStarEntity(Handleable entity) {
		stars.add(entity);
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