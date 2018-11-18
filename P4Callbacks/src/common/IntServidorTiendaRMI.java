package common;


import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntServidorTiendaRMI extends Remote {
	public String[] listaAutores() throws RemoteException, IOException;
	public String[] buscaAutor(String autorBuscado) throws RemoteException, IOException;
	public String compraComic(int codigoBuscado) throws RemoteException, IOException;
	public String vendeComic(int codigoBuscado) throws RemoteException, IOException;
	public void fin() throws RemoteException, IOException;
	public void registroCbk(IntCallbackCliente objCbkCliente) throws RemoteException, IOException;
	public void borraRegistro(IntCallbackCliente objCbkCliente) throws RemoteException, IOException;

}
