package cliente;

import comun.MyStreamSocket;
import gestor.GestorConsultas;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Esta clase es un modulo que proporciona la logica de aplicacion
 * para el Cliente de una Tienda de Comics usando sockets de tipo stream
 */

public class AuxiliarClienteTienda {

    private MyStreamSocket mySocket; // Socket de datos para comunicarse con el servidor
    private InetAddress serverHost;  // IP del servidor
    private int serverPort;          // Puerto asociado al servicio en el servidor


    /**
     * Construye un objeto auxiliar asociado a un cliente de la tienda.
     * Crea un socket para comunicarse con el servidor.
     *
     * @param    hostName    nombre de la maquina que ejecuta el servidor
     * @param    portNum        numero de puerto asociado al servicio en el servidor
     */
    AuxiliarClienteTienda(String hostName, String portNum)
            throws SocketException, UnknownHostException, IOException {
    	this.serverPort = Integer.parseInt(portNum);
    	this.serverHost = InetAddress.getByName(hostName);
    	this.mySocket = new MyStreamSocket(serverHost,serverPort);
    	System.out.println("Petición de conexión hecha");
    	
    } // end constructor

    /**
     * Pide al servidor que devuelva un vector con los autores en el catalogo.
     *
     * @return vector de cadenas con los nombres de los autores
     * @throws IOException
     */
    public String[] listaAutores() throws IOException {
    	String msjEnviado = "1";
    	mySocket.sendMessage(msjEnviado);	// Enviamos al socket la operacion a realizar
    	String[] autores = mySocket.receiveMessage().split("#");
        return autores;	// Devolvemos los autores

    } // end buscaAutor

    /**
     * Pide al servidor que busque los comics de un determinado autor.
     *
     * @param    autorComic    nombre del autor buscado
     * @return vector de cadenas con la informacion de los comics encontrados
     * @throws IOException
     */
    public String[] buscaAutor(String autorComic) throws IOException {
    	String msjEnviado = "2#" + autorComic;
    	mySocket.sendMessage(msjEnviado);	// Enviamos al socket la operacion a realizar
    	String[] comicsAutor = mySocket.receiveMessage().split("#");
        return comicsAutor; // Devolvemos la información de los comics

    } // end buscaAutor

    /**
     * Pide al servidor que compre un comic con un codigo dado y devuelva sus datos
     *
     * @param    codigoComic    codigo del comic a comprar
     * @return cadena con informacion sobre el comic comprado.
     * Vacia si no se encuentra.
     * @throws IOException
     */
    public String compraComic(int codigoComic) throws IOException {
    	String msjEnviado = "3#" + codigoComic;
    	mySocket.sendMessage(msjEnviado);	// Enviamos al socket la operacion a realizar
    	String msjRecibido = mySocket.receiveMessage();
        return msjRecibido;	// Devolvemos la información del comic
    } // end compraComic

    /**
     * Pide al servidor que venda un comic con un codigo dado y devuelva sus datos
     *
     * @param    codigoComic    codigo del comic a vender
     * @return cadena con informacion sobre el comic vendido.
     * Vacia si no se encuentra.
     * @throws IOException
     */
    public String vendeComic(int codigoComic) throws IOException {
    	String msjEnviado = "4#" + codigoComic;	// Enviamos al socket la operacion a realizar
    	mySocket.sendMessage(msjEnviado);
    	String msjRecibido = mySocket.receiveMessage();
        return msjRecibido; // Devolvemos la información del comic
    } // end vendeComic

    /**
     * Finaliza la conexion con el servidor
     *
     * @throws IOException
     */
    public void fin() throws IOException {
    	mySocket.sendMessage("0");	// Enviamos al socket la operacion a realizar
    	mySocket.close();
    } // end done
} //end class
