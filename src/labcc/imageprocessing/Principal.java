package labcc.imageprocessing;

import labcc.imageprocessing.util.IOImage;
import labcc.imageprocessing.view.ImageFrame;

public class Principal {
	
	public static void main(String[] args) {

		IOImage i = new IOImage("./img/sims.bmp");
		ManipulaImagem manipula = new ManipulaImagem(i);


		
		manipula.tonsDeCinza();
				
		ImageFrame frame = new ImageFrame(i.getImage(), i.getWidth(), i.getHeight());
		frame.show();
	}
	
}

class ManipulaImagem {
	
    private int red[][], green[][], blue[][], weight, height;
    
    public ManipulaImagem(IOImage img) {
		extraiPixels(img);
		this.weight = img.getHeight();
		this.height = img.getWidth();
	}

	public  void extraiPixels(IOImage img) {
		this.red = img.getRed();
		this.green = img.getGreen();
		this.blue = img.getBlue();
	}
	
	public void tonsDeCinza() {
	    int gray;

	    for(int i = 0; i < weight; i++) {
	        for(int j = 0; j < height; j++) {
	            //obtém a média das cores
	            gray = (red[i][j] + green[i][j] + blue[i][j])/3;

	            //define o tom de cinza obtido
	            red[i][j] = gray;
	            green[i][j] = gray;
	            blue[i][j] = gray;
	        }
	    }

	}
	
}