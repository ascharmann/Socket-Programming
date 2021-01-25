import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException

public class simpleUDPClient {

    private static final int TIMEOUT = 3000;   // Resend timeout (milliseconds)
    private static final int MAXTRIES = 5;     // Maximum retransmissions

    public static void main(String[] args) throws IOException {

        if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

        InetAddress serverAddress = InetAddress.getByName(args[0]);  // Server address
        // Convert input String to bytes using the default character encoding
        byte[] bytesToSend = args[1].getBytes();

        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        DatagramSocket socket = new DatagramSocket();

        socket.setSoTimeout(TIMEOUT);  // Maximum receive blocking time (milliseconds)

        DatagramPacket sendPacket = new DatagramPacket(bytesToSend,  // Sending packet
                bytesToSend.length, serverAddress, servPort);

        DatagramPacket receivePacket =                              // Receiving packet
                new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);

        int tries = 0;      // Packets may be lost, so we have to keep trying

        double start = System.nanoTime();
        boolean receivedResponse = false;
        do {
            socket.send(sendPacket);          // Send the string
            try {
                socket.receive(receivePacket);  // Attempt reply reception

                if (!receivePacket.getAddress().equals(serverAddress))  // Check source
                    throw new IOException("Received packet from an unknown source");

                receivedResponse = true;
            } catch (InterruptedIOException e) {  // We did not get anything
                tries += 1;
                System.out.println("Timed out, " + (MAXTRIES-tries) + " more tries...");
            }
        } while ((!receivedResponse) && (tries < MAXTRIES));

        if (receivedResponse)
            System.out.println("Received: " + new String(receivePacket.getData()));
        else
            System.out.println("No response -- giving up.");
        double end = System.nanoTime();
        double time = ((end - start) / 1000000);
        System.out.println("Round trip time: " + time + "ms.");

        socket.close();
    }
}