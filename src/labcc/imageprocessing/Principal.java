package labcc.imageprocessing;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import labcc.imageprocessing.util.IOImage;
import labcc.imageprocessing.view.ImageFrame;

public class Principal {
	static Scanner scanner = new Scanner(System.in);
	static ManipulaImagem manipula = null;
	static IOImage i;
	final static JFileChooser fc = new JFileChooser();
	static File file;

	public static void main(String[] args) {
		int op = 0;
		
		
		mostraCabecalho();
		
		System.out.println("\nSelecionando a imagem...\n");
		
		try {
			// Localizar imagem			
			file = localizarImagem(fc);

			do {
				// Carrega imagem
				i = new IOImage(file);
				manipula = new ManipulaImagem(i);
				
				exibeMenu();
				// Selecionar opção do Menu
				op = scanner.nextInt();

				if (op != 0) {
					aplicarFiltro(op, manipula);
					if(op!=7){	//Não exibir imagem quando é selecionado uma nova
						exibeImagem(i);
					}
				}
			} while (op != 0);
		} catch (IOException e) {
			System.out.println("Erro: Imagem não localizada.");
		}catch (IllegalArgumentException e) {
			System.out.println("Erro: Imagem não localizada.");
		}
		System.out.println("\nEncerrando...\n");
		scanner.close();
		System.exit(0);
	}

	private static void mostraCabecalho() {
		System.out.printf("*************************************\n");
		System.out.printf("*\tTratamento de Imagens\t    *\n");
		System.out.printf("*************************************\n\n");
	}

	private static void exibeMenu() {
		// Exibe menu de opcoes
		System.out.println("\nEscolha o Filtro: \n");
		System.out.print("1- Negativo\n" 
				+ "2- Filtrar Cor\n" 
				+ "3- Girar Imagem\n" 
				+ "4- Tons de Cinza\n"
				+ "5- Clarear/Escurecer\n" 
				+ "6- Preto e Branco\n" 
				+ "7- Ler Outra Imagem\n"
				+ "0- Sair\n");
	}

	private static void aplicarFiltro(int op, ManipulaImagem manipula) {
		int cor;

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

			if (taxa > 0)
				manipula.clarear(taxa);
			else
				manipula.escurecer(taxa);

			break;
		}
		case 6: {
			manipula.tonsDeCinza(); // transforma para tons de cinza;
			manipula.pretoBranco();
			break;
		}
		case 7:{
			System.out.println("Localizando a imagem...\n");
			file = localizarImagem(fc);
			break;			
		}
		case 0: {
			System.out.println("\nEncerrando...");
			break;
		}
		default:
			System.out.println("Erro: Opção inválida!\n");
			break;
		}

	}

	private static void exibeImagem(IOImage i) {
		ImageFrame frame = new ImageFrame(i.getImage(), i.getWidth(), i.getHeight());
		frame.show();
	}
	
	private static File localizarImagem(final JFileChooser fc) {
		File file;
		fc.showOpenDialog(fc);
		file = fc.getSelectedFile();
		return file;
	}

}
