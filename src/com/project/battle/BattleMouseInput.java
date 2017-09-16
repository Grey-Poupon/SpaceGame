package com.project.battle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.project.Handler;
import com.project.MouseInput;
import com.project.ScrollableList;

public class BattleMouseInput extends MouseInput {
	private static List<ScrollableList> scrollableLists = new ArrayList<ScrollableList>();
	private static int selectedList = 0;
	private BattleHandler hans;
	public BattleMouseInput(Handler hans) {
		super(hans);
		this.hans = (BattleHandler) hans;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		setpointClicked(arg0.getX(), arg0.getY());
		
		if(SwingUtilities.isLeftMouseButton(arg0)) {
			// left click
			if(hans.checkLeftClick(arg0.getX(), arg0.getY(),arg0.getButton())){
				
				int index = checkLists(arg0.getX(), arg0.getY());
				if(index>-1 && selectedList!= index){selectedList = index;}
			}
		}
		else if(SwingUtilities.isRightMouseButton(arg0)) {
			// right click
			hans.checkRightClick(arg0.getX(),arg0.getY());
		}
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		scrollableLists.get(selectedList).scroll(arg0.getWheelRotation());
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		hans.updateMouse((int)arg0.getX(), (int)arg0.getY());
		if(SwingUtilities.isLeftMouseButton(arg0)) {
			hans.checkDrag(arg0.getX(), arg0.getY(),arg0.getButton());
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(SwingUtilities.isLeftMouseButton(arg0)) {
			hans.checkPress(arg0.getX(),arg0.getY(),arg0.getButton());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(SwingUtilities.isLeftMouseButton(arg0)) {
			hans.checkRelease(arg0.getX(),arg0.getY(),arg0.getButton());
		}
	}
	
	
	
	public static void addList(ScrollableList scrollableList) {
		scrollableLists.add(scrollableList);	
	}
	public static void removeList(ScrollableList scrollableList) {
		scrollableLists.remove(scrollableList);	
	}
	public static int checkLists(int x, int y){
		for(int i = 0;i<scrollableLists.size();i++){
			ScrollableList current = scrollableLists.get(i);
			if(x > current.getX() && x < current.getX() + current.getWidth()){
				if(y > current.getY() && y < current.getY() + current.getHeight()){
					return i;
				}
			}
		}
		return -1;
	}

	
}
