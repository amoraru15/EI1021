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
			
		}while(op < 1 ||  op > 5);
		teclado.nextLine();
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
		Scanner teclado = new Scanner(System.in);
		int select = menu(teclado);
		switch (select) {
		case 1:
			System.out.println("CERRANDO EL PROGRAMA DE PRUEBA");
			gestor.cierraGestor();
			break;
		case 2:
			gestor.listaAutores();
			break;
		case 3:
			System.out.println("Introduce el nombre de un autor: ");
			String autor = teclado.next();
			gestor.buscaAutor(autor);
			break;
		case 4:
			System.out.println("Introduce el nombre de un cómic para comprar: ");
			int codigo = teclado.nextInt();
			gestor.bajaEjemplar(codigo);
			break;
		case 5:
			System.out.println("Introduce el nombre de un autor: ");
			codigo = teclado.nextInt();
			gestor.altaEjemplar(codigo);
		default:
			break;
		}
		teclado.close();
	} // fin de main

} // fin class PruebaFicheroComics
