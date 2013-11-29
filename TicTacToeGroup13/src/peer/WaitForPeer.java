/*
Title: P2P Tic-Tac-Toe Game
Class: WaitForPeer
Created: November 20th 2013
Last Edited: November 28th 2013
Authors: ECSE 414, McGill University, Fall 2013 - Introduction to Telecom - Group 10
*/

package peer;

import java.io.IOException;

public class WaitForPeer extends Thread {

	Game game;
	Communicater communicater;


	public WaitForPeer(Game game, Communicater communicater) { //WaitForPeer constructor
		this.game = game;
		this.communicater = communicater;
	}
		
	public void run() {		
	
		try {
			communicater.connectToServer();// Here the peer  tries to connect to the server
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (game.status == 'a') { // Status A indicates  the peer is still connected to the server, and waiting to connect to another peer
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		game.btn1v1.setEnabled(true);		
		game.newGame();
		
	}
}
