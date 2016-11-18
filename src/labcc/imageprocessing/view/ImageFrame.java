package labcc.imageprocessing.view;

import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.JFrame;

public class ImageFrame {
	
	BufferedImage i;
	int h, w;
	
	
	public ImageFrame(BufferedImage i, int w, int h) {
		this.i = i;
		this.h = h;
		this.w = w;
	}
	
	public void show() {
		ImagePanel panel = new ImagePanel(this.i);
		JFrame snakeFrame = new JFrame();
        GroupLayout layout = new GroupLayout(panel);
		snakeFrame.setSize(this.w, this.h);
		snakeFrame.setLocationRelativeTo(null);	//centraliza na tela
		snakeFrame.setVisible(true);
		panel.setLayout(layout);
		snakeFrame.add(panel);
		snakeFrame.pack();
		snakeFrame.setSize(this.w, this.h);
	}

}
