package servidor;

import comun.MyStreamSocket;
import gestor.GestorConsultas;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente de la Tienda de Comics.
 * El metodo run contiene la logica para gestionar una sesion con un cliente.
 */

class HiloServidorTienda implements Runnable {
    private MyStreamSocket myDataSocket;
    private GestorConsultas gestor;

    /**
     * Construye el objeto a ejecutar por la hebra para servir a un cliente
     *
     * @param myDataSocket socket stream para comunicarse con el cliente
     * @param unGestor     stream asociado al fichero con los datos de los c�mics
     */
    HiloServidorTienda(MyStreamSocket myDataSocket, GestorConsultas unGestor) {
    	// POR IMPLEMENTAR
    	this.myDataSocket = myDataSocket;
    	this.gestor = unGestor;

    }

    /**
     * Gestiona una sesión con un cliente
     */
    public void run() {
        boolean done = false;
        int operacion = 0;
        // ...
        String [] peticion = null;
                
        try {
            while (!done) {
                // Recibe una peticion del cliente
                // Extrae la operacion y los argumentos
            	peticion = myDataSocket.receiveMessage().split("#");
            	operacion = Integer.parseInt(peticion[0]);
                switch (operacion) {
                    case 0: // fin de conexión con el cliente
                        // ...
                    	myDataSocket.close();//cierra el socket de datos
                    	done = true;
                        break;
                    case 1: { // Lista autores en el catalogo
                        // ...
                    	String[] listaDeAutores = gestor.listaAutores();
                    	int numAutores = listaDeAutores.length;
                    	myDataSocket.sendMessage(String.valueOf(numAutores));//Enviamos el numero de autores del catálogo
                    	if (numAutores > 0)
                    		for (int i=0; i<numAutores; i++)
                    			myDataSocket.sendMessage(listaDeAutores[i]);//Enviamos cada uno de los autores
                        break;
                    }

                    case 2: { // Buscar comics por autor
                        // ...
                    	String autorBuscado = peticion[1];
                    	String [] datosAutor = gestor.buscaAutor(autorBuscado);
                    	int numComics = datosAutor.length;
                    	myDataSocket.sendMessage(autorBuscado);
                    	for(int i = 0; i < numComics; i++)
                    		myDataSocket.sendMessage(datosAutor[i]);
                    	
                        break;
                    }
                    case 3: { // Comprar un comic
                        // ...
                    	int codigoBuscado = Integer.parseInt(peticion[1]);
                    	String datosComic = gestor.bajaEjemplar(codigoBuscado);
                    	myDataSocket.sendMessage(datosComic);
                    		
                        break;
                    }
                    case 4: { // Vende un comic
                        // ...
                    	int codigoBuscado = Integer.parseInt(peticion[1]);
                    	String datosComic = gestor.altaEjemplar(codigoBuscado);
                    	myDataSocket.sendMessage(datosComic);

                        break;
                    }

                } // fin switch
            } // fin while
        } // fin try
        catch (Exception ex) {
            System.out.println("Exception caught in thread: " + ex);
        } // fin catch
    } //fin run
    

} //fin class 
