package labcc.imageprocessing;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import labcc.imageprocessing.util.IOImage;
import labcc.imageprocessing.view.ImageFrame;

public class Principal {
	static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		int op, cor;
		
		mostraCabecalho();
		
		// Faz Leitura do nome da imagem + sua extensão
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
				// TODO Verificar porque não está girando
				manipula.girarImagem();
				break;
			}
			case 4: {
				manipula.tonsDeCinza();
				break;
			}
			case 5: {
				// TODO Melhorar algoritmo
				int taxa;
				System.out.println("Valor de Brilho (-255 a 255): ");
				taxa = scanner.nextInt();
				manipula.brilho(taxa);
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
		// Exibe menu de opções
		System.out.println("\nEscolha o Filtro: \n");
		System.out.print("1- Negativo\n" + "2- Filtrar Cor\n" + "3- Girar Imagem\n" + "4- Tons de Cinza\n"
				+ "5- Clarear/Escurecer\n" + "0- Sair\n");
	}

}

class ManipulaImagem {

	private int red[][], green[][], blue[][], width, height;

	public ManipulaImagem(IOImage img) {
		extraiPixels(img);
		this.width = img.getWidth();
		this.height = img.getHeight();
	}

	public void brilho(int taxa) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				// adiciona a qtde de brilho desejada
				red[i][j] += taxa;
				green[i][j] += taxa;
				blue[i][j] += taxa;
			}
		}

	}

	public void girarImagem() {
		Scanner scanner = new Scanner(System.in);
		int op;
		int redAux[][] = new int[height][width];
		int greenAux[][] = new int[height][width];
		int blueAux[][] = new int[height][width];

		System.out.println("1- Girar p/ Direita\t 2- Girar p/ Esquerda");
		try {
			op = scanner.nextInt();

			if (op == 1) { // Girar p/ direita

				// Gira em uma matriz auxiliar
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						redAux[i][j] = red[width - j - 1][i];
						greenAux[i][j] = green[width - j - 1][i];
						blueAux[i][j] = blue[width - j - 1][i];
					}
				}
			} else if (op == 2) { // Girar p/ Esquerda

				// Gira em uma matriz auxiliar
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						redAux[i][j] = red[j][height - i - 1];
						greenAux[i][j] = green[j][height - i - 1];
						blueAux[i][j] = blue[j][height - i - 1];
					}
				}
			}else{
				System.out.println("Erro: Opção inválida.");
				return;
			}
			
			// Copia os valores da matriz auxiliar p/ matriz base
			for(int i=0; i < height; i++){
				for(int j=0; j < width; j++){
					red[i][j] = redAux[i][j];
					green[i][j] = greenAux[i][j];
					blue[i][j] = blueAux[i][j];
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("Erro: Valor digitado é inválido!");
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
				// obtém a média das cores
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
				// Inverte os valores de cada uma das 3 cores básicas
				red[i][j] = 255 - red[i][j];
				green[i][j] = 255 - green[i][j];
				blue[i][j] = 255 - blue[i][j];
			}
		}
	}

	public void filtrarCor(int cor) {
		switch (cor) {
		// faz verificação da cor
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
		// exibe mensagem de erro, caso valor seja inválido
		default: {
			System.out.println("O valor da cor deve estar entre 1 e 3");
		}
		}
	}
}