package peer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver extends Thread {
	public Game game;
	public Socket peer;
	
	public Receiver(Game game, Socket peer) {
		this.game = game;
		this.peer = peer;
	}
	
	public void run() {
		while (true) {
			DataInputStream peerInput;
			
			try {
				peerInput = new DataInputStream(peer.getInputStream());
				int position;
				position = peerInput.readInt();
				System.out.println("position received : "+ position);
				game.setGrid(position);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			game.checkWin();
			
		}
	}
}
