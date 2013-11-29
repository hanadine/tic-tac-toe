package peer;
/*
Title: P2P Tic-Tac-Toe Game
Class: Receiver
Created: November 20th 2013
Last Edited: November 28th 2013
Authors: ECSE 414, McGill University, Fall 2013 - Introduction to Telecom - Group 10
*/

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread { 
// Receiver must act as a thread to be able to continually check for incoming data 
	public Game game;
	public Socket peer;
	private boolean stop = false;
	
	public Receiver(Game game, Socket peer) {//receiver constructor
		this.game = game;
		this.peer = peer;
		stop = false;
	}
	
	public void run() {
		while (!stop) {
			DataInputStream peerInput;
			
			try {
				peerInput = new DataInputStream(peer.getInputStream()); // collects data 
				int action;
				action = peerInput.readInt();
				
				if(action > 0 && action < 10) {//positions 1-9 represent a position on the tic tac toe board
					System.out.println("position received : "+ action);
					
					game.setGrid(action);
					game.checkWin();// Must check for a win everytime a position is received
				} else if (action == 10) {// 10 is a request for a new game
					game.requestNewGame();
				} else if (action == 11) {// 11 creates a new game
					game.newGame();
				} else if (action == 12) {//12 indicates the player has quit the game
					game.status = 'a';
					game.playerQuit();
					stop = true;
				}
				
				
			} catch (IOException e) { // this represents the player closing the window with using the quit button
				game.playerQuit();
				stop = true;
			}		
			
		}
	}
	
	public void stopThread() {
		stop = true;
	}
}
