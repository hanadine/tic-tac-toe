import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

//import XOmain.receive;

//import XOmain.FindPlayerThread;
//import XOmain.receive;

public class Peer extends JFrame {

	private static final long serialVersionUID = 1L;

	//ServerSocket, Socket, Input and Output Streams
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream objInputStream = null;
	private ObjectOutputStream objOutputStream = null;

	private Dimension screenSize;									// screen size
	private int width;												// width of screen
	private int height;												// height of screen

	private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9;				// buttons XO fields
	/*
	 Button position:
	 [b1][b2][b3]
	 [b4][b5][b6]
	 [b7][b8][b9]
	 */

	private JTextArea textArea;										// text area on right side of frame for chat and notifications
	private JScrollPane sp;											// scroll pane for text area

	private JTextField ip, port, nick, message; 					// IP address, port number, nickname, chat message
	private JButton join, create, newGame; 						// buttons : JOIN, CREATE, NEW GAME

	private String xoField[] = { "","","", "","","", "","","" }; 		// FIELDS XO (see example in multiline comment)
	/*
	Explanation:
	for ex. if xoField = { "X", "X", "X", ...}; then X win
	[X][X][X]
	[ ][ ][ ]
	[ ][ ][ ]
	2nd ex. if xoField = { "O","","", "","O","", "","","O" };
	[O][ ][ ]
	[ ][O][ ]
	[ ][ ][O]
	 */

	private String xo = ""; 										// server is X , client is O
	private String nick1, nick2, chatmsg; 							// nick1 server, nick2 client, chat message

	private boolean signal; 										// signal for "WHOSE TURN"
	private boolean msgSignal = true;								// if this signal is false, then stop sending messages over Internet

	private int turn = 0;										//The number of parties // 0=X play first; 1=O play 2nd; 3=X turn....
	private int moves = 0;											//The number of moves, if number is higher or equal to 9 , game is draw

	private Font fontText, fontButtons; 
	private String safeSing = "!pass123!#$%&/()!";	




	public Peer() {
		//Initialize the Interface
		UserInterface();

	}

	private void UserInterface() {

		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) { } // get&set system UI

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();	 // get screen size
		width = (int) screenSize.getWidth(); 						 // get width
		height = (int) screenSize.getHeight(); 						 // get height

		setSize(width/3, height/3); 								 // set the size of a frame to 1/3 of a window screen
		setResizable(true);
		setTitle("Tic-Tac-Toe");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		fontText = new Font("Book Antiqua", Font.PLAIN, 30);
		fontButtons = new Font("Book Antiqua", Font.PLAIN, 14);

		// --- CENTAR PANEL ----
		// panel for xo buttons
		JPanel pCenter = new JPanel();
		pCenter.setLayout(new GridLayout(3, 3));

		b1 = new JButton("   ");
		b1.setFont(fontText);
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b1.setText(xo); // set X or O button text
					send(xo + "1" + safeSing);
					send("true" + safeSing);
					signal = false;
					b1.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[0] = "X";
					}
					else
					{
						xoField[0] = "O";
					}
					++moves;
					checkTable();
				}
			}
		});
		pCenter.add(b1);

		b2 = new JButton("   ");
		b2.setFont(fontText);
		b2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b2.setText(xo);
					send(xo + "2" + safeSing);
					send("true" + safeSing);
					signal = false;
					b2.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[1] = "X";
					}
					else
					{
						xoField[1] = "O";
					}
					++moves;
					checkTable();
				}
			}
		});
		pCenter.add(b2);

		b3 = new JButton("   ");
		b3.setFont(fontText);
		b3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b3.setText(xo);
					send(xo + "3" + safeSing);
					send("true" + safeSing);
					signal = false;
					b3.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[2] = "X";
					}
					else
					{
						xoField[2] = "O";
					}
					++moves;
					checkTable();
				}
			}
		});
		pCenter.add(b3);

		b4 = new JButton("   ");
		b4.setFont(fontText);
		b4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b4.setText(xo);
					send(xo + "4" + safeSing);
					send("true" + safeSing);
					signal = false;
					b4.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[3] = "X";
					}
					else
					{
						xoField[3] = "O";
					}
					++moves;
					checkTable();
				}
			}
		});
		pCenter.add(b4);

		b5 = new JButton("   ");
		b5.setFont(fontText);
		b5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b5.setText(xo);
					send(xo + "5" + safeSing);
					send("true" + safeSing);
					signal = false;
					b5.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[4] = "X";
					}
					else
					{
						xoField[4] = "O";
					}
					++moves;
					checkTable();
				}
			}
		});
		pCenter.add(b5);

		b6 = new JButton("   ");
		b6.setFont(fontText);
		b6.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b6.setText(xo);
					send(xo + "6" + safeSing);
					send("true" + safeSing);
					signal = false;
					b6.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[5] = "X";
					}
					else
					{
						xoField[5] = "O";
					}					
					++moves;
					checkTable();
				}
			}
		});
		pCenter.add(b6);

		b7 = new JButton("   ");
		b7.setFont(fontText);
		b7.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b7.setText(xo);
					send(xo + "7" + safeSing);
					send("true" + safeSing);
					signal = false;
					b7.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[6] = "X";
					}

					else
					{
						xoField[6] = "O";
					}
					++moves;
					checkTable();
				}
			}
		});
		pCenter.add(b7);

		b8 = new JButton("   ");
		b8.setFont(fontText);
		b8.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b8.setText(xo);
					send(xo + "8" + safeSing);
					send("true" + safeSing);
					signal = false;
					b8.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[7] = "X";
					}
					else
					{
						xoField[7] = "O";
					}
					++moves;
					checkTable();
				}
			}
		});
		pCenter.add(b8);

		b9 = new JButton("   ");
		b9.setFont(fontText);
		b9.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(signal)
				{
					b9.setText(xo);
					send(xo + "9" + safeSing);
					send("true" + safeSing);
					signal = false;
					b9.setEnabled(false);
					if(xo.equals("X"))
					{
						xoField[8] = "X";
					}
					else
					{
						xoField[8] = "O";
					}
					++moves;
					checkTable();
				}	
			}
		});
		pCenter.add(b9);

		setFields(false); 	// set all buttons on false till we wait for client to join
		add(pCenter, BorderLayout.CENTER);

		// --- OSTALE KOMPONENTE - OTHER COMPONENTS ---
		// --- EAST PANEL ---
		JPanel pEast = new JPanel();
		pEast.setLayout(new BorderLayout());
		pEast.setPreferredSize(new Dimension(200, height));
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(fontButtons);
		textArea.append("Join Game...\n");
		sp = new JScrollPane(textArea); 
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pEast.add(sp, BorderLayout.CENTER);
		add(pEast, BorderLayout.WEST);

		// --- SOUTH PANEL ---
		JPanel pSouth = new JPanel();
		pSouth.setLayout(new BorderLayout());
		pSouth.setPreferredSize(new Dimension(width/3, 50));
		message = new JTextField(" ");
		message.setEditable(false);
		message.setFont(fontText);
		message.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					textArea.append(nick.getText() + ":" + message.getText() + "\n");
					scrollToBottom();
					send(message.getText());
					message.setText(" ");
				}
			}
		});
		pSouth.add(message, BorderLayout.CENTER);
		add(pSouth, BorderLayout.SOUTH);

		ip = new JTextField("127.0.0.1");
		ip.setToolTipText("Enter Host IP addres");
		ip.setPreferredSize(new Dimension(100, 25));
		port = new JTextField("9876");
		port.setToolTipText("Enter Host PORT nubmer, default:9876");
		port.setPreferredSize(new Dimension(100, 25));
		nick = new JTextField();
		nick.setToolTipText("Enter your Nickname");
		nick.setPreferredSize(new Dimension(100, 25));

		// --- CREATE BUTTON ---
		/*create = new JButton("Create");
		create.setToolTipText("Create game");
		create.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				if(nick.getText().equals("") || nick.getText().equals(" "))
				{
					try { JOptionPane.showMessageDialog(null, "You did not input your nickname!"); } catch (ExceptionInInitializerError exc) { }
					return;
				}

				new FindPlayerThread("CreateButton"); // we need thread while we wait for client, because we don't want frozen frame
			}
		});*/

		// --- JOIN BUTTON ---
		join = new JButton("Join");
		join.setToolTipText("Join remote game");
		join.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event) 
			{
				try
				{ 	
					if(nick.getText().equals("") || nick.getText().equals(" "))
					{
						try { JOptionPane.showMessageDialog(null, "You did not input your nickname!"); } catch (ExceptionInInitializerError exc) { }
						return;
					}

					join.setEnabled(false);
					port.setEnabled(false);
					nick.setEnabled(false);

					findPlayer(); // we need thread while we wait for client, because we don't want frozen frame

					//socket = new Socket(ip.getText(), Integer.parseInt(port.getText())); 

					//objOutputStream = new ObjectOutputStream(socket.getOutputStream());
					//objOutputStream.flush();

					/*objInputStream = new ObjectInputStream(socket.getInputStream());

					chatmsg = (String) objInputStream.readObject();
					textArea.append(chatmsg + "\n");
					scrollToBottom();

					xo = "O";
					signal = false;

					nick2 = nick.getText();

					chatmsg = (String) objInputStream.readObject(); // get nick from host
					nick1 = "" + chatmsg;*/

					send("FUK");

					/*setFields(true);
					message.setEditable(true);

					ip.setEnabled(false);
					port.setEnabled(false);
					nick.setEnabled(false);

					textArea.append("X plays first!\n");
					scrollToBottom();

					join.setEnabled(false);
					//create.setEnabled(false);
					ip.setEnabled(false);
					port.setEnabled(false);
					nick.setEnabled(false);

					new receive("chatmsgOdServera"); // thread for receive data from host	*/	
				}
				catch(Exception e)
				{
					close();
					restart();
					try { JOptionPane.showMessageDialog(null, "JoinButton: Error: Server is offline: \n" + e); } catch (ExceptionInInitializerError exc) { }
				}
			}
		});

		// --- NEW GAME ---
		newGame = new JButton("New Game");
		newGame.setToolTipText("Play a new game");
		newGame.setEnabled(false);
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				send("ZahtevZaNovuPartiju!" + safeSing); // send request to client, for new game

				++turn;

				for (int i=0; i<xoField.length; i++)
				{
					xoField[i] = "";
				}

				if(turn %2 == 0)
				{
					signal = true;
					textArea.append("X  plays first!\n");
					scrollToBottom();
					send("false" + safeSing);
					send("X  plays first!");
				}
				else 
				{
					signal = false;
					send("true" + safeSing);
					textArea.append("O plays first!\n");
					scrollToBottom();
					send("O  plays first!");
				}

				initFields();
				setFields(true);
				newGame.setEnabled(false);
			}
		});

		JPanel pNorth = new JPanel();
		//pNorth.add(ip);
		//pNorth.add(port);
		pNorth.add(nick);
		//pNorth.add(create);
		pNorth.add(join);
		pNorth.add(newGame);
		add(pNorth, BorderLayout.NORTH);

		// --- WINDOW ADAPTER ---
		addWindowListener(new WindowAdapter()
		{
			public void windowActivated(WindowEvent event) 
			{
				try 
				{
					InetAddress thisIp = InetAddress.getLocalHost();
					ip.setText(thisIp.getHostAddress());
				} 
				catch (Exception e) 
				{ 
					ip.setText("127.0.0.1"); 
				}
			}	

			public void windowClosing(WindowEvent event) 
			{
				if(socket != null) 
				{
					send("Going offline!");
				}
				close();
			}
		});
	}



	private void findPlayer()
	{
		try 
		{
			//serverSocket = new ServerSocket(Integer.parseInt(port.getText())); 

			//textArea.append("Waiting for player...\n");
			//scrollToBottom();				

			socket = new Socket(ip.getText(), Integer.parseInt(port.getText())); 

			objOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objOutputStream.flush();
			
			send("YO");
			
			//incoming port
			objInputStream = new ObjectInputStream(socket.getInputStream());			
			port.setText((String) objInputStream.readObject());
			System.out.println("port: " + port.getText());
			
			//serverSocket = new ServerSocket(Integer.parseInt(port.getText()));
					
			//socket = serverSocket.accept();

			/*objOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objOutputStream.flush();
			objInputStream = new ObjectInputStream(socket.getInputStream());
			send(nick.getText() +  ": Successfully connected!");
			textArea.append("Client Successfully connected!\n");
			scrollToBottom();

			xo = "X";
			signal = true;

			nick1 = nick.getText();

			send(nick1);
			chatmsg = (String) objInputStream.readObject(); // prima NICK OD SERVERA
			nick2 = "" + chatmsg;
			setFields(true);
			message.setEditable(true);
			ip.setEnabled(false);
			textArea.append("X plays first!\n");
			scrollToBottom();
			new receive("chatmsgOdKlijenta");*/
		}
		catch (Exception e) 
		{ 
			close();
			restart();
			try { JOptionPane.showMessageDialog(null, "CreateButton: Error while creating game:\n" + e);  } catch (ExceptionInInitializerError exc) { }
		}
	}



	// --- CHECK FIELDS --- 
	private void checkTable()
	{
		// ------_X_X_X_------
		if
		(
				// CHECK X POSITIONS - VERTICAL
				(xoField[0].equals("X") && xoField[1].equals("X") && xoField[2].equals("X")) || 
				(xoField[3].equals("X") && xoField[4].equals("X") && xoField[5].equals("X")) ||
				(xoField[6].equals("X") && xoField[7].equals("X") && xoField[8].equals("X")) ||
				// CHECK X POSITIONS - HORIZONTAL
				(xoField[0].equals("X") && xoField[3].equals("X") && xoField[6].equals("X")) ||
				(xoField[1].equals("X") && xoField[4].equals("X") && xoField[7].equals("X")) ||
				(xoField[2].equals("X") && xoField[5].equals("X") && xoField[8].equals("X")) ||
				// CHECK X POSITIONS - DIAGONAL
				(xoField[0].equals("X") && xoField[4].equals("X") && xoField[8].equals("X")) ||
				(xoField[2].equals("X") && xoField[4].equals("X") && xoField[6].equals("X"))
				)
		{
			moves = 0;
			setFields(false);
			JOptionPane.showMessageDialog(null, nick1 + "WINS!");
			if(xo.equals("X")) { newGame.setEnabled(true); }
		}
		// ------_O_O_O_------
		else if
		(
				// CHECK O POSITIONS - HORIZONTAL
				(xoField[0].equals("O") && xoField[1].equals("O") && xoField[2].equals("O")) ||
				(xoField[3].equals("O") && xoField[4].equals("O") && xoField[5].equals("O")) ||
				(xoField[6].equals("O") && xoField[7].equals("O") && xoField[8].equals("O")) ||
				// CHECK O POSITIONS - VERTICAL
				(xoField[0].equals("O") && xoField[3].equals("O") && xoField[6].equals("O")) ||
				(xoField[1].equals("O") && xoField[4].equals("O") && xoField[7].equals("O")) ||
				(xoField[2].equals("O") && xoField[5].equals("O") && xoField[8].equals("O")) ||
				// CHECK O POSITIONS - DIAGONAL
				(xoField[0].equals("O") && xoField[4].equals("O") && xoField[8].equals("O")) ||
				(xoField[2].equals("O") && xoField[4].equals("O") && xoField[6].equals("O"))
				)
		{
			moves = 0;
			setFields(false);
			JOptionPane.showMessageDialog(null, nick2 + " WINS!");
			if(xo.equals("X")) { newGame.setEnabled(true); }
		}
		else
		{
			//CHECK IF IS DRAW
			if(moves >= 9)
			{
				moves = 0;
				send("NERESENO!" + safeSing);
				JOptionPane.showMessageDialog(null, "DRAW/NERESENO!");
				if(xo.equals("X")) { newGame.setEnabled(true); }
			}
		}
	}


	// --- enable/disable buttons ---
	private void setFields(boolean b)
	{
		b1.setEnabled(b);
		b2.setEnabled(b);
		b3.setEnabled(b);
		b4.setEnabled(b);
		b5.setEnabled(b);
		b6.setEnabled(b);
		b7.setEnabled(b);
		b8.setEnabled(b);
		b9.setEnabled(b);		
	}


	// --- set default text state to buttons ---
	private void initFields()
	{
		b1.setText("   ");
		b2.setText("   ");
		b3.setText("   ");
		b4.setText("   ");
		b5.setText("   ");
		b6.setText("   ");
		b7.setText("   ");
		b8.setText("   ");
		b9.setText("   ");
	}


	// --- Send Data over Internet ---
	private void send(String p)
	{			
		try
		{
			if(msgSignal)
			{
				objOutputStream.writeObject(p);
				objOutputStream.flush();
			}
		}
		catch(SocketException e)
		{
			if(msgSignal)
			{
				msgSignal = false;
				close();
				restart();
			}
		}
		catch(Exception e) 
		{ 
			if(msgSignal)
			{
				msgSignal = false;
				close();
				restart();
				try { JOptionPane.showMessageDialog(null, "Sending data/Disconnect:\n" + e); } catch (ExceptionInInitializerError exc) { }
			}
		}
	}


	// --- Receive data/messages thread ---
	private class receive implements Runnable
	{	
		private boolean nitSig;
		private String imeNiti;

		public receive(String i)
		{
			nitSig = true;
			imeNiti = i;
			new Thread(this, imeNiti).start();
		}

		public void run()
		{
			while(nitSig)
			{
				try
				{
					chatmsg = "";
					chatmsg = (String) objInputStream.readObject();			// receive messages

					if(imeNiti.equals("chatmsgOdServera")) 			// client receive data from host/server
					{
						if(chatmsg.equalsIgnoreCase("true" + safeSing))
						{
							signal = true;
						}
						else if(chatmsg.equalsIgnoreCase("false" + safeSing))
						{
							signal = false;
						}
						else if(chatmsg.equalsIgnoreCase("NERESENO!" + safeSing))
						{
							JOptionPane.showMessageDialog(null, "DRAW!");
						}
						else if(chatmsg.equalsIgnoreCase("ZahtevZaNovuPartiju!" + safeSing))
						{
							for (int i=0; i<xoField.length; i++)
							{
								xoField[i] = "";
							}
							signal = true;
							initFields();
							setFields(true);
						}
						else if(chatmsg.equalsIgnoreCase("X1" + safeSing))
						{
							b1.setText("X");
							xoField[0] = "X";
							b1.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("X2" + safeSing))
						{
							b2.setText("X");
							xoField[1] = "X";
							b2.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("X3" + safeSing))
						{
							b3.setText("X");
							xoField[2] = "X";
							b3.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("X4" + safeSing))
						{
							b4.setText("X");
							xoField[3] = "X";
							b4.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("X5" + safeSing))
						{
							b5.setText("X");
							xoField[4] = "X";
							b5.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("X6" + safeSing))
						{
							b6.setText("X");
							xoField[5] = "X";
							b6.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("X7" + safeSing))
						{
							b7.setText("X");
							xoField[6] = "X";
							b7.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("X8" + safeSing))
						{
							b8.setText("X");
							xoField[7] = "X";
							b8.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("X9" + safeSing))
						{
							b9.setText("X");
							xoField[8] = "X";
							b9.setEnabled(false);
							checkTable();
						}
						else
						{
							if(chatmsg.endsWith(safeSing))
							{
								chatmsg = chatmsg.substring(0, chatmsg.length() - safeSing.length());
							}
							textArea.append(nick1 + ":" + chatmsg + "\n");
							scrollToBottom();
						}
					}
					else if(imeNiti.equals("chatmsgOdKlijenta"))			// host/server receive data from client
					{
						if(chatmsg.equalsIgnoreCase("true" + safeSing))
						{
							signal = true;
						}
						else if(chatmsg.equalsIgnoreCase("O1" + safeSing))
						{
							++moves;
							b1.setText("O");
							xoField[0] = "O";
							b1.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("O2" + safeSing))
						{
							++moves;
							b2.setText("O");
							xoField[1] = "O";
							b2.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("O3" + safeSing))
						{
							++moves;
							b3.setText("O");
							xoField[2] = "O";
							b3.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("O4" + safeSing))
						{
							++moves;
							b4.setText("O");
							xoField[3] = "O";
							b4.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("O5" + safeSing))
						{
							++moves;
							b5.setText("O");
							xoField[4] = "O";
							b5.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("O6" + safeSing))
						{
							++moves;
							b6.setText("O");
							xoField[5] = "O";
							b6.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("O7" + safeSing))
						{
							++moves;
							b7.setText("O");
							xoField[6] = "O";
							b7.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("O8" + safeSing))
						{
							++moves;
							b8.setText("O");
							xoField[7] = "O";
							b8.setEnabled(false);
							checkTable();
						}
						else if(chatmsg.equalsIgnoreCase("O9" + safeSing))
						{
							++moves;
							b9.setText("O");
							xoField[8] = "O";
							b9.setEnabled(false);
							checkTable();
						}
						else
						{
							if(chatmsg.endsWith(safeSing))
							{
								chatmsg = chatmsg.substring(0, chatmsg.length() - safeSing.length());
							}
							textArea.append(nick2 + ":" + chatmsg + "\n");
							scrollToBottom();
						}
					}
				}
				catch (Exception e)
				{
					nitSig = false;
					close();
					restart();
					try { JOptionPane.showMessageDialog(null, "Receiving Data Failed/Disconnect:\n" + e); } catch (ExceptionInInitializerError exc) { }
				}
			}
		}
	}



	public void scrollToBottom()
	{
		textArea.setCaretPosition(textArea.getText().length());
	}



	// --- Turn OFF all streams ---
	private void close()
	{
		try { objOutputStream.flush(); 		} catch (Exception e) { }
		try { objOutputStream.close(); 		} catch (Exception e) { }
		try { objInputStream.close(); 		} catch (Exception e) { }
		try { serverSocket.close();	} catch (Exception e) { }
		try { socket.close(); 	} catch (Exception e) { }
	}


	// --- restart the game to the initial state ---
	private void restart()
	{
		initFields();
		setFields(false);

		chatmsg = "";
		xo = "";
		msgSignal = true;
		moves = 0;
		turn = 0;

		for (int i=0; i<xoField.length; i++)
		{
			xoField[i] = "";
		}

		ip.setEnabled(true);
		port.setEnabled(true);
		nick.setEnabled(true);
		//create.setEnabled(true);
		join.setEnabled(true);

		newGame.setEnabled(false);
		message.setEditable(false);
	}



	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new Peer().setVisible(true);				
			}
		});
	}
}
