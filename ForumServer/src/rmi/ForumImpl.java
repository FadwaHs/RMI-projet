package rmi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ForumImpl extends UnicastRemoteObject implements Forum {
	
	// Liste des objets proxy connectés au forum
	 private List<proxy> Proxies = new ArrayList<>();
	// Nom de l'hôte du serveur
	 String hostname;
    

	protected ForumImpl() throws RemoteException, UnknownHostException {
		
		super();
		this.Proxies = new ArrayList<>();
		 // Récupération du nom de l'hôte du serveur
		hostname = InetAddress.getLocalHost().getHostName();
				
	}

	@Override
	public int entrer(proxy pr) throws RemoteException, UnknownHostException {
		
		// Ajout du proxy à la liste des objets connectés
		Proxies.add(pr);
		// Envoi d'un message de confirmation de connexion au client
	    pr.ecouter("client connecté a "+InetAddress.getLocalHost().getHostName()
	    		+ " avec Id "+ Proxies.indexOf(pr));
	    // Renvoi de l'ID du client pour les futures communications
        int id = Proxies.indexOf(pr);
        return id;
 
	}

	@Override
	public void dire(int id, String msg) throws RemoteException {

        // Diffusion du message à tous les clients sauf l'émetteur
		for (int i = 0; i < Proxies.size(); i++) {
			
	        proxy p = Proxies.get(i);
            // Vérification que le proxy n'est pas l'émetteur
	        if (i != id) {
	        	
	            p.ecouter(msg);
	        }
	    }

	}

	@Override
	public String qui() throws RemoteException, UnknownHostException {
		
		StringBuilder sbuffer = new StringBuilder();
		// Construction de la liste des clients connectés
        for (proxy p : this.Proxies) {
        	
        	sbuffer.append(p.toStringp()).append("\n");
        }
        return sbuffer.toString();
	}

	@Override
	public void quiter(int id) throws RemoteException {
		
		 // Suppression du client de la liste des objets connectés
    	this.Proxies.remove(id);
	}

}








