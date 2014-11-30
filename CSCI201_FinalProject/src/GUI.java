import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import tetris.gui;

import javax.swing.Icon;
import javax.swing.JComboBox;

public class GUI extends JFrame implements Runnable{
	static JPanel cardPanel = new JPanel();
	static CardLayout cardLayout;
	private JTextField textField, textField_1, textField_2, textField_3, textField_4, textField_5;
	private JComboBox iconCombobox;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextArea chatTextArea;
	private Font font;
	private Map attributes;
	JTextArea team1TextArea = new JTextArea();
	JTextArea team2TextArea = new JTextArea();
	JButton btnJoin = new JButton("Join");
	JButton btnQuit = new JButton("Quit");
	JButton btnJoin_1 = new JButton("Join");
	JButton btnQuit_1 = new JButton("Quit");
	JButton btnNewButton_1 = new JButton("Start!");
	gui gameboard = new gui();
	JTextArea opponentTextArea = new JTextArea();
	JTextArea partnerTextArea = new JTextArea();
	JPanel endPanel = new JPanel();
	JLabel lblPlayer1 = new JLabel("");
	JLabel lblPlayer2 = new JLabel("");
	JLabel lblPlayer3 = new JLabel("");
	JLabel lblPlayer4 = new JLabel("");
	JLabel numLines1 = new JLabel("", JLabel.CENTER);
	JLabel numLines2 = new JLabel("", JLabel.CENTER);
	JLabel numLines3 = new JLabel("", JLabel.CENTER);
	JLabel numLines4 = new JLabel("", JLabel.CENTER);
	
	int temp_lineSent = 0;

	Vector<String> team1roster = new Vector<String>();
	Vector<String> team2roster = new Vector<String>();
	
	//database
	DatabaseApp database;
	
	//server/client
	BufferedReader in;
    PrintWriter out;
    
    //username
    String username;
    boolean start = false;
    boolean join_team1 = true;
    boolean end_sent = false;
    
    //tetris game
    public static JLabel jl;
	public static int lines;
	
	public GUI(DatabaseApp database) throws IOException
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
		JPanel helpPanel = new JPanel();
		JPanel teamPanel = new JPanel();
		
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
				database.addUser(textfield.getText(), passwordField_1.getPassword(), textField_4.getText());
				username = textfield.getText();
				cardLayout.show(cardPanel, "Play");
			}
		});
		btnCreate.setBounds(265, 415, 97, 25);
		createUser.add(btnCreate);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Start");
			}
		});
		btnCancel.setBounds(374, 415, 97, 25);
		createUser.add(btnCancel);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(265, 291, 50, 16);
		createUser.add(lblEmail);
		
		//adding icons to combobox
//		ImageIcon[] images = null;
//		for (int i = 1; i <= 9; i++) {
//			images[i] = new ImageIcon("tetris_icons/icon" + i + ".png");
//		}
		BufferedImage myPic = ImageIO.read(new File("tetris_icons/icon1.png"));
	   	int imageType = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(100, 100, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (true) {
    		g.setComposite(AlphaComposite.Src);
    	}
    	g.drawImage(myPic, 0, 0, 100, 100, null); 
    	g.dispose();
    	
    	BufferedImage myPic2 = ImageIO.read(new File("tetris_icons/icon2.png"));
	   	int imageType2 = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI2 = new BufferedImage(100, 100, imageType2);
    	Graphics2D g2 = scaledBI2.createGraphics();
    	if (true) {
    		g2.setComposite(AlphaComposite.Src);
    	}
    	g2.drawImage(myPic2, 0, 0, 100, 100, null); 
    	g2.dispose();
    	
    	BufferedImage myPic3 = ImageIO.read(new File("tetris_icons/icon3.png"));
	   	int imageType3 = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI3 = new BufferedImage(100, 100, imageType3);
    	Graphics2D g3 = scaledBI3.createGraphics();
    	if (true) {
    		g3.setComposite(AlphaComposite.Src);
    	}
    	g3.drawImage(myPic3, 0, 0, 100, 100, null); 
    	g3.dispose();
    	
    	BufferedImage myPic4 = ImageIO.read(new File("tetris_icons/icon4.png"));
	   	int imageType4 = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI4 = new BufferedImage(100, 100, imageType4);
    	Graphics2D g4 = scaledBI4.createGraphics();
    	if (true) {
    		g4.setComposite(AlphaComposite.Src);
    	}
    	g4.drawImage(myPic4, 0, 0, 100, 100, null); 
    	g4.dispose();
    	
    	BufferedImage myPic5 = ImageIO.read(new File("tetris_icons/icon5.png"));
	   	int imageType5 = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI5 = new BufferedImage(100, 100, imageType5);
    	Graphics2D g5 = scaledBI5.createGraphics();
    	if (true) {
    		g5.setComposite(AlphaComposite.Src);
    	}
    	g5.drawImage(myPic5, 0, 0, 100, 100, null); 
    	g5.dispose();
    	
    	BufferedImage myPic6 = ImageIO.read(new File("tetris_icons/icon6.png"));
	   	int imageType6 = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI6 = new BufferedImage(100, 100, imageType6);
    	Graphics2D g6 = scaledBI6.createGraphics();
    	if (true) {
    		g6.setComposite(AlphaComposite.Src);
    	}
    	g6.drawImage(myPic6, 0, 0, 100, 100, null); 
    	g6.dispose();
    	
    	BufferedImage myPic7 = ImageIO.read(new File("tetris_icons/icon7.png"));
	   	int imageType7 = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI7 = new BufferedImage(100, 100, imageType7);
    	Graphics2D g7 = scaledBI7.createGraphics();
    	if (true) {
    		g7.setComposite(AlphaComposite.Src);
    	}
    	g7.drawImage(myPic7, 0, 0, 100, 100, null); 
    	g7.dispose();
    	
    	BufferedImage myPic8 = ImageIO.read(new File("tetris_icons/icon8.png"));
	   	int imageType8 = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI8 = new BufferedImage(100, 100, imageType8);
    	Graphics2D g8 = scaledBI8.createGraphics();
    	if (true) {
    		g8.setComposite(AlphaComposite.Src);
    	}
    	g8.drawImage(myPic8, 0, 0, 100, 100, null); 
    	g8.dispose();
    	
    	BufferedImage myPic9 = ImageIO.read(new File("tetris_icons/icon9.png"));
	   	int imageType9 = true ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI9 = new BufferedImage(100, 100, imageType9);
    	Graphics2D g9 = scaledBI9.createGraphics();
    	if (true) {
    		g9.setComposite(AlphaComposite.Src);
    	}
    	g9.drawImage(myPic9, 0, 0, 100, 100, null); 
    	g9.dispose();
    	
		ImageIcon i1 = new ImageIcon(scaledBI);
		ImageIcon i2 = new ImageIcon(scaledBI2);
		ImageIcon i3 = new ImageIcon(scaledBI3);
		ImageIcon i4 = new ImageIcon(scaledBI4);
		ImageIcon i5 = new ImageIcon(scaledBI5);
		ImageIcon i6 = new ImageIcon(scaledBI6);
		ImageIcon i7 = new ImageIcon(scaledBI7);
		ImageIcon i8 = new ImageIcon(scaledBI8);
		ImageIcon i9 = new ImageIcon(scaledBI9);
		Object options[] = {i1, i2, i3, i4, i5, i6, i7, i8, i9};
		iconCombobox = new JComboBox(options);
		iconCombobox.setBounds(334, 323, 137, 79);
		createUser.add(iconCombobox);
		
		JLabel lblselectAIcon = new JLabel("<html>Select a Icon:</html>");
		lblselectAIcon.setBounds(265, 334, 56, 48);
		createUser.add(lblselectAIcon);
		JPanel playPanel = new JPanel();
		cardPanel.add(playPanel, "Play");
		playPanel.setLayout(null);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if(team1roster.size()!=0){
//					for(int i=0; i<team1roster.size(); i++){
//						team1TextArea.append("\n"+team1roster.get(i));
//					}
//				}
//				if(team2roster.size()!=0){
//					for(int i=0; i<team2roster.size(); i++){
//						team2TextArea.append("\n"+team2roster.get(i));
//					}
//				}
				cardLayout.show(cardPanel, "Team");
			}
		});
		btnPlay.setBounds(297, 228, 199, 82);
		playPanel.add(btnPlay);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Help");
			}
		});
		btnHelp.setBounds(297, 323, 199, 82);
		playPanel.add(btnHelp);
		
		JButton quitGameBtn = new JButton("Quit");
		quitGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		quitGameBtn.setBounds(297, 418, 199, 82);
		playPanel.add(quitGameBtn);
		
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
				JOptionPane.showMessageDialog(GUI.this, 
						"Email sent", 
						"Forgot Password", 
						JOptionPane.INFORMATION_MESSAGE);
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
		
		//help page
		cardPanel.add(helpPanel, "Help");
		helpPanel.setLayout(null);
		
		JLabel lblHelp = new JLabel("Help", SwingConstants.CENTER);
		lblHelp.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblHelp.setBounds(0, 34, 782, 92);
		helpPanel.add(lblHelp);
		
		JLabel lblRules = new JLabel("Rules:");
		lblRules.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRules.setBounds(139, 110, 75, 35);
		helpPanel.add(lblRules);
		font = lblRules.getFont();
		attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblRules.setFont(font.deriveFont(attributes));
		
		JLabel ruleDescription = new JLabel("<html>Each team has one or two players on each to start, as the rules of the game is to beat your opponent(s) by sending unclearable lines with the every 20 lines you complete in your game. Players are allow to communicate with everyone in the game or just with their teammate. </html");
		ruleDescription.setBounds(139, 147, 504, 71);
		helpPanel.add(ruleDescription);
		
		JLabel lblControls = new JLabel("Controls:");
		lblControls.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblControls.setBounds(139, 226, 75, 35);
		font = lblRules.getFont();
		attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblControls.setFont(font.deriveFont(attributes));
		helpPanel.add(lblControls);
		
		ImageIcon keyPic = new ImageIcon("arrowKeys.png");
		JLabel lblKeys = new JLabel(keyPic);
		lblKeys.setBounds(254, 294, 200, 113);
		helpPanel.add(lblKeys);
		
		JLabel lblRotate = new JLabel("Rotate");
		lblRotate.setBounds(331, 272, 56, 16);
		helpPanel.add(lblRotate);
		
		JLabel lblmoveRight = new JLabel("<html>Move Right</html>");
		lblmoveRight.setBounds(466, 342, 56, 42);
		helpPanel.add(lblmoveRight);
		
		JLabel lblmoveLeft = new JLabel("<html>Move Left</html>");
		lblmoveLeft.setBounds(203, 345, 44, 37);
		helpPanel.add(lblmoveLeft);
		
		JLabel lblMoveDown = new JLabel("Move Down");
		lblMoveDown.setBounds(324, 409, 75, 16);
		helpPanel.add(lblMoveDown);
		
		ImageIcon spacebarPic = new ImageIcon("spacebar.png");
		JLabel lblSpacebar = new JLabel(spacebarPic);
		lblSpacebar.setBounds(145, 463, 427, 71);
		helpPanel.add(lblSpacebar);
		
		JLabel lblDropBlock = new JLabel("Drop Block");
		lblDropBlock.setBounds(324, 547, 75, 16);
		helpPanel.add(lblDropBlock);
		
		JLabel lblChatInstruction = new JLabel("<html>Note: To chat to all players in the game type \"/a \" before you write your message, and to chat with partner, type \"/t \" before your message.</html>");
		lblChatInstruction.setBounds(203, 577, 298, 59);
		helpPanel.add(lblChatInstruction);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(cardPanel, "Play");
			}
		});
		btnBack.setBounds(504, 666, 97, 25);
		helpPanel.add(btnBack);
		
		cardPanel.add(teamPanel, "Team");
		JPanel gamePanel = new JPanel();
		cardPanel.add(gamePanel, "Game");
		
		
		gameboard.setBounds(12, 10, 361, 720);
		gamePanel.setLayout(null);
		
//		JPanel tetrisPanel = new JPanel();
//		tetrisPanel.setBorder(blackline);
//		tetrisPanel.setBounds(12, 13, 450, 729);
//		gamePanel.add(tetrisPanel);

		gamePanel.add(gameboard);
		
		
		
		opponentTextArea.setEditable(false);
		opponentTextArea.setBounds(482, 13, 288, 136);
		gamePanel.add(opponentTextArea);
		
		JLabel lblNewLabel = new JLabel("My Team:");
		lblNewLabel.setBounds(480, 162, 64, 22);
		gamePanel.add(lblNewLabel);
		
		
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
					if(join_team1){
						out.println("MESSAGE1"+text);
					}
					else{
						out.println("MESSAGE2"+text);
					}
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
				if(join_team1){
					out.println("MESSAGE1"+text);
				}
				else{
					out.println("MESSAGE2"+text);
				}
				textField_2.setText("");
        		
        	}
		});
		
		gamePanel.setFocusable(true);
		
		//game over
		cardPanel.add(endPanel, "End");
		endPanel.setLayout(null);
		
		JLabel gameOverLbl = new JLabel("Game Over!", JLabel.CENTER);
		gameOverLbl.setFont(new Font("Tahoma", Font.BOLD, 47));
		gameOverLbl.setBounds(0, 29, 782, 50);
		endPanel.add(gameOverLbl);
		
		JButton replayBtn = new JButton("Play Again");
		replayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Play");
			}
		});
		replayBtn.setBounds(222, 661, 175, 41);
		endPanel.add(replayBtn);
		
		JButton quitBtn = new JButton("Quit");
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(1);
			}
		});
		quitBtn.setBounds(409, 661, 175, 41);
		endPanel.add(quitBtn);
		
		JLabel lblUsername_1 = new JLabel("Username:");
		lblUsername_1.setBounds(235, 105, 82, 16);
		font = lblUsername_1.getFont();
		attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblControls.setFont(font.deriveFont(attributes));
		endPanel.add(lblUsername_1);
		
		JLabel lblLinesSent = new JLabel("Lines Sent:");
		lblLinesSent.setBounds(409, 105, 84, 16);
		font = lblLinesSent.getFont();
		attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblControls.setFont(font.deriveFont(attributes));
		endPanel.add(lblLinesSent);
		
//		JLabel lblKo = new JLabel("KO!");
//		lblKo.setBounds(528, 105, 56, 16);
//		font = lblKo.getFont();
//		attributes = font.getAttributes();
//		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
//		lblControls.setFont(font.deriveFont(attributes));
//		endPanel.add(lblKo);
		
		JLabel label_1 = new JLabel("1.)");
		label_1.setBounds(196, 150, 56, 16);
		endPanel.add(label_1);
		
		JLabel label_2 = new JLabel("2.)");
		label_2.setBounds(196, 190, 56, 16);
		endPanel.add(label_2);
		
		JLabel label_3 = new JLabel("3.)");
		label_3.setBounds(196, 231, 56, 16);
		endPanel.add(label_3);
		
		JLabel label_4 = new JLabel("4.)");
		label_4.setBounds(196, 269, 56, 16);
		endPanel.add(label_4);
		
		lblPlayer1.setBounds(222, 150, 175, 16);
		lblPlayer1.setForeground(Color.green);
		endPanel.add(lblPlayer1);
		
		lblPlayer2.setBounds(222, 190, 175, 16);
		lblPlayer2.setForeground(Color.green);
		endPanel.add(lblPlayer2);
		
		lblPlayer3.setBounds(222, 231, 175, 16);
		lblPlayer3.setForeground(Color.red);
		endPanel.add(lblPlayer3);
		
		lblPlayer4.setBounds(222, 269, 175, 16);
		lblPlayer4.setForeground(Color.red);
		endPanel.add(lblPlayer4);
				
		numLines1.setBounds(409, 150, 65, 16);
		numLines1.setForeground(Color.green);
		endPanel.add(numLines1);
			
		numLines2.setBounds(409, 190, 65, 16);
		numLines2.setForeground(Color.green);
		endPanel.add(numLines2);	
		
		numLines3.setBounds(409, 231, 65, 16);
		numLines3.setForeground(Color.red);
		endPanel.add(numLines3);	
		
		numLines4.setBounds(409, 269, 65, 16);
		numLines4.setForeground(Color.red);
		endPanel.add(numLines4);
		
//		JLabel numKO1 = new JLabel("", SwingConstants.CENTER);
//		numKO1.setForeground(Color.GREEN);
//		numKO1.setBounds(505, 150, 65, 16);
//		endPanel.add(numKO1);
//		
//		JLabel numKO2 = new JLabel("", SwingConstants.CENTER);
//		numKO2.setForeground(Color.GREEN);
//		numKO2.setBounds(505, 190, 65, 16);
//		endPanel.add(numKO2);
//		
//		JLabel numKO3 = new JLabel("", SwingConstants.CENTER);
//		numKO3.setForeground(Color.RED);
//		numKO3.setBounds(505, 231, 65, 16);
//		endPanel.add(numKO3);
//		
//		JLabel numKO4 = new JLabel("", SwingConstants.CENTER);
//		numKO4.setForeground(Color.RED);
//		numKO4.setBounds(505, 269, 65, 16);
//		endPanel.add(numKO4);
		startPanel.setLayout(null);
		
		cardLayout = (CardLayout) cardPanel.getLayout();
		
		//main page to login
		JLabel lblTitle = new JLabel("BATTLE BLOKZ", JLabel.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 47));
		lblTitle.setBounds(0, 252, 782, 50);
		startPanel.add(lblTitle);
		
		//password - login page
		passwordField = new JPasswordField();
		passwordField.setBounds(367, 403, 114, 22);
		startPanel.add(passwordField);
		
		//username - login page
		textField_3 = new JTextField();
		textField_3.setBounds(373, 368, 108, 22);
		startPanel.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean success = database.verifyLogin(textField_3.getText(), passwordField.getPassword());
				if(success){
					username = textField_3.getText();
					cardLayout.show(cardPanel, "Play");
				}
				else{
					JOptionPane.showMessageDialog(GUI.this, 
							"Invalid username/password", 
							"Failed Login", 
							JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		btnNewButton.setBounds(306, 435, 175, 41);
		startPanel.add(btnNewButton);
		
		JButton button = new JButton("Quit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.exit(1);
				cardLayout.show(cardPanel, "End");
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
		
		//choosing team
		teamPanel.setLayout(null);
		
		JLabel lblTeam = new JLabel("Team 1", JLabel.CENTER);
		lblTeam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTeam.setBounds(0, 55, 400, 50);
		teamPanel.add(lblTeam);
		
		JLabel lblTeam_1 = new JLabel("Team 2", JLabel.CENTER);
		lblTeam_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTeam_1.setBounds(400, 55, 382, 50);
		teamPanel.add(lblTeam_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (team1roster.size() == team2roster.size() && team1roster.size() != 0) {
					start = true;
					btnNewButton_1.setEnabled(false);
					out.println("START"+team1roster.size());
					
				}
				else {
					JOptionPane.showMessageDialog(GUI.this, "Both teams need to have same number of players.", "Can't Start!", JOptionPane.INFORMATION_MESSAGE);
				}
					
			}
		});
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(336, 519, 134, 70);
		teamPanel.add(btnNewButton_1);	
		
		team1TextArea.setEditable(false);
		team1TextArea.setBounds(128, 111, 151, 171);
		teamPanel.add(team1TextArea);	
		
		team2TextArea.setEditable(false);
		team2TextArea.setBounds(519, 111, 151, 171);
		teamPanel.add(team2TextArea);
		
		//joining team 1
		btnJoin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				join_team1 = true;
				out.println("JOIN1 "+username);
				btnJoin.setEnabled(false);
        		btnQuit.setEnabled(true);
        		btnJoin_1.setEnabled(false);
        		btnQuit_1.setEnabled(false);
        		btnNewButton_1.setEnabled(true);
			}
		});
		btnJoin.setBounds(152, 328, 97, 25);
		teamPanel.add(btnJoin);
		
		//quitting team 1
		btnQuit.setEnabled(false);
		btnQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				join_team1 = false;
				out.println("QUIT1"+username);
				btnJoin.setEnabled(true);
        		btnQuit.setEnabled(false);
        		btnJoin_1.setEnabled(true);
        		btnQuit_1.setEnabled(false);
        		btnNewButton_1.setEnabled(false);
			}
		});
		btnQuit.setBounds(152, 364, 97, 25);
		teamPanel.add(btnQuit);
		
		//joining team 2
		btnJoin_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				join_team1 = false;
				out.println("JOIN2 "+username);
				btnJoin_1.setEnabled(false);
        		btnQuit_1.setEnabled(true);
        		btnJoin.setEnabled(false);
        		btnQuit.setEnabled(false);
        		btnNewButton_1.setEnabled(true);
			}
		});
		btnJoin_1.setBounds(544, 328, 97, 25);
		teamPanel.add(btnJoin_1);
		
		//quitting team 2
		btnQuit_1.setEnabled(false);
		btnQuit_1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				out.println("QUIT2"+username);
				btnJoin.setEnabled(true);
        		btnQuit.setEnabled(false);
        		btnJoin_1.setEnabled(true);
        		btnQuit_1.setEnabled(false);
        		btnNewButton_1.setEnabled(false);
			}
		});
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
		
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		setVisible(true);
		setFocusable(false);

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
	public void updatelines() {
		
		
		if(!gameboard.end_game){
//			System.out.println("line sent = "+gameboard.lineSent);
			if ((gameboard.lineSent % 5 == 0) && (gameboard.lineSent != 0) && (gameboard.lineSent != temp_lineSent)) {
//				temp_lineSent = gameboard.lineSent;
				if(join_team1){
					out.println("ADDLINE"+1);
					System.out.println("sent line from"+1);
				}
				else{
					out.println("ADDLINE"+2);
					System.out.println("sent line from"+2);
				}
			}
			else if(gameboard.lineSent != temp_lineSent){
//				System.out.println("new line sent "+gameboard.lineSent);
				temp_lineSent = gameboard.lineSent;
				out.println("LINESENT "+temp_lineSent+" "+username);
				
			}
		}
		else if(!end_sent){
			out.println("END "+username);
			end_sent = true;
		}
		
	}
	
	public void getConnection(){
		// Make connection and initialize streams
	      String serverAddress = getServerAddress();
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
	
	public void run() {
		System.out.println("running sending lines");
		updatelines();
	}
	
	public void play() throws IOException {
        while (true) {
            String line = in.readLine();
            if(line.startsWith("JOIN1")){
        		String addname = line.substring(6);
        		team1TextArea.append("\n"+addname);
        		team1roster.addElement(addname);
        	}
        	else if(line.startsWith("QUIT1")){
        		//delete the username from team1 textarea
        		team1TextArea.setText("");
        		String deletename = line.substring(6);
        		team1roster.remove(deletename);
        		for(int i=0; i<team1roster.size(); i++){
        			team1TextArea.setText(team1roster.get(i));
        		}
        	}
        	else if(line.startsWith("JOIN2")){
        		String addname = line.substring(6);
        		team2TextArea.append("\n"+addname);
        		team2roster.addElement(addname);
        	}
        	else if(line.startsWith("QUIT2")){
        		//delete the username from team2 textarea
        		team2TextArea.setText("");
        		String deletename = line.substring(6);
        		team2roster.remove(deletename);
        		for(int i=0; i<team2roster.size(); i++){
        			team2TextArea.setText(team2roster.get(i));
        		}
        	}
        	else if(line.startsWith("START") && start){
        		out.println("LINESENT "+0+" "+username);
        		cardLayout.show(cardPanel, "Game");
				gameboard.startGame();
				
				gameboard.setFocusable(true);
				gameboard.requestFocus();
				gameboard.requestFocusInWindow();
				
				if(join_team1){
					for(int i=0; i<team2roster.size(); i++){
						opponentTextArea.append(team2roster.get(i)+"\n");
					}
					for(int i=0; i<team1roster.size(); i++){
						partnerTextArea.append(team1roster.get(i)+"\n");
					}
				}
				else{
					for(int i=0; i<team2roster.size(); i++){
						partnerTextArea.append(team2roster.get(i)+"\n");
					}
					for(int i=0; i<team1roster.size(); i++){
						opponentTextArea.append(team1roster.get(i)+"\n");
					}
				}
				
        	}
        	else if(line.startsWith("ALL")){
        		chatTextArea.append(line.substring(4) + "\n");
        	}
        	else if (line.startsWith("TEAM")) {
        		if(line.substring(4,5).equals("1")){
        			if(join_team1){
        				chatTextArea.append(line.substring(6) + "\n");
        			}
        		}
        		else if(line.substring(4,5).equals("2")){
        			if(!join_team1){
        				chatTextArea.append(line.substring(6) + "\n");
        			}
        		}
        	}
        	else if (line.startsWith("IND")) {
        		String msg = line.substring(3);
        		String[] words = msg.split("\\s+");
        		System.out.println("name passed from server = "+words[0]);
        		System.out.println("username = "+username);
        		if(username.equals(words[0])){
        			chatTextArea.append(msg.substring(username.length()+1) + "\n");
        		}
        	}
        	else if(line.startsWith("ADDLINE")){
        		int team = Integer.parseInt(line.substring(7));
        		if(team==1 && !join_team1){
        			System.out.println("add line to"+2);
        			gameboard.addRandomLine();
        		}
        		else if(team==2 && join_team1){
        			System.out.println("add line to"+1);
        			gameboard.addRandomLine();
        		}
        		else{
        			System.out.println("adding line?");
        		}
        	}
        	else if(line.startsWith("ENDGAME")){
        		if(!gameboard.end_game){
        			out.println("END "+username);
        		}
        		
        		int num = Integer.parseInt(line.substring(7,8));
        		String[] names = line.split("\\s+");
        		for(int i=0; i<names.length; i++){
        			System.out.println(names[i]);
        		}
        		if(team1roster.contains(names[1])){
        			lblPlayer1.setForeground(Color.green);
        			numLines1.setForeground(Color.green);
        		}
        		else{
        			lblPlayer1.setForeground(Color.red);
        			numLines1.setForeground(Color.red);
        		}
        		if(team1roster.contains(names[3])){
        			lblPlayer2.setForeground(Color.green);
        			numLines2.setForeground(Color.green);
        		}
        		else{
        			lblPlayer2.setForeground(Color.red);
        			numLines2.setForeground(Color.red);
        		}
        		lblPlayer1.setText(names[1]);
        		numLines1.setText(names[2]);
        		lblPlayer2.setText(names[3]);
        		numLines2.setText(names[4]);
        		if(num==4){
        			if(team1roster.contains(names[5])){
            			lblPlayer3.setForeground(Color.green);
            			numLines3.setForeground(Color.green);
            		}
            		else{
            			lblPlayer3.setForeground(Color.red);
            			numLines3.setForeground(Color.red);
            		}
        			if(team1roster.contains(names[7])){
            			lblPlayer4.setForeground(Color.green);
            			numLines4.setForeground(Color.green);
            		}
            		else{
            			lblPlayer4.setForeground(Color.red);
            			numLines4.setForeground(Color.red);
            		}
        			lblPlayer3.setText(names[5]);
        			numLines3.setText(names[6]);
        			lblPlayer4.setText(names[7]);
        			numLines4.setText(names[8]);
        		}
        		cardLayout.show(cardPanel, "End");
        	}
        }
    }
}
