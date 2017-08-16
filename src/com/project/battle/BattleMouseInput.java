package com.project.battle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

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
		if(hans.checkClick(arg0.getX(), arg0.getY(),arg0.getButton())){
			
			int index = checkLists(arg0.getX(), arg0.getY());
			if(index>-1 && selectedList!= index){selectedList = index;}
		}
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		scrollableLists.get(selectedList).scroll(arg0.getWheelRotation());
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
