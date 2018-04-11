package com.project.title;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;

import com.project.Handler;
import com.project.KeyInput;
import com.project.Main;
import com.project.MouseInput;
import com.project.Phase;
import com.project.phase2.Phase2;

public class Title implements Phase {

	public static Main main;
	private Handler handler = new Handler();
	private KeyInput keyIn;
	private MouseInput mouseIn;
	public static ArrayList<Rectangle> tabs = new ArrayList<Rectangle>();
	
	
	public Title(Main main)  {
		this.main = main;
		for(int i = 0; i<3 ;i ++) {
			tabs.add(new Rectangle(100, 100 +100*i,500,75));
		}
		
		keyIn = new TitleKeyInput();
		mouseIn = new TitleMouseInput(handler);
		addListeners(main);
	}
	
	public static void clickTab(int i) {
		if(i ==0 ) {
			main.setPhase(new Phase2(main));
		}
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, main.WIDTH, main.HEIGHT);;
		handler.render(g);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setFont(new Font("Sevensegies",Font.PLAIN,36));
		g2d.setColor(Color.WHITE);
		for(int i =0; i<tabs.size();i++) {
			g2d.setColor(Color.cyan);
			g2d.draw(tabs.get(i));
			g2d.drawString("Start "+ i,150,150+i*100);
		}
	}

	@Override
	public MouseInput getMouseInput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyInput getKeyInput() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addListeners(Main main) {
		main.addKeyListener(keyIn);
		main.addMouseListener(mouseIn);
		main.addMouseMotionListener(mouseIn);
		main.addMouseWheelListener(mouseIn);
	}	
	

}
