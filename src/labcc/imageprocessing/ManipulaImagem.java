package labcc.imageprocessing;

import java.util.InputMismatchException;
import java.util.Scanner;

import labcc.imageprocessing.util.IOImage;

class ManipulaImagem {
	Scanner scanner = new Scanner(System.in);
	
	private int red[][], green[][], blue[][], width, height;
	private IOImage img;

	public ManipulaImagem(IOImage img){
		extraiPixels(img);
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.img = img;
	}

	public void pretoBranco() {
		int limiar = 122;
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				if(red[i][j] >= limiar){	//pixel se torna branco
					red[i][j] = 255;
					green[i][j] = 255;
					blue[i][j] = 255;
				}else{						//pixel se torna preto
					red[i][j] = 0;
					green[i][j] = 0;
					blue[i][j] = 0;
				}
			}
		}
		
	}

	public void clarear(int taxa) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				// adiciona a qtde de brilho desejada
				int cor = red[i][j] += taxa;
				red[i][j] = cor > 255 ? 255 : cor;
				
				cor = blue[i][j] += taxa;
				blue[i][j] = cor > 255 ? 255 : cor;
				
				cor = green[i][j] += taxa;
				green[i][j] = cor > 255 ? 255 : cor;
				
			}
		}

	}

	public void escurecer(int taxa) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				// adiciona a qtde de brilho desejada
				int cor = red[i][j] += taxa;
				red[i][j] = cor < 0 ? 0 : cor;
				
				cor = blue[i][j] += taxa;
				blue[i][j] = cor < 0 ? 0 : cor;
				
				cor = green[i][j] += taxa;
				green[i][j] = cor < 0 ? 0 : cor;
				
			}
		}

	}

	public void girarImagem() {
		int op;
		int redAux[][] = new int[width][height];
		int greenAux[][] = new int[width][height];
		int blueAux[][] = new int[width][height];

		System.out.println("1- Girar p/ Esquerda\t 2- Girar p/ Direita");
		try {
			op = scanner.nextInt();
			if (op == 1) { // Girar p/ esquerda

				// Gira em uma matriz auxiliar
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						redAux[j][i] = red[i][width - j - 1];
						greenAux[j][i] = green[i][width - j - 1];
						blueAux[j][i] = blue[i][width - j - 1];
					}
				}
			} else if (op == 2) { // Girar p/ direita

				// Gira em uma matriz auxiliar
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						redAux[j][i] = red[height - i - 1][j];
						greenAux[j][i] = green[height - i - 1][j];
						blueAux[j][i] = blue[height - i - 1][j];
					}
				}
			}else{
				System.out.println("Erro: Opcao invalida.");
				return;
			}
			
			img.setMatrix(redAux, greenAux, blueAux);
			
		} catch (InputMismatchException e) {
			System.out.println("Erro: Valor digitado eh invalido!");
		}
		
	}

	public void extraiPixels(IOImage img) {
		this.red = img.getRed();
		this.green = img.getGreen();
		this.blue = img.getBlue();
	}

	public void tonsDeCinza() {
		int gray;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// obtem a media das cores
				gray = (red[i][j] + green[i][j] + blue[i][j]) / 3;

				// define o tom de cinza obtido
				red[i][j] = gray;
				green[i][j] = gray;
				blue[i][j] = gray;
			}
		}

	}

	public void negativo() {

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Inverte os valores de cada uma das 3 cores basicas
				red[i][j] = 255 - red[i][j];
				green[i][j] = 255 - green[i][j];
				blue[i][j] = 255 - blue[i][j];
			}
		}
	}

	public void filtrarCor(int cor) {
		switch (cor) {
		// faz verificao da cor
		case 1: {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					red[i][j] = 0; // 1 = red
				}
			}
			break;
		}
		case 2: {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					green[i][j] = 0; // 2 = green
				}
			}
			break;
		}
		case 3: {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					blue[i][j] = 0; // 3 = blue
				}
			}
			break;
		}
		// exibe mensagem de erro, caso valor seja invalido
		default: {
			System.out.println("O valor da cor deve estar entre 1 e 3");
		}
		}
	}
}
