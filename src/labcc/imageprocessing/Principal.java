package labcc.imageprocessing;

import java.util.InputMismatchException;
import java.util.Scanner;

import labcc.imageprocessing.util.IOImage;
import labcc.imageprocessing.view.ImageFrame;

public class Principal {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		int op, cor;
		
		mostraCabecalho();
		
		// Faz Leitura do nome da imagem + sua extensao
		System.out.println("Nome da Imagem: ");
		String nomeArquivo = scanner.nextLine();
		
		do {
			// Carrega imagem
			IOImage i = new IOImage("./img/" + nomeArquivo);
			ManipulaImagem manipula = new ManipulaImagem(i);

			exibeMenu();
			op = scanner.nextInt();

			switch (op) {
			case 1:
				manipula.negativo();
				break;
			case 2: {
				System.out.println("Cor a ser filtrada:\n1- Vermelho\t2- Verde\t3- Azul\n");
				cor = scanner.nextInt();
				manipula.filtrarCor(cor);
				break;
			}
			case 3: {
				manipula.girarImagem();
				break;
			}
			case 4: {
				manipula.tonsDeCinza();
				break;
			}
			case 5: {
				System.out.println("Valor de Brilho (-255 a 255): ");
				int taxa = scanner.nextInt();

				if(taxa > 0)  manipula.clarear(taxa);
					else 	  manipula.escurecer(taxa);

				break;
			}
			default:
				break;
			}
			if (op != 0) {
				ImageFrame frame = new ImageFrame(i.getImage(), i.getWidth(), i.getHeight());
				frame.show();
			}
		} while (op != 0);
		scanner.close();

		System.exit(1);
	}

	private static void mostraCabecalho() {
		System.out.printf("*************************************\n");
		System.out.printf("*\tTratamento de Imagens\t    *\n");
		System.out.printf("*************************************\n\n");
	}

	private static void exibeMenu() {
		// Exibe menu de opcoes
		System.out.println("\nEscolha o Filtro: \n");
		System.out.print("1- Negativo\n" + "2- Filtrar Cor\n" + "3- Girar Imagem\n" + "4- Tons de Cinza\n"
				+ "5- Clarear/Escurecer\n" + "0- Sair\n");
	}

}

class ManipulaImagem {

	private int red[][], green[][], blue[][], width, height;
	private IOImage img;

	public ManipulaImagem(IOImage img) {
		extraiPixels(img);
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.img = img;
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
		Scanner scanner = new Scanner(System.in);
		int op;
		int redAux[][] = new int[width][height];
		int greenAux[][] = new int[width][height];
		int blueAux[][] = new int[width][height];

		System.out.println("1- Girar p/ Direita\t 2- Girar p/ Esquerda");
		try {
			op = scanner.nextInt();

			if (op == 1) { // Girar p/ direita

				// Gira em uma matriz auxiliar
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						redAux[j][i] = red[i][width - j - 1];
						greenAux[j][i] = green[i][width - j - 1];
						blueAux[j][i] = blue[i][width - j - 1];
					}
				}
			} else if (op == 2) { // Girar p/ Esquerda

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