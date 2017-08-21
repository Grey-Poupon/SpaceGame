package com.project;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.project.ship.Slot;

public class LayeredImage {
	private int x;
	private int y;
	public float scale = 3.5f;
	public double anglePhi;
	public double angleTheta;
	public static float cameraZ;
	public static float cameraX;
	public static float cameraY;
	public ArrayList<ImageHandler> layers;
	public int noLayers;
	public int tickerZ;
	public int tickerX;
	public int tickerY;
	public ArrayList<Integer> layersX=new ArrayList<>();;
	public ArrayList<Integer> layersY=new ArrayList<>();;
	private ArrayList<Slot> slots= new ArrayList<>();
	public String[] layerIsSlot;
	public int largestWidth;
	public int largestHeight;
	public String path;
	private float zPerLayer;
	private float z;
	private Random rand = new Random();
	private boolean destroyed = false;
	
	public LayeredImage(int x,int y, String path,float z,float zPerLayer) {
		this.path = path;
		this.zPerLayer = zPerLayer;
		this.x= x;
		this.y =y;
		this.z = z;
		tickerX =1;
		tickerY =1;
		tickerZ =1;
		layers = new ArrayList<ImageHandler>();
		loadLayers();
		largestWidth = getLargestWidth();
		largestHeight = getLargestHeight();
		cameraZ = -0.3f;
		cameraX = 0;
		cameraY = 0;
	}
	public LayeredImage(int x,int y, String path,float z,float zPerLayer,float scale) {
		this.path = path;
		this.zPerLayer = zPerLayer;
		this.x= x;
		this.y =y;
		this.z = z;
		this.scale = scale;
		tickerX =1;
		tickerY =1;
		tickerZ =1;
		layers = new ArrayList<ImageHandler>();
		loadLayers();
		largestWidth = getLargestWidth();
		largestHeight = getLargestHeight();
		cameraZ = -0.3f;
		cameraX = 0;
		cameraY = 0;
		
	}
	public void tick() {
		if(cameraZ>=1.5||cameraZ<=-1.25) {
			tickerZ*=-1;
		}
		if(cameraX>=5||cameraX<=-5) {
			tickerX*=-1;
		}
		if(cameraY>=15||cameraY<=-15) {
			tickerY*=-1;
		}
		//cameraY+=0.03*tickerY;
		cameraX+=0.03*tickerX;
		//cameraZ+=0.03*tickerZ;
		if(!destroyed) {
			for(int i = 0; i<layers.size();i++) {
				setScaling(layers.get(i),i);
				if(layers.get(i).isVisible()) {
					setLayersShading(layers.get(i));
				}
			}
		}
		
	}
	
	public void destruct() {
		if(!destroyed) {
		for(int i = 0; i<layers.size();i++) {
			if(rand.nextBoolean()) {layers.get(i).setXVel((rand.nextInt(2)+1));}
			else {layers.get(i).setXVel(-1*(rand.nextInt(2)+1));}
			
			if(rand.nextBoolean()) {layers.get(i).setYVel((rand.nextInt(2)+1));}
			else {layers.get(i).setYVel((-1*(rand.nextInt(2)+1)));}
		}
		destroyed= true;
		}
	}
	
	private void setScaling(ImageHandler e,int index) {
		float f = Math.abs((float)e.getZ()/(float)(e.getZ()-cameraZ));
		double d1 =Math.atan2(cameraX, e.getZ());
		double d = (Math.cos(d1)*scale*(f));
		e.setXScale((float)d);
		if(cameraX>0) {
			e.setxCoordinate((int)(this.x+e.getXScale()*layersX.get(layersX.size()-index-1)));
		}
		else {
			e.setxCoordinate((int)(this.x+e.getXScale()*layersX.get(layersX.size()-index-1) + (this.largestWidth*(scale-(e.getXScale())))));
		}
		e.setYScale((float) Math.cos(Math.atan2(cameraY, e.getZ()))*scale*(Math.abs((float)e.getZ()/(float)(e.getZ()-cameraZ))));	    
		if(cameraY>0) {
			e.setyCoordinate((int)(this.y+e.getYScale()*layersY.get(layersY.size()-index-1)));
		}
		else {
			e.setyCoordinate((int)(this.y+e.getYScale()*layersY.get(layersY.size()-index-1) + (this.largestHeight*(scale-(e.getYScale())))));
		}	
	}
		
	private int getLargestWidth() {
		int largest = 0;
		for(int i =0;i<layers.size();i++) {
			if(layers.get(i).getImg().getWidth()>largest) {
				largest  = layers.get(i).getImg().getWidth();
			}
		}
		return largest;
	}
	private int getLargestHeight() {
		int largest = 0;
		for(int i =0;i<layers.size();i++) {
			if(layers.get(i).getImg().getWidth()>largest) {
				largest  = layers.get(i).getImg().getHeight();
			}
		}
		return largest;
	}
	
	private void loadLayers() {
		getLayerCoords();
		setNoLayers();
		for(int i =0; i<noLayers;i++) {
			
				ImageHandler layer = new ImageHandler(x+layersX.get(layersX.size()-i-1),y+layersY.get(layersY.size()-i-1),this.path+"/data/layer"+Integer.toString(i)+".png",true,EntityID.shipLayer);
				layer.setZ((float)(this.z-i*zPerLayer));
				if(layerIsSlot[layersX.size()-i-1]!="!") {
					layer.setVisible(false);
					Slot slot=null;
					if(layerIsSlot[layersX.size()-i-1].contains("s")) {
						 slot = new Slot(x+layersX.get(layersX.size()-i-1),y+layersY.get(layersY.size()-i-1),'s');
					}
					if(layerIsSlot[layersX.size()-i-1].contains("m")) {
						 slot = new Slot(x+layersX.get(layersX.size()-i-1),y+layersY.get(layersY.size()-i-1),'m');
					}
					if(layerIsSlot[layersX.size()-i-1].contains("l")) {
						 slot = new Slot(x+layersX.get(layersX.size()-i-1),y+layersY.get(layersY.size()-i-1),'l');
					}
					slots.add(slot);
					
					
					
				}
				layers.add(layer);
			
			
			//if(i==0) {layer.setVisible(false);}
			
			
		}
		
				
	}
	
	public void getLayerCoords() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/"+this.path+"/info.txt")))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    everything = everything.replaceAll("(\\r|\\n)", ",");
		    everything = everything.replaceAll(",,", ",");
		    everything=everything.substring(0, everything.length()-1);
		    //System.out.println(everything);
		    String[] split = everything.split(",");
		    ArrayList<String> splitList =  new ArrayList<String>();
		    
		    
		    
		    int counter= 0;
		    int layerCounter = 0;
		    layerIsSlot = new String[split.length/2];
		    for(int i =0;i<split.length;i++) {
		    	if(counter==2) {
		    		counter =0;
		    		layerCounter++;
		    		layerIsSlot[layerCounter]="!";
		    		
		    	}
		    	if(split[i].contains("m")||split[i].contains("s")||split[i].contains("l")) {
		    		layerIsSlot[layerCounter]=split[i];
		    	}
		    	else {
		    		counter++;
		    		splitList.add(split[i]);
		    		
		    	}
		    }
		    
		    for(int i = 0;i<layerIsSlot.length;i++) {
		    	if(layerIsSlot[i]==null) {
		    		layerIsSlot[i]="!";
		    	}
		    }
//		    for(int i =0; i<layerIsSlot.length;i++) {
//		    	System.out.println(layerIsSlot[i]);
//		    }
//		    
//		    
//		    
//		    for(int i =0; i<split.length;i++) {
//		    	System.out.println(split[i]);
//		    }
		    
		    
		    for(int i =0; i<splitList.size()/2;i++) {
		    	layersX.add(Integer.parseInt(splitList.get(2*i)));
		    	layersY.add(Integer.parseInt(splitList.get(1+(2*i))));
		    }
//		    for(int i =0;i<layersX.length;i++) {
//		    	System.out.print(Integer.toString(layersX[i]));
//		    	System.out.print(Integer.toString(layersY[i]));
//		    }
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void organiseLayers() {
		ArrayList<ImageHandler> sortedLayers = new ArrayList<ImageHandler>();
		int oriSize = layers.size();
		int index = 0;
		float biggestZ = 0;
		while(oriSize>sortedLayers.size()) {
			for(int i = 0;i<layers.size();i++) {
				if(layers.get(i).getZ()>biggestZ) {
					biggestZ = layers.get(i).getZ();
					index =i;
				}
			}
			sortedLayers.add(layers.get(index));
			layers.remove(index);
		}
		layers = sortedLayers;
	}
	public void setNoLayers() {
		this.noLayers=layersX.size();
	}
	public ArrayList<Slot> getSlots() {
		return slots;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setSlots(ArrayList<Slot> slots) {
		this.slots = slots;
	}
	
	public void setLayersShading(ImageHandler e){
		BufferedImage img = new BufferedImage(e.getImg().getWidth(), e.getImg().getHeight(), BufferedImage.TRANSLUCENT);
		img  = e.getImg();
			for(int x =0;x< e.getImg().getWidth();x++) {
				for(int y=0;y<e.getImg().getHeight();y++) {

					//this Line actually works
					float shade  = ((e.getZ()/z)); 
//					float shade  = (cameraZ-editable.layerZ[0])/(cameraZ-e.getZ()-(e.getImg().getWidth()-x)*cameraX/e.getImg().getWidth());
//					float shade = (float) ((e.getZ()+10*((e.getxCoordinate()+x)/this.largestWidth)*Math.sqrt(9-(e.getXScale()*e.getXScale())))/(e.getImg().getWidth()+editable.layerZ[editable.layerZ.length-1]+5));
//					shade = 1-shade*0.5f;
					
					Color col = new Color(e.getOriImg().getRGB(x,y),true);
					int a = (int)(col.getAlpha());
					if(a==0) {
						continue;
					}
					int r = (int) (col.getRed()*(1-0.5*shade));
					int g = (int) (col.getGreen()*(1-0.5*shade));
					int b = (int) (col.getBlue()*(1-0.5*shade));
					
					int rgba = (a << 24) | (r << 16) | (g << 8) | b;
					//if(shade<1) {img.setRGB(x,y,new Color(r,g,b,a).getRGB());}						
					

					img.setRGB(x,y,rgba);
				}
			}
			e.setImg(img);
	}	
}
