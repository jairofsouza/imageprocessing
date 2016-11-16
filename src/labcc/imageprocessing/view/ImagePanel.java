package labcc.imageprocessing.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = -2670846298165086436L;
	
	private BufferedImage image;

	public ImagePanel(BufferedImage img) {
		image = img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0,image.getWidth(), image.getHeight(), this); 
	}

}
