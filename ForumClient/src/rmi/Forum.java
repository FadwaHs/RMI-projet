package rmi;

import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Forum extends Remote {
	
	public int entrer(proxy pr) throws RemoteException, UnknownHostException;
	public void dire(int id, String msg) throws RemoteException;
	public String qui() throws RemoteException, UnknownHostException;
	public void quiter(int id) throws RemoteException;

}
