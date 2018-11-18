package common;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IntCallbackCliente extends Remote{
	public String notificame(String msj)throws RemoteException, IOException;
}
