import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner;

public class ClientUDP {

    private static final int TIMEOUT = 3000;
    private static final int MAXTRIES = 5;

  public static void main(String args[]) throws Exception {

      byte tml = 9;
      short ID = 1;
      byte x = 0;
      byte a1 = 0;
      byte a2 = 0;
      byte a3 = 0;
      byte a0 = 0;
      byte checksum = 0;

      if (args.length != 2 && args.length != 3)  // Test for correct # of args        
	  throw new IllegalArgumentException("Parameter(s): <Destination>" +
					     " <Port> [<encoding]");
      
      InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
      int destPort = Integer.parseInt(args[1]);               // Destination port

      System.out.println("Running Polynomial Calculator Server");
      System.out.println("Destination Address: " + destAddr.getHostAddress());
      System.out.println("Destination Port: " + destPort);
      System.out.println();

      //get user input
      boolean doContinue = true;
      while(doContinue = true) {
          Scanner input = new Scanner(System.in);
          System.out.println("This calculator computes 3rd degree polynomials:");
          System.out.println("P(x) = a3(x^3) + a2(x^2) + a1(x) + a0");
          System.out.println();
          System.out.println("Enter a value for x such that 0 <= x <= 64 : ");
          byte xinput = input.nextByte();
          x = xinput;
          System.out.println("Enter a value for a1 such that 0 <= a3 <= 64 : ");
          byte a3input = input.nextByte();
          a3 = a3input;
          System.out.println("Enter a value for a2 such that 0 <= a2 <= 64 : ");
          byte a2input = input.nextByte();
          a2 = a2input;
          System.out.println("Enter a value for a1 such that 0 <= a1 <= 64 : ");
          byte a1input = input.nextByte();
          a1 = a1input;
          System.out.println("Enter a value for a0 such that 0 <= a0 <= 64 : ");
          byte a0input = input.nextByte();
          a0 = a0input;
          System.out.println();

          byte[] check = new byte[] {tml, x, a3, a2, a1, a0};
          checksum = (byte) calcChecksum(check);

          Request request = new Request(tml, ID, x, a3, a2, a1, a0, checksum);

          DatagramSocket sock = new DatagramSocket(); // UDP socket for sending

          // Use the encoding scheme given on the command line (args[2])
          RequestEncoder encoder = (args.length == 3 ?
                  new RequestEncoderBin(args[2]) :
                  new RequestEncoderBin());

          byte[] codedRequest = encoder.encode(request); // Encode request

          System.out.print("Message sent (Bytes represented in Hexadecimal): ");
          for (byte hexValue : codedRequest) {
              System.out.print(" 0x" + String.format("%02x", hexValue).toUpperCase());
          }
          System.out.println();

          //Packet to send
          DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length,
                  destAddr, destPort);
          DatagramPacket responsePacket = new DatagramPacket(new byte[9], 9);

          int tries = 0;
          double startTime = System.nanoTime(); //Mark beginning time of request
          boolean received = false;
          do {
              sock.send(message);
              try {
                  sock.receive(responsePacket);

                  if (!responsePacket.getAddress().equals(destAddr))
                      throw new IOException("Received packet from unknown source.");

                  received = true;
              } catch (InterruptedIOException e) {
                  tries += 1;
                  System.out.println("Timed out, " + (MAXTRIES - tries) + " more tries...");
              }
          }
          while ((!received) && (tries < MAXTRIES));

          if (received) {

              double endTime = System.nanoTime(); //mark time response received
              double time = (endTime - startTime) / 1000000;
              RequestDecoder decoder = new RequestDecoderBin();
              Response receivedResponse = decoder.decodeResponse(responsePacket);

              System.out.println("Response Received:  ");
              System.out.print(receivedResponse);
              System.out.println();
              System.out.println("P(" + x + ") = " + a3 + "*" + x + "^3 + " + a2 + "*" + x + "^2 + " + a1 + " + " + a0 + " = " + receivedResponse.result);
              System.out.println("Total Round-Trip Time: " + time + "ms.");
              System.out.println();
          }

          System.out.println("Type \"quit\" to leave or \"continue\" to continue: ");
          Scanner scan = new Scanner(System.in);
          String str = scan.nextLine();
          if (str.equals("continue")) {
              ID++; //increase ID number
              doContinue = true;
          }
          if (str.equals("quit")) {
              System.exit(0);
          }
      }
      //sock.close();
  }

  private static byte calcChecksum(byte[] message) {
      int i = 0;
      short sum = 0;

      for(int j = 0; j < message.length; j++) {
          sum += message[j];
      }
      byte result = (byte) (~sum);
      byte all = (byte)(result + sum);

      //System.out.println(Integer.toBinaryString(all));

      return (byte) result;
  }


}
