import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream
import java.nio.charset.StandardCharsets;

public class simpleTCPServer {

    private static final int BUFSIZE = 255;   // Size of receive buffer

    public static void main(String[] args) throws IOException {

        String message ="";
        String deVowel = "";

        if (args.length != 1)  // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
        ServerSocket servSock = new ServerSocket(servPort);

        byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer

        for (;;) { // Run forever, accepting and servicing connections
            Socket clntSock = servSock.accept();     // Get client connection

            System.out.println("Handling client at " +
                    clntSock.getInetAddress().getHostAddress() + " on port " +
                    clntSock.getPort());

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            // Receive until client closes connection, indicated by -1 return
            while ((in.read(byteBuffer)) != -1) {
                message = new String(byteBuffer);
                System.out.println("Client's Message: " + message);
                deVowel = message.replaceAll("[AEIOUaeiou]", "");
                System.out.println("Devoweled Message: " + deVowel);
                System.out.println("Sending back to client.");
                byte[] deVowelbytes = deVowel.getBytes();

                out.flush();
                out.write(deVowelbytes);

            }

            clntSock.close();  // Close the socket.  We are done with this client!
        }
        /* NOT REACHED */
    }
}
