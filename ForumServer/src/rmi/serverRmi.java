package rmi;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class serverRmi {

	public static void main(String[] args) throws RemoteException, MalformedURLException, UnknownHostException {
		
		
        LocateRegistry.createRegistry(1099);
		
        ForumImpl forum = new ForumImpl();
		
		Naming.rebind("rmi://localhost:1099/IRCServer", forum); //bdli hada mnb3d localhost
		
	}
}
