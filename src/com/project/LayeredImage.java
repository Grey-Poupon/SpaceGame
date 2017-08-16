package com.project;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LayeredImage {
	public int x;
	public int y;
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
	public int[] layersX;
	public int[] layersY;
	public int largestWidth;
	public int largestHeight;
	public String path;
	private float zPerLayer;
	private float z;
	
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
		for(int i = 0; i<layers.size();i++) {
			setScaling(layers.get(i),i);
		}
		
	}
	
	private void setScaling(ImageHandler e,int index) {
		float f = Math.abs((float)e.getZ()/(float)(e.getZ()-cameraZ));
		double d1 =Math.atan2(cameraX, e.getZ());
		double d = (Math.cos(d1)*scale*(f));
		e.setXScale((float)d);
		if(cameraX>0) {
			e.setxCoordinate((int)(this.x+e.getXScale()*layersX[layersX.length-index-1]));
		}
		else {
			e.setxCoordinate((int)(this.x+e.getXScale()*layersX[layersX.length-index-1] + (this.largestWidth*(scale-(e.getXScale())))));
		}
		e.setYScale((float) Math.cos(Math.atan2(cameraY, e.getZ()))*scale*(Math.abs((float)e.getZ()/(float)(e.getZ()-cameraZ))));	    
		if(cameraY>0) {
			e.setyCoordinate((int)(this.y+e.getYScale()*layersY[layersY.length-index-1]));
		}
		else {
			e.setyCoordinate((int)(this.y+e.getYScale()*layersY[layersY.length-index-1] + (this.largestHeight*(scale-(e.getYScale())))));
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
			ImageHandler layer = new ImageHandler(x+layersX[layersX.length-i-1],y+layersY[layersY.length-i-1],this.path+"/data/layer"+Integer.toString(i)+".png",true,EntityID.shipLayer);
			if(i==0) {layer.setVisible(false);}
			layer.setZ((float)(this.z-i*zPerLayer));
			layers.add(layer);
			
		}
				
	}
	
	public void getLayerCoords() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/"+this.path+"/info.txt"), "UTF-8"))) {
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
		    String[] split = everything.split(",");
		    layersX = new int[split.length/2];
		    layersY = new int[split.length/2];
		    for(int i =0; i<split.length/2;i++) {
		    	layersX[i]=Integer.parseInt(split[2*i]);
		    	layersY[i]=Integer.parseInt(split[1+(2*i)]);
		    }
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
		this.noLayers=this.layersX.length;
	}
}
