package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IntCallbackCliente;


public class ImplClienteTiendaRMI extends UnicastRemoteObject
implements IntCallbackCliente {

	protected ImplClienteTiendaRMI() throws RemoteException {
		super();
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String notificame(String msj) throws RemoteException, IOException {
		// TODO Auto-generated method stub
		String mensaje = "Callback recibido: " + msj;
		/*if(msj.equals("5")) {
			System.out.println(mensaje + "Se ha revendido un cómic");
		}else {
			System.out.println("Estoy en el else");
		}*/System.out.println(mensaje);
		return mensaje;
	}

}
