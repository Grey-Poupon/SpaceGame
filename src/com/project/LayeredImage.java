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
	private float scale = 3.5f;
	private double anglePhi;
	private double angleTheta;
	private static float cameraZ;
	private static float cameraX;
	private static float cameraY;
	private ArrayList<ImageHandler> layers;
	private int noLayers;
	private int tickerZ;
	private int tickerX;
	private int tickerY;
	private ArrayList<Integer> layersX=new ArrayList<>();;
	private ArrayList<Integer> layersY=new ArrayList<>();;
	private ArrayList<Slot> backSlots= new ArrayList<>();
	private ArrayList<Slot> frontSlots= new ArrayList<>();
	private String[] layerIsSlot;
	private int largestWidth;
	private int largestHeight;
	private String path;
	public float getScale() {
		return scale;
	}
	public void setScale(float scale) {
		this.scale = scale;
	}
	public static float getCameraZ() {
		return cameraZ;
	}
	public static void setCameraZ(float cameraZ) {
		LayeredImage.cameraZ = cameraZ;
	}
	public static float getCameraX() {
		return cameraX;
	}
	public static void setCameraX(float cameraX) {
		LayeredImage.cameraX = cameraX;
	}
	public static float getCameraY() {
		return cameraY;
	}
	public static void setCameraY(float cameraY) {
		LayeredImage.cameraY = cameraY;
	}
	public ArrayList<ImageHandler> getLayers() {
		return layers;
	}
	public void setLayers(ArrayList<ImageHandler> layers) {
		this.layers = layers;
	}
	public int getNoLayers() {
		return noLayers;
	}
	public void setNoLayers(int noLayers) {
		this.noLayers = noLayers;
	}
	public ArrayList<Integer> getLayersX() {
		return layersX;
	}
	public void setLayersX(ArrayList<Integer> layersX) {
		this.layersX = layersX;
	}
	public ArrayList<Integer> getLayersY() {
		return layersY;
	}
	public void setLayersY(ArrayList<Integer> layersY) {
		this.layersY = layersY;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public float getzPerLayer() {
		return zPerLayer;
	}
	public void setzPerLayer(float zPerLayer) {
		this.zPerLayer = zPerLayer;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	public boolean isDestroyed() {
		return destroyed;
	}
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setLargestWidth(int largestWidth) {
		this.largestWidth = largestWidth;
	}
	public void setLargestHeight(int largestHeight) {
		this.largestHeight = largestHeight;
	}

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
			for(int i=0;i<backSlots.size();i++) {
				Slot s =backSlots.get(i);
				setScaling(s);
				s.setX(layers.get(s.getLayerIndex()).getxCoordinate());
				s.setY(layers.get(s.getLayerIndex()).getyCoordinate());
				s.setWidth((int) (layers.get(s.getLayerIndex()).getXScale()*layers.get(s.getLayerIndex()).getImg().getWidth()));
				s.setHeight((int) (layers.get(s.getLayerIndex()).getYScale()*layers.get(s.getLayerIndex()).getImg().getHeight()));
			}
			for(int i=0;i<frontSlots.size();i++) {
				Slot s =frontSlots.get(i);
				setScaling(s);
				s.setX(layers.get(s.getLayerIndex()).getxCoordinate());
				s.setY(layers.get(s.getLayerIndex()).getyCoordinate());
				s.setWidth((int) (layers.get(s.getLayerIndex()).getXScale()*layers.get(s.getLayerIndex()).getImg().getWidth()));
				s.setHeight((int) (layers.get(s.getLayerIndex()).getYScale()*layers.get(s.getLayerIndex()).getImg().getHeight()));
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
	
	private void setScaling(Slot s) {
		float f = Math.abs((float)s.getZ()/(float)(s.getZ()-cameraZ));
		double d1 =Math.atan2(cameraX, s.getZ());
		double d = (Math.cos(d1)*scale*(f));
		s.getSlotItem().getSlotItemBody().setxScale((float)d);
		if(cameraX>0) {
			s.setX((int)(this.x+s.getSlotItem().getSlotItemBody().getxScale()*layersX.get(layersX.size()-s.getLayerIndex()-1)));
		}
		else {
			s.setX((int)(this.x+s.getSlotItem().getSlotItemBody().getxScale()*layersX.get(layersX.size()-s.getLayerIndex()-1) + (this.largestWidth*(scale-(s.getSlotItem().getSlotItemBody().getxScale())))));
		}
		s.getSlotItem().getSlotItemBody().setyScale((float) Math.cos(Math.atan2(cameraY, s.getZ()))*scale*(Math.abs((float)s.getZ()/(float)(s.getZ()-cameraZ))));	    
		if(cameraY>0) {
			s.setY((int)(this.y+s.getSlotItem().getSlotItemBody().getyScale()*layersY.get(layersY.size()-s.getLayerIndex()-1)));
		}
		else {
			s.setY((int)(this.y+s.getSlotItem().getSlotItemBody().getyScale()*layersY.get(layersY.size()-s.getLayerIndex()-1) + (this.largestHeight*(scale-(s.getSlotItem().getSlotItemBody().getyScale())))));
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
				if(!layerIsSlot[layersX.size()-i-1].contains("!")) {
					layer.setVisible(false);
					Slot slot = new Slot(x+layersX.get(layersX.size()-i-1),y+layersY.get(layersY.size()-i-1),layersX.get(layersX.size()-i-1),layersY.get(layersY.size()-i-1),layer.getImg().getWidth(),i,layerIsSlot[layersX.size()-i-1].contains("f"),(float)(this.z-i*zPerLayer));
					if(slot.isFront()) {
						frontSlots.add(slot);
					}
					else {
						backSlots.add(slot);
					}
					
					
					
					
				}
				layers.add(layer);			
			
		}
		organiseSlots();
		
				
	}
	
	private void organiseSlots() {
		
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
		    	if(split[i].contains("f")||split[i].contains("b")) {
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
	public ArrayList<Slot> getBackSlots() {
		return backSlots;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setBackSlots(ArrayList<Slot> slots) {
		this.backSlots = slots;
	}
	
	public ArrayList<Slot> getFrontSlots() {
		return frontSlots;
	}
	public void setFrontSlots(ArrayList<Slot> frontSlots) {
		this.frontSlots = frontSlots;
	}
	public void setLayersShading(ImageHandler e){
		BufferedImage img = new BufferedImage(e.getImg().getWidth(), e.getImg().getHeight(), BufferedImage.TRANSLUCENT);
		img  = e.getImg();
			for(int x =0;x< e.getImg().getWidth();x++) {
				for(int y=0;y<e.getImg().getHeight();y++) {

					//this Line actually works
					float shade  = ((e.getZ()/z)); 

					Color col = new Color(e.getOriImg().getRGB(x,y),true);
					int a = (int)(col.getAlpha());
					if(a==0) {continue;}
					int r = (int) (col.getRed()*(1-0.5*shade));
					int g = (int) (col.getGreen()*(1-0.5*shade));
					int b = (int) (col.getBlue()*(1-0.5*shade));
					
					int rgba = (a << 24) | (r << 16) | (g << 8) | b;						
					

					img.setRGB(x,y,rgba);
				}
			}
			e.setImg(img);
	}	
}
