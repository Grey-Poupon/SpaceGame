package com.project;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseInput implements MouseListener,MouseMotionListener,MouseWheelListener {

	public static Point mousePosition = new Point(0,0);
	public static Point pointClicked = new Point(0,0);
	protected Handler hans;
	public MouseInput(Handler hans){
		this.hans = hans;
	}
	
	
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
		hans.checkClick(arg0.getX(), arg0.getY());
		//System.out.println(Integer.toString(arg0.getX())+" "+Integer.toString(arg0.getY()));
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
		hans.updateMouse((int)arg0.getX(), (int)arg0.getY());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		setMousePosition(arg0.getX(), arg0.getY());
		hans.updateMouse((int)arg0.getX(), (int)arg0.getY());
	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
	}

}
