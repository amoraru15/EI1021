package server;

import java.io.EOFException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import common.IntCallbackCliente;
import common.IntServidorTiendaRMI;



/**
 * This class implements the remote interface HelloInterface.
 * @author M. L. Liu
 */

public class ImplServidorTiendaRMI extends UnicastRemoteObject
implements IntServidorTiendaRMI {
	private ArrayList<IntCallbackCliente> listaClientes;

	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private boolean vendido = false;

	public ImplServidorTiendaRMI() throws RemoteException {
		super( );
		listaClientes = new ArrayList<IntCallbackCliente>();
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
		hacerCallbacks();
		return gestor.altaEjemplar(codigoBuscado);
	}
	
	public void fin() throws IOException {
		GestorConsultas gestor = new GestorConsultas();
		gestor.cierraGestor();
	}

	@Override
	public void registroCbk(IntCallbackCliente objCbkCliente) throws RemoteException, IOException {
		//almacenamos el objeto callback en el vector
		if(!listaClientes.contains(objCbkCliente)) {
			listaClientes.add(objCbkCliente);
			System.out.println("Se ha registrado un nuevo cliente para notificar");
			objCbkCliente.notificame("Se te ha registrado");
		}
	}

	@Override
	public synchronized void borraRegistro(IntCallbackCliente objCbkCliente) throws RemoteException, IOException {
		if(listaClientes.remove(objCbkCliente)) {
			System.out.println("Se ha eliminado un cliente con exito");
			objCbkCliente.notificame("Se te ha eliminado");
		} else {
			objCbkCliente.notificame("Nunca te has registrado");
		}
	}
	
	private synchronized void hacerCallbacks() {
		System.out.println("\n******* Callbacks a clientes registrados ***********");
		System.out.println("Clientes registrados: " + listaClientes.size());
		Iterator<IntCallbackCliente> iter = listaClientes.iterator();
		for (int i = 1; iter.hasNext(); i++) {
			System.out.println("Mandando mensaje a cliente " + i);
			IntCallbackCliente proxClient = iter.next();
			try {
				proxClient.notificame("Hay un nuevo cómic revendido");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("\nError, el cliente no es valido");
				System.out.println("Eliminando cliente");
				iter.remove();
			}
		}
		System.out.println("************* No hay más callbacks *************  \n");	
	}
	
} // end class
