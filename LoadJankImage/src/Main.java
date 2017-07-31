package LoadJankImage.src;


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
	protected MouseInput mouseIn;
	protected KeyInput keyIn;
	private LayeredImage lImage;
	private Random rand;

	public Main(){
		rand = new Random();
		for(int i=0; i<30;i++) {
			Star star = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"star.png",true);
		}
		lImage = new LayeredImage(400,200);
		r = new Random();
		mouseIn = new MouseInput();
		keyIn = new KeyInput();
		this.addKeyListener(keyIn);
		this.addMouseListener(mouseIn);
		this.addMouseMotionListener(mouseIn);
		handler = new Handler();
		window = new Window(WIDTH+18,HEIGHT+45,"Space",this);
		}

	protected void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
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
				System.out.println("FPS: "+frames);
				
				frames = 0;
				
			}
		}
		stop();
	}
	
	public void tick() {
		handler.tick();
		lImage.tick();
		keyIn.tick();
		
	}
	
	
	public static void main(String[] args){
		new Main();
	}
	
}