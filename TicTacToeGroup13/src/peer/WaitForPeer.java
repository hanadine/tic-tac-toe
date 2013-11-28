package peer;

import java.io.IOException;

public class WaitForPeer extends Thread {

	Game game;
	Communicater communicater;
	
	public WaitForPeer(Game game, Communicater communicater) {
		this.game = game;
		this.communicater = communicater;
	}
		
	public void run() {		
	
		try {
			communicater.connectToServer();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
