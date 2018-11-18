package server;

import java.io.EOFException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import common.IntCallbackCliente;
import common.IntServidorTiendaRMI;



/**
 * This class implements the remote interface HelloInterface.
 * @author M. L. Liu
 */

public class ImplServidorTiendaRMI extends UnicastRemoteObject
implements IntServidorTiendaRMI {
	private Vector listaClientes;

	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private boolean vendido = false;

	public ImplServidorTiendaRMI() throws RemoteException {
		super( );
		listaClientes = new Vector();
	}

	@Override
	public String[] listaAutores() throws IOException,RemoteException {
		GestorConsultas gestor = new GestorConsultas();
		return gestor.listaAutores();
	}

	@Override
	public String[] buscaAutor(String autorBuscado) throws EOFException, IOException,RemoteException {
		GestorConsultas gestor = new GestorConsultas();
		return gestor.buscaAutor(autorBuscado);
	}

	@Override
	public String compraComic(int codigoBuscado) throws IOException,RemoteException {
		GestorConsultas gestor = new GestorConsultas();
		
		return gestor.bajaEjemplar(codigoBuscado);
	}

	@Override
	public String vendeComic(int codigoBuscado) throws IOException,RemoteException {
		GestorConsultas gestor = new GestorConsultas();
		vendido = true;
		return gestor.altaEjemplar(codigoBuscado);
	}
	
	public void fin() throws IOException {
		GestorConsultas gestor = new GestorConsultas();
		gestor.cierraGestor();
	}

	@Override
	public void registroCbk(IntCallbackCliente objCbkCliente) throws RemoteException, IOException {
		// TODO Auto-generated method stub
		//almacenamos el objeto callback en el vector
		if(!listaClientes.contains(objCbkCliente)) {
			listaClientes.addElement(objCbkCliente);
			System.out.println("Se ha registrado un nuevo cliente para notificar");
			hacerCallbacks();
		}
	}

	@Override
	public synchronized void borraRegistro(IntCallbackCliente objCbkCliente) throws RemoteException, IOException {
		// TODO Auto-generated method stub
		if(listaClientes.removeElement(objCbkCliente)) {
			System.out.println("Registro eliminado con éxito");
		}else {
			System.out.println("El cliente nunca se ha registrado");
		}
		
	}
	
	private synchronized void hacerCallbacks() throws RemoteException, IOException {
		System.out.println("************* Callbacks a clientes registrados *************  \n");
		for (int i = 0; i < listaClientes.size(); i++) {
			System.out.println("Callback numero: " + i);
			//convertimos el objeto vector al objeto callback
			IntCallbackCliente proxCliente = (IntCallbackCliente) listaClientes.elementAt(i);
			//metodo que notifica a los clientes
			proxCliente.notificame("Numero de clientes " + listaClientes.size());
			int codigoBuscado = 0;
			if(vendido) {
				proxCliente.notificame("Hay un nuevo cómic revendido");
			}
			
			
		}
		System.out.println("************* No hay más callbacks *************  \n");	
	}
	
} // end class
