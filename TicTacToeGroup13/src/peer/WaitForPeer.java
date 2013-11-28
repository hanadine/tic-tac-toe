package peer;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;

public class WaitForPeer extends Thread {

	public Game game;
	
	public WaitForPeer(Game game) {
		this.game = game;
	}
		
	public void run() {		
		
		
		game.communicater = new Communicater(game);
		
		while (game.status == 'a') {
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
