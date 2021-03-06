package com.project.battle;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.project.ActionBox;
import com.project.DraggableIcon;
import com.project.Handler;
import com.project.button.Button;
import com.project.slider.VerticalSliderHandle;

public class BattleHandler extends Handler {
	
	private BattleScreen bs;

	
	public BattleHandler(BattleScreen bs) {
		this.bs=bs;
		
	}
	public BattleHandler() {
		
	}
	public boolean clickShip(int x , int y) {
		if (bs.checkShipClick(x,y)) {
			return bs.clickShip(x,y);
		}
		return false;
	}
	
	public int getLayerClicked(int x,int y) {
		return bs.getLayerClicked(x,y);
	}
	
	public boolean checkLeftClick(int x, int y, int button) {
		if(checkButtons(x, y,button)){ return true;}
		return clickShip(x,y);
	}
	public void updateMouse(int x,int y) {
		super.updateMouse(x, y);
		
	}
	public void checkDrag(int x, int y, int button) {
			if(dragging instanceof Button) {
				((Button) dragging ).drag(x,y,button);
			}
			if(dragging instanceof DraggableIcon) {
				((DraggableIcon) dragging ).drag(x,y);
			}
			if(dragging instanceof VerticalSliderHandle){
				((VerticalSliderHandle) dragging).drag(x,y);
			}
		
	}
	public boolean checkPress(int x, int y, int button) {
			if(dragging == null) {
				// check all draggable objects
				for(int i = 0; i<buttons.size();i++) {
					if(buttons.get(i).isDraggable()&&buttons.get(i)!=null && buttons.get(i).isInside(x, y)) {
						dragging = buttons.get(i);
						return true;
					}
				}
				for(int i = 0; i<icons.size();i++) {
					if(icons.get(i).isInside(x, y)) {
						dragging = icons.get(i);
						return true;
					}
				}
				
				for(int i = 0; i<handles.size();i++) {
					if(handles.get(i).isInside(x, y)) {
						dragging = handles.get(i);
						handles.get(i).grabbed(x, y);
						return true;
					}
				}

			}
			return false;
		
	}
	public boolean checkRelease(int x, int y, int button) {
		if(dragging == null) {
			return false;
		}
		if(dragging instanceof DraggableIcon) {
			List<ActionBox> boxes = new ArrayList<ActionBox>();
			boxes.addAll(BattleUI.actionBoxes);
			boxes.addAll(BattleUI.manoeuvreActionBoxes);
			((DraggableIcon) dragging ).drop(boxes);
			if(button == MouseEvent.BUTTON3) {((DraggableIcon)dragging).reset();}
		}
		if (dragging instanceof VerticalSliderHandle){
			((VerticalSliderHandle) dragging).drop();
		}
		dragging = null;
		return true;
		
	}
	public void checkRightClick(int x, int y) {
		checkDraggableIcons(x,y);
		
	}
	private boolean checkDraggableIcons(int x, int y) {
		for(int i = 0; i<icons.size();i++) {
			if(icons.get(i).isInside(x, y)) {
				icons.get(i).reset();
				return true;
			}
		}
		return false;
		
	}
	public static void changeMouseIcon(String path){
		changeMouseIcon(path);
	}
	public BattleScreen getBs() {
		return bs;
	}
	public void setBs(BattleScreen bs) {
		this.bs = bs;
	}
}
