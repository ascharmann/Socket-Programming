import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream

public class simpleTCPClient {

    private static final int BUFSIZE = 32;   // Size of receive buffer

    public static void main(String[] args) throws IOException {

        if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

        String server = args[0];       // Server name or IP address
        // Convert input String to bytes using the default character encoding
        byte[] message = args[1].getBytes();

        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server...sending message.");

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        double start = System.nanoTime();
        out.flush();
        out.write(message);  // Send the encoded string to the server


        // Receive the devoweled string back from the server
        byte[] received = new byte[message.length];
        if (in.read(received) != -1)
            System.out.println("Received: " + new String(received));

        double end = System.nanoTime();
        double time = ((end - start) / 1000000);

        System.out.println("Round trip time: " + time +"ms.");

        socket.close();  // Close the socket and its streams
    }
}
