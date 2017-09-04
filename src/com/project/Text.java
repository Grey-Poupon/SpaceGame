package com.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class Text implements Handleable{

	protected int xCoordinate;//  top left coordinates of where the image is to be placed
	protected int yCoordinate;
	private static final float textRatio = 31f/35f;
	//private ImageObserver observer;
	private boolean visible = true;
	private String text;
	private Font font;
	private Color colour;	
	private Shape clip;
	private int justOnce = 0;
	private boolean leftAlign=false;
	
	
	public Text(String text,boolean visible,int x, int y,String fontName, int style, int size, Color colour){
		this.xCoordinate = x;
		this.yCoordinate = y+(int)(size*textRatio);
		this.text        = text;
		this.font        = new Font(fontName, style, size);
		this.colour      = colour;
		Handler.texts.add(this);
	}
	public Text(String text,boolean visible,int x, int y){
		this.xCoordinate = x;
		this.yCoordinate = y+32;
		this.text        = text;	
		this.font        = new Font("Sevensegies", Font.PLAIN, 36);
		this.colour      = Color.WHITE;
		Handler.texts.add(this);
	}
	public void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setColor(colour);
		g2d.setFont(font);
		FontMetrics metrics = g2d.getFontMetrics();
		if(visible){
			if(clip!=null) {
				g2d.setClip(clip);
				if(justOnce == 0) {
					if(metrics.stringWidth(text)>=clip.getBounds().getWidth()) {
						String newText = "";
						String lines = "";
						String words = "";
						for(int i=0;i<text.length();i++) {
							words += text.charAt(i);
							if(text.charAt(i)==' ') {
								if(metrics.stringWidth(lines+words)>=clip.getBounds().getWidth()) {
									lines +="\n";
									newText +=lines;
									lines=words;
								}
								else {
									lines+=words;
								}
								words = "";
							}
							
							
							
						}
						newText+=lines;
						if(metrics.stringWidth(newText+words)>=clip.getBounds().getWidth()) {
							newText+="\n"+words;
						}else {newText+=words;}
						text = newText;
						justOnce +=1;
						leftAlign =true;
					}
			
				}
					for(int i= 0 ; i<text.split("\n").length;i++) {
						if(leftAlign) {g2d.drawString(text.split("\n")[i], xCoordinate, yCoordinate+(i+1)*metrics.getHeight());}
						else{g2d.drawString(text.split("\n")[i], (int) (xCoordinate+(clip.getBounds().getWidth()-metrics.stringWidth(text.split("\n")[i]))/2), yCoordinate+(i)*metrics.getHeight());}
					}
			}else {g2d.drawString(text, xCoordinate, yCoordinate);}
			
		}
	};
	public static void delete(Text t) {
		Handler.texts.remove(t);
		t = null;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public void move(int x , int y) {
		this.xCoordinate = x;
		this.yCoordinate = y;
	}
	public void changeMask(int x , int y, int width, int height) {
		this.clip = new Rectangle2D.Float(x, y, width, height);
	}
	public void removeMask() {
		this.clip = null;		
	}
	@Override
	public void tick() {
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
}
