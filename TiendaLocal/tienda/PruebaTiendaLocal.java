package tienda;

import java.io.IOException;
import java.util.Scanner;


public class PruebaTiendaLocal {

	/**
	 * Muestra el menu de opciones y lee repetidamente de teclado hasta obtener una opcion valida
	 *
	 * @param teclado
	 * @return
	 */
	public static int menu(Scanner teclado) {

		// POR IMPLEMENTAR

		int op = 0;
		do {
		
			System.out.println("1. Salir del programa.");
			System.out.println("2. Listar los autores en el catálogo de la tienda.");
			System.out.println("3. Buscar un autor con un nombre dado y mostrar sus cómics en la tienda.");
			System.out.println("4. Comprar un cómic con un código dado, actualizando sus existencias y mostrar sus datos.");
			System.out.println("5. Revender a la tienda un ejemplar de un cómic de su catálogo con un código dado, actualizando sus existencias y mostrar sus datos .");
			System.out.println("Elige una opción: ");
			op = teclado.nextInt();
		}while(op != 1);
		return op; // DEVOLVER EL VALOR ADECUADO EN CADA CASO;
	}


	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {

		// POR IMPLEMENTAR
		GestorConsultas gestor = new GestorConsultas();
		gestor.creaFichero("");
		int select = menu(new Scanner(System.in));
		switch (select) {
		case 1:
			System.out.println("CERRANDO EL PROGRAMA");
			gestor.cierraGestor();
			break;
		case 2:
				//REVISAR EL MÉTODO
			gestor.listaAutores();
			break;
		case 3:
				//REVISAR EL MÉTODO
			System.out.println("Introduce el nombre de un autor: ");
			Scanner autor = new Scanner(System.in);
			gestor.buscaAutor(autor.next());
			break;
		case 4:
				//REVISAR EL MÉTODO
			System.out.println("Introduce el nombre de un cómic para comprar: ");
			Scanner codigo = new Scanner(System.in);
			gestor.bajaEjemplar(codigo.nextInt());
			break;
		case 5:
				//REVISAR EL MÉTODO
			System.out.println("Introduce el nombre de un autor: ");
			codigo = new Scanner(System.in);
			gestor.altaEjemplar(codigo.nextInt());
		default:
			break;
		}

	} // fin de main

} // fin class PruebaFicheroComics
