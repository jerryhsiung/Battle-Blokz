import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;


public class Server {

    private static final int PORT = 9001;

    //set of all clients' usernames
//    private static HashSet<String> names = new HashSet<String>();

    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    
    static int total_player;
    static int num_start = 0;
    static int num_end = 0;
    static Vector<String> ranking = new Vector<String>();
    static HashMap<String, Integer> records = new HashMap<String, Integer>();

    public static void main(String[] args) throws Exception {
        System.out.println("The server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {

                // Create character streams for the socket.
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                writers.add(out);

                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    else if(input.startsWith("JOIN1")){
                    	name = input.substring(6);
//                    	System.out.println("name = "+name);
                    	for (PrintWriter writer : writers) {
                            writer.println("JOIN1 " + name);
                        }
                    }
                    else if(input.startsWith("JOIN2")){
                    	name = input.substring(6);
//                    	System.out.println("name = "+name);
                    	for (PrintWriter writer : writers) {
                            writer.println("JOIN2 " + name);
                        }
                    }
                    else if(input.startsWith("QUIT1")){
                    	name = input.substring(5);
                    	System.out.println("name = "+name);

                    	for (PrintWriter writer : writers) {
                            writer.println("QUIT1 " + name);
                        }
                    }
                    else if(input.startsWith("QUIT2")){
                    	name = input.substring(5);
                    	System.out.println("name = "+name);
                    	for (PrintWriter writer : writers) {
                            writer.println("QUIT2 " + name);
                        }
                    }
                    else if(input.startsWith("START")){
                    	total_player = 2*Integer.parseInt(input.substring(5));
                    	num_start++;
                    	if(num_start==total_player){
                    		for (PrintWriter writer : writers) {
                                writer.println("START");
                            }
                    	}
                    }
                    else if(input.startsWith("MESSAGE")){
                    	int team = Integer.parseInt(input.substring(7,8));
                    	String msg = input.substring(8);
                    	String[] words = msg.split("\\s+");
                    	if(words[0].equals("/a")){
                    		for (PrintWriter writer : writers) {
                                writer.println("ALL " + name + ": " + input.substring(11));
                            }
                    	}
                    	else if(words[0].equals("/t")){
                    		for (PrintWriter writer : writers) {
                                writer.println("TEAM"+team+" " + name + ": " + msg.substring(3));
                            }
                    	}
                    	else{
                    		String ind_name = words[0].substring(1);
                    		System.out.println(ind_name);
                    		for (PrintWriter writer : writers) {
                    			writer.println("IND"+name+" "+ name + ": " + input.substring(9+ind_name.length()));
                                writer.println("IND"+ind_name+" "+ name + ": " + input.substring(9+ind_name.length()));
                            }
                    	}
                    }
                    else if(input.startsWith("ADDLINE")){
                    	String team = input.substring(7);
                    	for (PrintWriter writer : writers) {
                			writer.println("ADDLINE"+team);
                        }
                    }
                    else if(input.startsWith("LINESENT")){
                    	String[] words = input.split("\\s+");
                    	System.out.println(words[2]+" "+words[1]);
                    	records.put(words[2], Integer.parseInt(words[1]));
                    	
                    }
                    else if(input.startsWith("END")){
                    	System.out.println("add to ranking "+input.substring(4));
                    	ranking.insertElementAt(input.substring(4), 0);
                    	num_end++;
                    	if(num_end==total_player-1){
                    		String output;
                    		String end_output = "";
                    		System.out.println("record size = "+records.size());
                    		
                    		for(int i=0; i<records.size(); i++){
                    			end_output += ranking.get(i);
                    			end_output += " ";
                    			end_output += records.get(ranking.get(i));
                    			end_output += " ";
                    			records.remove(ranking.get(i));
                    		}
                    		System.out.println("left -> "+records);
                    		String temp = records.toString();
                    		output = temp.substring(1,temp.indexOf("="));
                    		output += " ";
                    		output += temp.substring(temp.indexOf("=")+1, temp.indexOf("}"));
                    		output += " ";
                    		for (PrintWriter writer : writers) {
                    			if(total_player==2){
                    				writer.println("ENDGAME2 "+output+end_output);
                    			}
                    			if(total_player==4){
                    				writer.println("ENDGAME4 "+output+end_output);
                    			}
                               
                            }
                    	}
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
//                if (name != null) {
//                    names.remove(name);
//                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}