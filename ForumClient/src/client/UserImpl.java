package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.*;

import rmi.Forum;
import rmi.proxy;
import rmi.proxyImpl;

public class UserImpl  implements User, Serializable  {
	
	public TextArea text; // used to print out chat messages
    public TextField data; // used to enter chat messages
    public Frame frame;

    public Forum frm = null; 	// reference to the forum remote object
    public int id; 		// identifier of the client allocated by the server
    
    
   
   
	public UserImpl() {
		
		// creation of the GUI 
		frame=new Frame();
		frame.setLayout(new FlowLayout());
		    	
		text=new TextArea(10,60);
		text.setEditable(false);
		text.setForeground(Color.white);
		text.setBackground(Color.black);
		text.setFont(new Font("Courier New", Font.PLAIN, 14)); // increase font size
		
		frame.add(text);
		    	
		data=new TextField(60);
		frame.add(data);
		    	
		Button write_button = new Button("Write");
		write_button.addActionListener(new writeListener(this));
		write_button.setFont(new Font("Arial", Font.BOLD, 14));
		write_button.setForeground(Color.white);
		write_button.setBackground(Color.blue);
		
		frame.add(write_button);
		    	
		Button connect_button = new Button("Connect");
		connect_button.addActionListener(new connectListener(this));
		connect_button.setBackground(new Color(59, 89, 182));
		connect_button.setForeground(Color.WHITE);
		connect_button.setFocusable(false);
		frame.add(connect_button);
		    	
		Button who_button = new Button("Who");
		who_button.addActionListener(new whoListener(this));
		who_button.setBackground(new Color(59, 89, 182));
		who_button.setForeground(Color.WHITE);
		who_button.setFocusable(false);
		frame.add(who_button);
		    	
		Button leave_button = new Button("Leave");
		leave_button.addActionListener(new leaveListener(this));
		leave_button.setBackground(new Color(59, 89, 182));
		leave_button.setForeground(Color.WHITE);
		leave_button.setFocusable(false);
		frame.add(leave_button);
		    	
		frame.setSize(470,300);
		text.setBackground(Color.black); 
		frame.setVisible(true);


    	
        

    }
    
    /* allow to print something in the window */
    public void ecrire(String msg) {
    	
    	//System.out.println("Received message: " + msg);
    	 
    	try {
    		
    		this.text.append(msg+"\n");
    		
    	} catch (Exception ex) {
    		
		   ex.printStackTrace();
		   return;
	   }	
    	
    }
    
    
    public static void main(String args[]) {
    	
    	   new UserImpl();
    }

	
    
    
    @Override
	public int getId() {
		
		return this.id;
	}
}


//action invoked when the " connect " button is clicked
class connectListener implements ActionListener {
	
	UserImpl irc;
	
	public connectListener (UserImpl i) {
   	irc = i;
	}
	
	public void actionPerformed (ActionEvent e) {
		
		try {

			Forum server = (Forum)Naming.lookup("rmi://localhost:1099/IRCServer"); 
			
	        irc.frm = server;
	        
	        proxy c = new proxyImpl(irc);
	        
	        if (this.irc.frm != null)
	        {  
	        	  irc.id = irc.frm.entrer(c); 
	        	 // c.ecouter("client connecté a "+InetAddress.getLocalHost().getHostName()+ " avec Id "+irc.id);
	        }
	      
	        
	        } catch (Exception ex) {
	        	
	    		ex.printStackTrace();
	    	}

	}
} 



//action invoked when the " write " button is clicked
class writeListener implements ActionListener {
	
  UserImpl irc;

  public writeListener(UserImpl i) {
      irc = i;
  }

  public void actionPerformed(ActionEvent e) {
      try {
      	
          String message = irc.data.getText();
          
          String msg = "Client"+irc.id+" : "+message;
          
          irc.frm.dire(irc.id, msg);
          
          irc.ecrire(msg);
          irc.data.setText(""); // clear the text field
          
      } catch (RemoteException ex) {
          ex.printStackTrace();
      }
  }
}


//action invoked when the "who" button is clicked
class whoListener implements ActionListener {
	
	UserImpl irc;
	
	public whoListener (UserImpl i) {
   	irc = i;
	}
	public void actionPerformed (ActionEvent e) {
		
   try{
		    
  	 try {
  		 
		irc.ecrire(irc.frm.qui());
		
	} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	 }catch( RemoteException ex){
		ex.printStackTrace();
	 }
		
	}
}


//action invoked when the "leave" button is clicked
class leaveListener implements ActionListener {
	
	UserImpl irc;
	
	public leaveListener (UserImpl i) {
		
   	   irc = i;
	}
	public void actionPerformed (ActionEvent e) {
		
		try{
		    
			irc.frm.quiter(irc.id);

	}catch( RemoteException ex){
		ex.printStackTrace();
	}
	}
}







