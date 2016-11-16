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
		snakeFrame.setBounds(100, 200, this.w, this.h);
		snakeFrame.setVisible(true);
		panel.setLayout(layout);
		snakeFrame.add(panel);
		snakeFrame.pack();
		snakeFrame.setSize(this.w, this.h);
	}

}
