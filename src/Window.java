

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class Window extends Canvas {
	
	
	private static final long serialVersionUID = -7259108873705494293L;

	public Window(int width, int height, String title, Main game){
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width,height));
		//frame.setMaximumSize(new Dimension(width,height));
		//frame.setMinimumSize(new Dimension(width,height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		frame.setCursor(frame.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null"));
		game.start();
	}
}
