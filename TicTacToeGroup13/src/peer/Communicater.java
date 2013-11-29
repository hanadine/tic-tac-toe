/*
Title: P2P Tic-Tac-Toe Game
Class: Communicater
Created: November 20th 2013
Last Edited: November 28th 2013
Authors: ECSE 414, McGill University, Fall 2013 - Introduction to Telecom - Group 10
*/



package peer;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class Communicater{
	
	int port = 50040, serverPort;
	String serverAddress;
	ServerSocket listener;
	Socket peer;
	Game game;
	Receiver receiver;
	
	public Communicater(Game game) { //Constructor for Communicater
		
		this.game = game;

		WaitForPeer waitForPeer = new WaitForPeer(game, this);
		waitForPeer.start();
		
	}
	
	public void connectToServer() throws IOException{ // This method serves to connect the peer to the server
	
		try {
			listener = new ServerSocket(port);
			//String input =  JOptionPane.showInputDialog(null, "Enter server IP address:" 
			//	       ,"");
			//System.out.println(input);
			Socket client = new Socket("142.157.166.82", 50060); // creates a client socket
			DataOutputStream output = new DataOutputStream(client.getOutputStream());
			System.out.println("port : "+ port);
			output.writeInt(port);
			
			client.close();
			
			System.out.println("closed client successfully");
			startGame();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "server not available", "ERROR", JOptionPane.ERROR_MESSAGE);
			game.btn1v1.setEnabled(true);
			e.printStackTrace();
		}	
		
	}

	public void startGame() throws IOException{
		
		System.out.println("test 1");
		Socket connector = listener.accept();
		System.out.println("test 2");
		BufferedReader in = new BufferedReader(new InputStreamReader(connector.getInputStream())); //Connection with server is completely established
		String type = in.readLine();
		System.out.println(type);
		if (type.equals("SERVER")){ // The peer has been sent "SERVER" from the server, and will thus now act as the host towards the other peer
			
			peer = listener.accept();
			game.setStatus('S'); //SERVER Status

			listener.close(); // closes the connection with the server
		
		} else { // The Peer is has been sent CLIENT from the server and will thus now act as a Client towards the other peer  
			
			serverPort = Integer.parseInt(in.readLine()); // the address of the host peer has been sent to the client.
						
			serverAddress = in.readLine();
			System.out.println("serverAddress: "+ serverAddress); 
			
			listener.close();
			
			peer = new Socket(serverAddress, serverPort); //The client tries to connect with the host
			game.setStatus('C');

		}
		
		receiver = new Receiver(game, peer);
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
		
	}	
	
	public void sendPosition(int position) throws IOException{ // Sends a position to the other peer
		
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream());
		System.out.println("position sent : "+ position);
		outputPeer.writeInt(position);
	}
	
	public void sendNewGame() throws IOException{		
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream()); //sends a new game request to the other peer
		System.out.println("New Game Request sent");
		outputPeer.writeInt(10);		
	}
	
	public void sendConfirm() throws IOException{		
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream()); //sends a confirmation for the new game request
		System.out.println("New Game Confirm sent");
		outputPeer.writeInt(11);		
	}
	
	public void sendExit() throws IOException{ // sends an exit request to the other peer
		DataOutputStream outputPeer = new DataOutputStream(peer.getOutputStream());
		System.out.println("Exit sent");
		outputPeer.writeInt(12);
	}
	
	public void close() throws IOException{
		listener.close();
		peer.close();
	}
	
	public void stopReceiver(){ // closes the receiver thread
		receiver.stopThread();
	}

}
