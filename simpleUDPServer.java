import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException

public class simpleUDPServer {

    private static final int PACKETMAX = 255;  // Maximum size of datagram

    public static void main(String[] args) throws IOException {

        if (args.length != 1)  // Test for correct argument list
            throw new IllegalArgumentException("Parameter(s): <Port>");

        int servPort = Integer.parseInt(args[0]);

        DatagramSocket socket = new DatagramSocket(servPort);
        DatagramPacket packet = new DatagramPacket(new byte[PACKETMAX], PACKETMAX);

        for (;;) {  // Run forever, receiving and sending datagrams

            socket.receive(packet);     // Receive packet from client

            System.out.println("Handling client at " +
                    packet.getAddress().getHostAddress() + " on port " + packet.getPort());

            String message = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println("Client's Message: " + message);
            String deVowel = message.replaceAll("[AEIOUaeiou]", "");

            System.out.println("Devoweled Message: " + deVowel);
            System.out.println("Sending back to client.");

            byte[] deVowelBytes = new byte[deVowel.length()];
            deVowelBytes = deVowel.getBytes();
            DatagramPacket deVowelPacket = new DatagramPacket(deVowelBytes, deVowelBytes.length, InetAddress.getByName(packet.getAddress().getHostAddress()), packet.getPort());

            //byte[] deVowelPacketBytes = new byte[deVowel.getBytes()];

            socket.send(deVowelPacket);       // Send packet back to client
            packet.setLength(PACKETMAX); // Reset length to avoid shrinking buffer
        }
        /* NOT REACHED */
    }
}
