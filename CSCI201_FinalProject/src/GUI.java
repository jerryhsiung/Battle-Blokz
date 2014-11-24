import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import tetris.gui;

public class GUI extends JFrame implements Runnable{
	static JPanel cardPanel = new JPanel();
	static CardLayout cardLayout;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JTextField textField_3;
	private JPasswordField passwordField_1;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextArea chatTextArea;
	
	//database
	DatabaseApp database;
	
	//server/client
	BufferedReader in;
    PrintWriter out;
    
    //tetris game
    public static JLabel jl;
	public static int lines;
	
	public GUI(DatabaseApp database)
	{
		super("CSCI 201 Final Project");
		setSize(800,800);
		setLocation(50,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		this.database = database;
		
		cardPanel.setBounds(0, 0, 782, 755);
		getContentPane().add(cardPanel);
		cardPanel.setLayout(new CardLayout());
		
		JPanel startPanel = new JPanel();
		JPanel createUser = new JPanel();
		JPanel forgotUser = new JPanel();
		JPanel playPanel = new JPanel();
		JPanel helpPanel = new JPanel();
		JPanel teamPanel = new JPanel();
		JPanel gamePanel = new JPanel();
		
		cardPanel.add(startPanel, "Start");
		cardPanel.add(createUser, "Create");
		createUser.setLayout(null);
		
		//creating users
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(265, 206, 84, 16);
		createUser.add(lblUsername);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setBounds(265, 250, 84, 16);
		createUser.add(lblPassword_1);
		
		JLabel lblCreateANew = new JLabel("Create A New User", JLabel.CENTER);
		lblCreateANew.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblCreateANew.setBounds(0, 82, 782, 92);
		createUser.add(lblCreateANew);
		
		//email - create user
		textField_4 = new JTextField();
		textField_4.setBounds(305, 288, 165, 22);
		createUser.add(textField_4);
		textField_4.setColumns(10);
		
		//password - create user
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(334, 247, 136, 22);
		createUser.add(passwordField_1);
		
		//username - create user
		JTextField textfield = new JTextField();
		textfield.setBounds(334, 203, 136, 22);
		createUser.add(textfield);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				database.addUser(textfield.getText(), new String(passwordField_1.getPassword()), textField_4.getText());
				cardLayout.show(cardPanel, "Play");
			}
		});
		btnCreate.setBounds(265, 332, 97, 25);
		createUser.add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Start");
			}
		});
		btnCancel.setBounds(374, 332, 97, 25);
		createUser.add(btnCancel);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(265, 291, 50, 16);
		createUser.add(lblEmail);
		
		//forgot login
		cardPanel.add(forgotUser, "Forgot");
		forgotUser.setLayout(null);
		
		JLabel label = new JLabel("Forgot Login", SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 42));
		label.setBounds(0, 74, 782, 92);
		forgotUser.add(label);
		
		JLabel lblEmail_1 = new JLabel("Email:");
		lblEmail_1.setBounds(263, 198, 56, 16);
		forgotUser.add(lblEmail_1);
		
		//email - forgot login
		textField_5 = new JTextField();
		textField_5.setBounds(304, 195, 213, 22);
		forgotUser.add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnSendLogin = new JButton("Send Login");
		btnSendLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnSendLogin.setBounds(289, 255, 97, 25);
		forgotUser.add(btnSendLogin);
		
		JButton btnCancel_1 = new JButton("Cancel");
		btnCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Start");
			}
		});
		btnCancel_1.setBounds(398, 255, 97, 25);
		forgotUser.add(btnCancel_1);
		
		//menu with 3 options: play, help and quit
		cardPanel.add(playPanel, "Play");
		playPanel.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Play");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Team");
			}
		});
		btnNewButton_2.setBounds(297, 228, 199, 82);
		playPanel.add(btnNewButton_2);
		
		JButton button_3 = new JButton("Help");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Help");
			}
		});
		button_3.setBounds(297, 323, 199, 82);
		playPanel.add(button_3);
		
		JButton button_4 = new JButton("Quit");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		button_4.setBounds(297, 418, 199, 82);
		playPanel.add(button_4);
		
		//help page
		cardPanel.add(helpPanel, "Help");
		helpPanel.setLayout(null);
		
		JLabel label_1 = new JLabel("Help", SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 42));
		label_1.setBounds(0, 34, 782, 92);
		helpPanel.add(label_1);
		cardPanel.add(teamPanel, "Team");
		cardPanel.add(gamePanel, "Game");
		startPanel.setLayout(null);
		
		cardLayout = (CardLayout) cardPanel.getLayout();
		
		//main page to login
		JLabel lblTetris = new JLabel("BATTLE BLOKZ", JLabel.CENTER);
		lblTetris.setFont(new Font("Tahoma", Font.BOLD, 47));
		lblTetris.setBounds(0, 252, 782, 50);
		startPanel.add(lblTetris);
		
		//password - login page
		passwordField = new JPasswordField();
		passwordField.setBounds(367, 400, 114, 22);
		startPanel.add(passwordField);
		
		//username - login page
		textField_3 = new JTextField();
		textField_3.setBounds(373, 368, 108, 22);
		startPanel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				database.verifyLogin(textField_3.getText(), new String(passwordField.getPassword()));
				cardLayout.show(cardPanel, "Play");
			}
		});
		btnNewButton.setBounds(306, 435, 175, 41);
		startPanel.add(btnNewButton);
		
		JButton button = new JButton("Quit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		button.setBounds(306, 597, 175, 41);
		startPanel.add(button);
		
		JButton button_1 = new JButton("Create New User");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(cardPanel, "Create");
			}
		});
		button_1.setBounds(306, 489, 175, 41);
		startPanel.add(button_1);
		
		JButton button_2 = new JButton("Forgot Login");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Forgot");
			}
		});
		button_2.setBounds(306, 543, 175, 41);
		startPanel.add(button_2);
		
		JLabel lblUser = new JLabel("Username:");
		lblUser.setBounds(306, 371, 75, 16);
		startPanel.add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(306, 406, 75, 16);
		startPanel.add(lblPassword);
		
		
		teamPanel.setLayout(null);
		
		JLabel lblTeam = new JLabel("Team 1", JLabel.CENTER);
		lblTeam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTeam.setBounds(0, 55, 400, 50);
		teamPanel.add(lblTeam);
		
		JLabel lblTeam_1 = new JLabel("Team 2", JLabel.CENTER);
		lblTeam_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTeam_1.setBounds(400, 55, 382, 50);
		teamPanel.add(lblTeam_1);
		
		gui gameboard = new gui();
		gameboard.setBounds(12, 10, 361, 720);
		
		JButton btnNewButton_1 = new JButton("Start!");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Game");
				gameboard.startGame();
				
				gameboard.setFocusable(true);
				gameboard.requestFocus();
				gameboard.requestFocusInWindow();
				

			}
		});
		btnNewButton_1.setBounds(336, 519, 134, 70);
		teamPanel.add(btnNewButton_1);
		
		JButton btnJoin = new JButton("Join");
		btnJoin.setBounds(152, 328, 97, 25);
		teamPanel.add(btnJoin);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(152, 364, 97, 25);
		teamPanel.add(btnQuit);
		
		JButton btnJoin_1 = new JButton("Join");
		btnJoin_1.setBounds(544, 328, 97, 25);
		teamPanel.add(btnJoin_1);
		
		JButton btnQuit_1 = new JButton("Quit");
		btnQuit_1.setBounds(544, 364, 97, 25);
		teamPanel.add(btnQuit_1);
		
		textField = new JTextField();
		textField.setBounds(519, 297, 151, 25);
		teamPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(127, 297, 151, 25);
		teamPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JTextArea team1TextArea = new JTextArea();
		team1TextArea.setEditable(false);
		team1TextArea.setBounds(128, 111, 151, 171);
		teamPanel.add(team1TextArea);
		
		JTextArea team2TextArea = new JTextArea();
		team2TextArea.setEditable(false);
		team2TextArea.setBounds(519, 111, 151, 171);
		teamPanel.add(team2TextArea);
		gamePanel.setLayout(null);
		
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
//		JPanel tetrisPanel = new JPanel();
//		tetrisPanel.setBorder(blackline);
//		tetrisPanel.setBounds(12, 13, 450, 729);
//		gamePanel.add(tetrisPanel);

		gamePanel.add(gameboard);
		
		
		JTextArea opponentTextArea = new JTextArea();
		opponentTextArea.setEditable(false);
		opponentTextArea.setBounds(482, 13, 288, 136);
		gamePanel.add(opponentTextArea);
		
		JLabel lblNewLabel = new JLabel("Partner:");
		lblNewLabel.setBounds(480, 162, 64, 22);
		gamePanel.add(lblNewLabel);
		
		JTextArea partnerTextArea = new JTextArea();
		partnerTextArea.setEditable(false);
		partnerTextArea.setBounds(482, 195, 288, 32);
		gamePanel.add(partnerTextArea);
		
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		chatTextArea.setBounds(482, 338, 288, 349);
		
		JScrollPane chatScroll = new JScrollPane(chatTextArea);
		chatScroll.setBounds(482, 338, 288, 349);
		gamePanel.add(chatScroll);
		
		JLabel lblChatBox = new JLabel("Chat Box:");
		lblChatBox.setBounds(482, 310, 200, 22);
		gamePanel.add(lblChatBox);
		
		//for chat
		textField_2 = new JTextField();
		textField_2.setBounds(482, 692, 206, 50);
		gamePanel.add(textField_2);
		textField_2.setColumns(10);
		textField_2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				String text = textField_2.getText();
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_ENTER) {
					out.println(text);
					textField_2.setText("");
				}
			}
		});
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(690, 693, 80, 49);
		gamePanel.add(btnSend);
		
		btnSend.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				String text = textField_2.getText();
				out.println(text);
				textField_2.setText("");
        		
        	}
		});
		
		gamePanel.setFocusable(true);
		
		setVisible(true);
		setFocusable(false);

//		gamePanel.requestFocus();
//		gamePanel.requestFocusInWindow();
//		addFocusListener(new FocusAdapter(){
//			public void focusGained(FocusEvent ae){
//				gameboard.requestFocus();
//				gameboard.requestFocusInWindow();
//				gamePanel.requestFocus();
//				gamePanel.requestFocusInWindow();
//			}
//		});
	}
	
	//connect IP addresses
	private String getServerAddress() {
        return JOptionPane.showInputDialog(
            this,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }
	
	//tetris game
	public static void updateLabel() {
		lines++;
		jl.setText("Lines Cleared - " + lines);
	}
	
	public void run() {
		// Make connection and initialize streams
      String serverAddress = getServerAddress();
//      String serverAddress = "localhost";
      Socket socket = null;
	try {
		socket = new Socket(serverAddress, 9001);
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      try {
		in = new BufferedReader(new InputStreamReader(
		      socket.getInputStream()));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      try {
		out = new PrintWriter(socket.getOutputStream(), true);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	
	public void chat() throws IOException {

        
        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
        	if (line.startsWith("MESSAGE")) {
        		chatTextArea.append(line.substring(8) + "\n");
            }
        }
    }
}
