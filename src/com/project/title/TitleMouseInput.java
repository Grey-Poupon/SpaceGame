package com.project.title;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.project.Handler;
import com.project.MouseInput;

public class TitleMouseInput extends MouseInput{
	


	
	public TitleMouseInput(Handler hans) {
		super(hans);
	

	}
	
	
	
	public void mouseClicked(MouseEvent arg0){
		
		for(int i = 0; i<Title.tabs.size();i++) {
			if(Title.tabs.get(i).contains(mousePosition)) {
				System.out.println("ADUFH");
				Title.clickTab(i);
			}
		}
		
	}
	
	public void mouseMoved(MouseEvent arg0) {
		super.mouseMoved(arg0);
	}
	
	private void mouseShop(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseShopClick(MouseEvent arg0) {
		ArrayList<Rectangle> buttons;
	}
	

}
