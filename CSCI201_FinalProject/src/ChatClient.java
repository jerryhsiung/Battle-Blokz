//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.LayoutManager;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//
//
//public class ChatClient extends JFrame{
//	JPanel mainP = new JPanel(null);
//	//display texts
//	JTextArea tarea = new JTextArea();
//	JScrollPane jsp = new JScrollPane(tarea);
//	
//	//enter texts
//	JPanel enterP = new JPanel();
//	JTextField tfield = new JTextField();
//	JButton send = new JButton("Send");
//	
//	private static int PORT = 8901;
//    private Socket socket;
//    private BufferedReader in;
//    private PrintWriter out;
//	
//	public ChatClient(String serverAddress) throws Exception{
//		// Setup networking
//        socket = new Socket(serverAddress, PORT);
//        in = new BufferedReader(new InputStreamReader(
//            socket.getInputStream()));
//        out = new PrintWriter(socket.getOutputStream(), true);
//		
//		//Layout GUI
//		setSize(400, 400);
//		enterP.setLayout(new BoxLayout(enterP, BoxLayout.X_AXIS));
//		tarea.append("Welcome to the chat room");
//		tarea.setBackground(Color.magenta);
//		
//		tfield.addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent e) {
//				String text = tfield.getText();
//				int keyCode = e.getKeyCode();
//				if(keyCode == KeyEvent.VK_ENTER) {
//					toServer(text);
////					tarea.append("\n"+text);
//				}
//			}
//		});
//		
//		//send texts
//		send.addMouseListener(new MouseAdapter(){
//			public void mousePressed(MouseEvent e){
//				String text = tfield.getText();
//				toServer(text);
//
////        		tarea.append("\n"+text);
//        		
//        	}
//		});
//		tfield.setBackground(Color.gray);
//		tfield.requestFocus();
//		tfield.setEditable(true);
//		tfield.setFocusable(true);
//		enterP.add(tfield);
//		enterP.add(send);
//		
//		jsp.setBounds(0, 0, 400, 300);
//		enterP.setBounds(0, 300, 400, 30);
//		mainP.add(jsp);
//		mainP.add(enterP);
//		add(mainP);
//		setVisible(true);
//		setResizable(false);
//	}
//	
//	public void chat() throws Exception{
//
//		String response = null;
//		while(true){
//			response = in.readLine();
//			if(response.startsWith("MESSAGE")){
//				tarea.append("\n"+response.substring(8));
//			}
////			if(response.startsWith("ALL")){
////				break;
////			}
////			else if(response.startsWith("TEAM")){
////				break;
////			}
////			else if(response.startsWith("IND")){
////				break;
////			}
//		}
//		
//	}
//	
//	public void toServer(String text){
//		out.println("MESSAGE"+text);
////		if(text.startsWith("/all")){
////			out.println("ALL "+text);
////		}
////		else if(text.startsWith("/t")){
////			out.println("TEAM "+text);
////		}
////		else if(!text.startsWith("/")){
////			tarea.append("\n"+"Must type in /all for all players; /t for team; and /<username> for individual player"); 
////		}
////		else{
////			out.println("IND"+text);
////		}
//	}
//
//	public static void main(String[] args) throws Exception{
//		while (true) {
//            String serverAddress = (args.length == 0) ? "localhost" : args[1];
//            ChatClient client = new ChatClient(serverAddress);
//            client.chat();
//            
//        }
////		new ChatClient("localhost");
//	}
//
//}

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A simple Swing-based client for the chat server.  Graphically
 * it is a frame with a text field for entering messages and a
 * textarea to see the whole dialog.
 *
 * The client follows the Chat Protocol which is as follows.
 * When the server sends "SUBMITNAME" the client replies with the
 * desired screen name.  The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are
 * already in use.  When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all
 * chatters connected to the server.  When the server sends a
 * line beginning with "MESSAGE " then all characters following
 * this string should be displayed in its message area.
 */
public class ChatClient {

    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
//    JTextField textField = new JTextField(40);
//    JTextArea messageArea = new JTextArea(8, 40);
    JPanel mainP = new JPanel(null);
	//display texts
	JTextArea tarea = new JTextArea();
	JScrollPane jsp = new JScrollPane(tarea);
	
	//enter texts
	JPanel enterP = new JPanel();
	JTextField tfield = new JTextField();
	JButton send = new JButton("Send");

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Return in the
     * listener sends the textfield contents to the server.  Note
     * however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED
     * message from the server.
     */
    public ChatClient() {
    	
    	frame.setSize(400, 400);
		enterP.setLayout((LayoutManager) new BoxLayout(enterP, BoxLayout.X_AXIS));
		tarea.append("Welcome to the chat room");
		tarea.setBackground(Color.magenta);
		
		tfield.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				String text = tfield.getText();
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_ENTER) {
//					toServer(text);
					out.println(text);
					tfield.setText("");
//					tarea.append("\n"+text);
				}
			}
		});
		
		//send texts
		send.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				String text = tfield.getText();
//				toServer(text);
				out.println(text);
				tfield.setText("");
//        		tarea.append("\n"+text);
        		
        	}
		});
		tfield.setBackground(Color.gray);
		tfield.requestFocus();
		tfield.setEditable(true);
		tfield.setFocusable(true);
		enterP.add(tfield);
		enterP.add(send);
		
		jsp.setBounds(0, 0, 400, 300);
		enterP.setBounds(0, 300, 400, 30);
		mainP.add(jsp);
		mainP.add(enterP);
		frame.add(mainP);
		frame.setVisible(true);
		frame.setResizable(false);

        // Layout GUI
//        textField.setEditable(false);
//        messageArea.setEditable(false);
//        frame.getContentPane().add(textField, "North");
//        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
//        frame.pack();

        // Add Listeners
//        textField.addActionListener(new ActionListener() {
//            /**
//             * Responds to pressing the enter key in the textfield by sending
//             * the contents of the text field to the server.    Then clear
//             * the text area in preparation for the next message.
//             */
//            public void actionPerformed(ActionEvent e) {
//                out.println(textField.getText());
//                textField.setText("");
//            }
//        });
    }

    /**
     * Prompt for and return the address of the server.
     */
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Prompt for and return the desired screen name.
     */
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Connects to the server then enters the processing loop.
     */
    private void run() throws IOException {

        // Make connection and initialize streams
//        String serverAddress = getServerAddress();
    	String serverAddress = "localhost";
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
//            if (line.startsWith("SUBMITNAME")) {
//                out.println(getName());
//            }
//            else if (line.startsWith("NAMEACCEPTED")) {
//                textField.setEditable(true);
//            }
//            else 
            	if (line.startsWith("MESSAGE")) {
                tarea.append(line.substring(8) + "\n");
            }
        }
    }

    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}