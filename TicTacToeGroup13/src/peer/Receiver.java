package peer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread {
	public Game game;
	public Socket peer;
	private boolean stop = false;
	
	public Receiver(Game game, Socket peer) {
		this.game = game;
		this.peer = peer;
		stop = false;
	}
	
	public void run() {
		while (!stop) {
			DataInputStream peerInput;
			
			try {
				peerInput = new DataInputStream(peer.getInputStream());
				int action;
				action = peerInput.readInt();
				
				if(action > 0 && action < 10) {
					System.out.println("position received : "+ action);
					
					game.setGrid(action);
					game.checkWin();
				} else if (action == 10) {
					game.requestNewGame();
				} else if (action == 11) {
					game.newGame();
				} else if (action == 12) {
					game.status = 'a';
					game.playerQuit();
					stop = true;
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
	}
	
	public void stopThread() {
		stop = true;
	}
}
