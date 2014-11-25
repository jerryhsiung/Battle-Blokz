import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


public class Server {

    private static final int PORT = 9001;

    //set of all clients' usernames
    private static HashSet<String> names = new HashSet<String>();

    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    //deal with single client and broadcast its messages
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

//                while (true) {
//                    out.println("SUBMITNAME");
//                    name = in.readLine();
//                    if (name == null) {
//                        return;
//                    }
//                    synchronized (names) {
//                        if (!names.contains(name)) {
//                            names.add(name);
//                            break;
//                        }
//                    }
//                }

                // Now that a successful name has been chosen, add the
                // socket's print writer to the set of all writers so
                // this client can receive broadcast messages.
                out.println("NAMEACCEPTED");
                writers.add(out);

                // Accept messages from this client and broadcast them.
                // Ignore other clients that cannot be broadcasted to.
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
                    else if(input.startsWith("MESSAGE")){
                    	for (PrintWriter writer : writers) {
                            writer.println("MESSAGE " + name + ": " + input);
                        }
                    }
                    
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (name != null) {
                    names.remove(name);
                }
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