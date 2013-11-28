package peer;

<<<<<<< HEAD
import java.awt.FlowLayout;
import java.awt.GridLayout;
=======
>>>>>>> 29965571642365d5b8690b272c97e3bbc988f441
import java.io.IOException;

public class WaitForPeer extends Thread {

<<<<<<< HEAD
	public Game game;
	
	public WaitForPeer(Game game) {
		this.game = game;
	}
		
	public void run() {		
		
		game.setCommunicater(new Communicater(game));
=======
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
>>>>>>> 29965571642365d5b8690b272c97e3bbc988f441
		
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
