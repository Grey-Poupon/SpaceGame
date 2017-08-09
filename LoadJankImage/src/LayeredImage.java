import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class LayeredImage {
	public int x;
	public int y;
	public double scale;
	public double anglePhi;
	public double angleTheta;
	public static float cameraZ;
	public static float cameraX;
	public static float cameraY;
	public static ArrayList<Entity> layers;
	public int noLayers;
	public int tickerZ;
	public int tickerX;
	public int tickerY;
	public int[] layersX;
	public int[] layersY;
	public int largestWidth;
	public int largestHeight;
	public String path;
	private int zPerLayer;
	private Editable editable;
	
	public LayeredImage(int x,int y) {
		editable = new Editable();
		this.path = editable.path;
		this.x= x;
		this.y =y;
		tickerX =1;
		tickerY =1;
		tickerZ =1;
		layers = new ArrayList<Entity>();
		loadLayers();
		largestWidth = getLargestWidth();
		largestHeight = getLargestHeight();
		cameraZ = -0.3f;
		cameraX = 0;
		cameraY = 0;
	}
	
	public void tick() {
		
		for(int i = 0; i<layers.size();i++) {
			setScaling(layers.get(i),i);
			setLayersShading(layers.get(i));
		}
		
		
	}
	
	private void setScaling(Entity e,int index) {
		float f = Math.abs((float)e.getZ()/(float)(e.getZ()-cameraZ));
		double d1 =Math.atan2(cameraX, e.getZ());
		double d = (Math.cos(d1)*3*(f));
		e.setXScale((float)d);
		if(cameraX>0) {
			e.setxCoordinate((this.x+e.getXScale()*layersX[layersX.length-index-1]));
		}
		else {
			//e.setxCoordinate((this.x+e.getXScale()*layersX[layersX.length-index-1] + (this.largestWidth*(3-(e.getXScale())))));
			e.setxCoordinate(this.x+e.getXScale()*layersX[layersX.length-index-1] + (this.largestWidth*(3-(e.getXScale()))));
		}
		e.setYScale((float) Math.cos(Math.atan2(cameraY, e.getZ()))*3*(Math.abs((float)e.getZ()/(float)(e.getZ()-cameraZ))));	    
		if(cameraY>0) {
			e.setyCoordinate((this.y+e.getYScale()*layersY[layersY.length-index-1]));
		}
		else {
			//e.setyCoordinate((this.y+e.getYScale()*layersY[layersY.length-index-1] + (this.largestHeight*(3-(e.getYScale())))));
			e.setyCoordinate((this.y+e.getYScale()*layersY[layersY.length-index-1] + (this.largestHeight*(3-(e.getYScale())))));
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
			if(layers.get(i).getImg().getHeight()>largest) {
				largest  = layers.get(i).getImg().getHeight();
			}
		}
		return largest;
	}
	
	private void loadLayers() {
		getLayerCoords();
		setNoLayers();
		for(int i =0; i<noLayers;i++) {
			Entity layer = new Entity(x+layersX[layersX.length-i-1],y+layersY[layersY.length-i-1],this.path+"/data/layer"+Integer.toString(i)+".png",true);
			if(i==0) {layer.setVisible(false);}
			layer.setZ((float)(editable.layerZ[editable.layerZ.length-i-1]));
			layers.add(layer);
			
		}
		
		
				
	}
	
	public void getLayerCoords() {
		try(BufferedReader br = new BufferedReader(new FileReader("res/"+this.path+"/info.txt"))) {
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
//		    System.out.println(everything);
		    String[] split = everything.split(",");
		    
//		    for(int i =0; i<split.length;i++) {
//		    	System.out.println(split[i]);
//		    }
		    layersX = new int[split.length/2];
		    layersY = new int[split.length/2];
		    for(int i =0; i<split.length/2;i++) {
		    	layersX[i]=Integer.parseInt(split[2*i]);
		    	layersY[i]=Integer.parseInt(split[1+(2*i)]);
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
		ArrayList<Entity> sortedLayers = new ArrayList<Entity>();
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
	
	public void setLayersShading(Entity e){
		BufferedImage img = new BufferedImage(e.getImg().getWidth(), e.getImg().getHeight(), BufferedImage.TRANSLUCENT);
			for(int x =0;x< e.getImg().getWidth();x++) {
				for(int y=0;y<e.getImg().getHeight();y++) {
//					float xheight = (e.xScale/3)*x/(e.getImg().getWidth());
//					float yheight= (e.yScale/3)*y/(e.getImg().getHeight());
//					
					//float shade = yheight*xheight/(e.getImg().getWidth()*e.getImg().getHeight());
//					float shade = xheight*yheight; 
					float shade  = (cameraZ-editable.layerZ[0])/(cameraZ-e.getZ());
					Color col = new Color(e.getOriImg().getRGB(x,y),true);
					int r = (int) (col.getRed()*(shade));
					int g = (int) (col.getGreen()*(shade));
					int b = (int) (col.getBlue()*(shade));
					int a = (int)(col.getAlpha());
					int rgba = (a << 24) | (r << 16) | (g << 8) | b;
					img.setRGB(x,y,rgba);
					e.setImg(img);
				}
			}	
	}	
	
}
