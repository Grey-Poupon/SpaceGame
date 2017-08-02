package com.project;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.project.battle.BattleUI;
import com.project.button.Button;

public class ScrollableList  {
	 private List<Button> buttons;
	 private int xCoordinate;
	 private int yCoordinate;
	 private int width;
	 private int height;
	 private final int scrollAmplifier = 20;
	public ScrollableList(List<Button> buttons, int x, int y, int width, int height) {
		this.buttons = buttons;
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.width = width;
		this.height = height;
		for(Button btn:buttons) {
			btn.setTextMask(x, y, width, height);
			btn.setClickable(false);
			BattleUI.addCrewButton(btn);
			btn.setImgMask(x, y, width, height);}
		placeButtons();
	}
	private void placeButtons() {
		int bottom = yCoordinate;
		boolean inList = true;
		for (int i=0;i<buttons.size();i++) {
			buttons.get(i).move(xCoordinate,bottom);
			buttons.get(i).setClickable(inList);
			bottom+=buttons.get(i).getHeight();
			if(inList && bottom>yCoordinate+height) {
				int btnHeight = buttons.get(i).getHeight();
				buttons.get(i).changeMask(xCoordinate,yCoordinate,width,btnHeight-(yCoordinate+height-bottom));
				inList = false;
			}
		}
	}
	public void scroll(int y) {
		y*=scrollAmplifier;
		for (Button btn:buttons) {
			btn.shift(0, y);
			if(btn.getY()<yCoordinate) {
				if(btn.getY()+btn.getHeight()<yCoordinate) {
					btn.setClickable(false);
				}
				else {
					btn.setClickable(true);
					btn.changeMask(btn.getX(),yCoordinate,width,btn.getHeight()-(yCoordinate-(btn.getY())));// mask 4 top cutoff
				}
			}
			else if(btn.getY()+btn.getHeight()>yCoordinate+height) {
				if(btn.getY()>yCoordinate+height) {
					btn.setClickable(false);
				}
				else {
					btn.setClickable(true);
					btn.changeMask(btn.getX(),btn.getY(),width,(yCoordinate+height)-btn.getY());// mask 4 bottom cutoff

				}
			}
			else {btn.setClickable(true);}
		}
	}


}
