package servidor;

import comun.MyStreamSocket;
import gestor.GestorConsultas;

import java.io.IOException;
import java.net.ServerSocket;


/**
 * Este modulo contiene la logica de aplicacion del servidor de la Tienda de Comics.
 * Utiliza sockets en modo stream para llevar a cabo la comunicacion entre procesos.
 * Puede servir a varios clientes de modo concurrente lanzando una hebra para atender a cada uno de ellos.
 * Se le puede indicar el puerto del servidor en linea de ordenes.
 */


public class ServidorTiendaSockets {

    static private GestorConsultas gestor;

	public static void main(String[] args) {

        // Acepta conexiones vía socket de distintos clientes.
        // Por cada conexión establecida lanza una hebra de la clase HiloServidorTienda.
    	int serverPort = 13;
    	gestor = new GestorConsultas();
    	if(args.length == 1) {
    		serverPort = Integer.parseInt(args[0]);
    	}
    	try {
    		ServerSocket miSocketConexion = new ServerSocket(serverPort);
    		System.out.println("¡Servidor listo!");
    		while(true) {
    			MyStreamSocket myDataSocket = new MyStreamSocket(miSocketConexion.accept());
    			System.out.println("Conexion aceptada");
    			Thread hilo = new Thread( new HiloServidorTienda(myDataSocket, gestor));
    			hilo.start();
    		}
    		
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}

        // Revisad el apartado 5.5 del libro de Liu

    } //fin main
} // fin class
