package com.project;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener,MouseMotionListener {

	public static Point mousePosition = new Point(0,0);
	public static Point pointClicked = new Point(0,0);
	
	public Point getMousePosition() {
		return mousePosition;
	}

	public static void setMousePosition(int x , int y) {
		mousePosition = new Point(x,y);
	}
	public static Point getpointClicked() {
		return pointClicked;
	}
	public static void setpointClicked(int x , int y) {
		pointClicked = new Point(x,y);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		setpointClicked(arg0.getX(), arg0.getY());
		UI.checkClick(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		UI.updateMouse((int)arg0.getX(), (int)arg0.getY());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		setMousePosition(arg0.getX(), arg0.getY());
		UI.updateMouse((int)arg0.getX(), (int)arg0.getY());
	}

}
