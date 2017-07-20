package com.project;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;



public class Main  extends Canvas implements Runnable{

	private static final long serialVersionUID = -1383369434483310525L;
	Thread thread;
	boolean running;
	public Handler handler;
	Random r;
	Window window;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public Ship playerShip; 
	public UI ui;
	public Player player;
	protected KeyInput keyIn;

	public Main(){
		r = new Random();
		//this.addKeyListener(keyIn);
		this.addMouseListener(new MouseInput());
		this.addMouseMotionListener(new MouseInput());
		handler = new Handler();
		//ui = new UI();
		player = new Player(100,RaceID.bugBitch);
		
		window = new Window(WIDTH+18,HEIGHT+45,"Space",this);
		
		}

	protected void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.CYAN);
		g.fillRect(0,0,WIDTH,HEIGHT);
		
		handler.render(g);
		window.update();
		
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
				//System.out.println("FPS: "+frames);
				
				frames = 0;
				
			}
		}
		stop();
	}
	public void tick() {
	
		handler.tick(ui);
		
	}
	
	
	public static void main(String[] args){
		new BattleScreen();
	}
	
}