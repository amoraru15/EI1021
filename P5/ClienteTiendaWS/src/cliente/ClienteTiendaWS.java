package cliente;

import java.util.Scanner;

import javax.ws.rs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;



public class ClienteTiendaWS {

	// Sustituye esta clase por tu implementación.
	// Deberías copiar y modificar ligeramente la clase cliente que has implementado por ejemplo
	// en la solución con sockets o RMI sin callbacks


	/**
	 * Muestra el menu de opciones y lee repetidamente de teclado hasta obtener una opcion valida
	 *
	 * @param teclado
	 * @return
	 */
	public static int menu(Scanner teclado) {

		int op = 0;

		System.out.println("1. Salir del programa.");
		System.out.println("2. Listar los autores en el catálogo de la tienda.");
		System.out.println("3. Buscar un autor con un nombre dado y mostrar sus cómics en la tienda.");
		System.out.println("4. Comprar un cómic con un código dado, actualizando sus existencias y mostrar sus datos.");
		System.out.println("5. Revender a la tienda un ejemplar de un cómic de su catálogo con un código dado, actualizando sus existencias y mostrar sus datos.");
		System.out.println("------------------");
		System.out.println("Elige una opción: ");
		op = teclado.nextInt();
		teclado.nextLine();

		return op; // DEVOLVER EL VALOR ADECUADO EN CADA CASO;
	}


	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		try {
			GestorTienda gestor = new GestorTienda();

			Scanner teclado = new Scanner(System.in);
			int select = 0;
			while (select != 1) {
				System.out.println("");
				System.out.println("Pulsa enter para continuar.");
				System.in.read();
				System.out.println("------------------");
				select = menu(teclado);
				switch (select) {
				case 1:
					System.out.println("CERRANDO EL PROGRAMA DE PRUEBA");
					System.exit(0);//Cerramos el programa
					//gestor.fin();
					break;
				case 2:
					String[] listaAutores = gestor.listaAutores();
					for (String autor: listaAutores) System.out.println(autor);
					break;
				case 3:
					System.out.println("Introduce el nombre de un autor: ");
					String autor = teclado.nextLine();
					String[] listaComics = gestor.buscaAutor(autor);
					if (listaComics.length == 0) System.out.println("No se ha encontrado el autor.");
					else for (String comic: listaComics) System.out.println(comic);
					break;
				case 4:
					System.out.println("Introduce el codigo de un cómic para comprar: ");
					int codigo = teclado.nextInt();
					System.out.println(gestor.compraComic(codigo));
					break;
				case 5:
					System.out.println("Introduce el codigo de un cómic para vender: ");
					codigo = teclado.nextInt();
					System.out.println(gestor.vendeComic(codigo));
					break;
				default:
					break;
				}
			}

			teclado.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	} // fin de main

} // fin clase

