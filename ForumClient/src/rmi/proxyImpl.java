package rmi;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.User;
import client.UserImpl;


public class proxyImpl  extends UnicastRemoteObject implements Serializable,proxy{
	
	private User user;

	public proxyImpl(UserImpl irc) throws RemoteException {
		
		super();
		this.user = irc;
	}

	@Override
	public void ecouter(String msg) throws RemoteException {
		  
	    	System.out.println("Received message: " + msg);
	        this.user.ecrire(msg);   
	}

	@Override
	public String toStringp() throws RemoteException, UnknownHostException {
		
		 return "User ID : " +user.getId() + " User Hostname : " +InetAddress.getLocalHost().getHostName();
	}
	
	
}









