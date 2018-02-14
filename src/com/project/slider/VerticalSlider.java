package com.project.slider;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import com.project.EntityID;
import com.project.ImageHandler;
import com.project.battle.BattleHandler;
import com.project.battle.BattleScreen;
import com.project.slider.VerticalSliderHandle;

public class VerticalSlider {
	private SliderID id;
	private int maxStep;
	private Observer obs;
	private int x,y;
	private VerticalSliderHandle handle;
	private ImageHandler panelImg;
	
	public VerticalSlider(int x, int y, int stepLen, int maxStep, int curStep, Observer obs, SliderID id, BufferedImage panelImg, BufferedImage handleImg, BattleHandler hans) {
		this.id = id;
		this.maxStep = maxStep;
		this.obs = obs;
		this.x = x;
		this.y = y;
		this.handle = new VerticalSliderHandle(x, y, stepLen, maxStep, curStep, handleImg, obs, id);
		this.panelImg = new ImageHandler(x, y, panelImg, true, EntityID.UI);
		hans.addLowPriorityEntity(this.panelImg);
		hans.addLowPriorityEntity(handle.getImg());
		hans.handles.add(handle);
	}
	public void moveSliderTo(int step){
		if(step>0 && step<maxStep+1 && step!=handle.getStep()){
			handle.moveTo(step);
		}
	}
	public int getStep(){
		return handle.getStep();
	}
	public void setX(int x) {
		this.x = x;
		this.panelImg.setxCoordinate(x);
		handle.setX(x);
		handle.setStartX(x);
	}
	public void setY(int y) {
		this.y = y;
		this.panelImg.setyCoordinate(y);
		handle.setY(y);
		handle.setStartY(y);
	}
	public static void delete(VerticalSlider s) {
		ImageHandler.delete(BattleScreen.handler, s.panelImg);
		VerticalSliderHandle.delete(s.handle);
		s.handle.deleteObservers();

		
	}

}
