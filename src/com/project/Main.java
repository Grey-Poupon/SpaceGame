package com.project;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Random;

import com.project.battle.BattleScreen;
import com.project.button.ButtonID;
import com.project.ship.Ship;



public class Main  extends Canvas implements Runnable{

	private static final long serialVersionUID = -1383369434483310525L;
	Thread thread;
	boolean running;
	public Handler handler;
	Random r;
	protected Window window;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public Player player;
	public Ship playerShip; 
	public UI ui;
	protected MouseInput mouseIn;
	protected KeyInput keyIn;
	private boolean paused=false;
	
	public Main(){
		r = new Random();
		handler = new Handler();
		player = new Player(100,RaceID.bugBitch);
		window = new Window(WIDTH,HEIGHT,"Space Game",this);
		}

	protected void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		if(!isPaused()) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,WIDTH,HEIGHT);
			handler.render(g);
			window.update();
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
		bs.show();

	}

	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public synchronized void start(){
		
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while(delta>=1){
				tick();
				delta --;
			}
			if(running)
				render();
			
			frames++;
			
			if(System.currentTimeMillis() - timer >1000){
				timer +=1000;
//				System.out.println("FPS: "+frames);
				
				frames = 0;
				
			}
		}
		stop();
	}
	public void tick() {
		handler.tick(ui);
	}
	
	public static void main(String[] args){
		new ResourceLoader();
		new BattleScreen();
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void update(Object arg1) {
		// TODO Auto-generated method stub
		
	}

	public void update(ButtonID ID, int index, int button) {
		// TODO Auto-generated method stub
		
	}

	
}