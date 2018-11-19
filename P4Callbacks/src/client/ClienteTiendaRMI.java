package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Scanner;

import common.IntCallbackCliente;
import common.IntServidorTiendaRMI;

/**
 * This class represents the object client for a
 * distributed object of class Hello, which implements
 * the remote interface HelloInterface.  A security
 * manager is installed to safeguard stub downloading.
 *
 * @author M. L. Liu
 */

public class ClienteTiendaRMI {

	public static int menu(Scanner teclado) {

		int op = 0;

		System.out.println("1. Salir del programa.");
		System.out.println("2. Listar los autores en el catálogo de la tienda.");
		System.out.println("3. Buscar un autor con un nombre dado y mostrar sus cómics en la tienda.");
		System.out.println("4. Comprar un cómic con un código dado, actualizando sus existencias y mostrar sus datos.");
		System.out.println("5. Revender a la tienda un ejemplar de un cómic de su catálogo con un código dado, actualizando sus existencias y mostrar sus datos.");
		System.out.println("6. Recibir notificaciones ");
		System.out.println("7. Dejar de recibir notificaciones ");
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
            int RMIPort;
            String hostName;
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);
            System.out.println("Enter the RMIRegistry host name:");
            hostName = br.readLine();
            System.out.println("Enter the RMIregistry port number:");
            String portNum = br.readLine();
            RMIPort = Integer.parseInt(portNum);

            // start a security manager - this is needed if stub
            // downloading is in use for this application.
            // Te following sentence avoids the need to use
            // the option -DJava.security.policy=..." when launching the clinet
            System.setProperty("java.security.policy", "src/client/java.policy");
            System.setSecurityManager(new SecurityManager());
           
            String registryURL = "rmi://localhost:" + portNum + "/tienda";
			// find the remote object and cast it to an interface object
			IntServidorTiendaRMI interfazServidor = (IntServidorTiendaRMI) Naming.lookup(registryURL);
			IntCallbackCliente interfazCliente = new ImplClienteTiendaRMI(); 

			System.out.println("Lookup completed ");


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
					interfazServidor.borraRegistro(interfazCliente);
					System.out.println("CERRANDO EL PROGRAMA DE PRUEBA");
					interfazServidor.fin();
					break;
				case 2:
					String[] listaAutores = interfazServidor.listaAutores();
					for (String autor: listaAutores) System.out.println(autor);
					break;
				case 3:
					System.out.println("Introduce el nombre de un autor: ");
					String autor = teclado.nextLine();
					String[] listaComics = interfazServidor.buscaAutor(autor);
					if (listaComics.length == 0) System.out.println("No se ha encontrado el autor.");
					else for (String comic: listaComics) System.out.println(comic);
					break;
				case 4:
					System.out.println("Introduce el codigo de un cómic para comprar: ");
					int codigo = teclado.nextInt();
					System.out.println(interfazServidor.compraComic(codigo));
					break;
				case 5:
					System.out.println("Introduce el codigo de un cómic para vender: ");
					codigo = teclado.nextInt();
					System.out.println(interfazServidor.vendeComic(codigo));
					break;
				case 6:
					interfazServidor.registroCbk(interfazCliente);
					break;
				case 7:
					interfazServidor.borraRegistro(interfazCliente);
					break;
				default:
					break;
				}
				
			}

			teclado.close();
		}catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Excepcion en ClienteTiendaRMI: " + ex);
		}
	} //end main
}//end class
