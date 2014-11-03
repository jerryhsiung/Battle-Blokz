import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class GUI extends JFrame{
	static JPanel cardPanel = new JPanel();
	static CardLayout cardLayout;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public static void main(String[] args) {
		GUI window = new GUI();
	}
	
	public GUI()
	{
		super("CSCI 201 Final Project");
		setSize(800,800);
		setLocation(50,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		cardPanel.setBounds(0, 0, 782, 755);
		getContentPane().add(cardPanel);
		cardPanel.setLayout(new CardLayout());
		
		JPanel startPanel = new JPanel();
		JPanel teamPanel = new JPanel();
		JPanel gamePanel = new JPanel();
		
		cardPanel.add(startPanel, "Start");
		cardPanel.add(teamPanel, "Team");
		cardPanel.add(gamePanel, "Game");
		startPanel.setLayout(null);
		
		cardLayout = (CardLayout) cardPanel.getLayout();
		
		JLabel lblTetris = new JLabel("Tetris", JLabel.CENTER);
		lblTetris.setFont(new Font("Tahoma", Font.BOLD, 47));
		lblTetris.setBounds(0, 252, 782, 50);
		startPanel.add(lblTetris);
		
		JButton btnNewButton = new JButton("Play Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cardLayout.show(cardPanel, "Team");
			}
		});
		btnNewButton.setBounds(306, 360, 175, 65);
		startPanel.add(btnNewButton);
		
		JButton button = new JButton("Quit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		button.setBounds(306, 435, 175, 65);
		startPanel.add(button);
		teamPanel.setLayout(null);
		
		JLabel lblTeam = new JLabel("Team 1", JLabel.CENTER);
		lblTeam.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTeam.setBounds(0, 55, 400, 50);
		teamPanel.add(lblTeam);
		
		JLabel lblTeam_1 = new JLabel("Team 2", JLabel.CENTER);
		lblTeam_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTeam_1.setBounds(400, 55, 382, 50);
		teamPanel.add(lblTeam_1);
		
		JButton btnNewButton_1 = new JButton("Start!");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Game");
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
		
		JPanel tetrisPanel = new JPanel();
		tetrisPanel.setBorder(blackline);
		tetrisPanel.setBounds(12, 13, 450, 729);
		gamePanel.add(tetrisPanel);
		
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
		
		JTextArea chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		chatTextArea.setBounds(482, 338, 288, 349);
		
		JScrollPane chatScroll = new JScrollPane(chatTextArea);
		chatScroll.setBounds(482, 338, 288, 349);
		gamePanel.add(chatScroll);
		
		JLabel lblChatBox = new JLabel("Chat Box:");
		lblChatBox.setBounds(482, 310, 200, 22);
		gamePanel.add(lblChatBox);
		
		textField_2 = new JTextField();
		textField_2.setBounds(482, 692, 206, 50);
		gamePanel.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(690, 693, 80, 49);
		gamePanel.add(btnSend);
		
		setVisible(true);
	}
}
