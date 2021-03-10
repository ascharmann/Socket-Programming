import java.net.*;  // for DatagramSocket and DatagramPacket


public class ServerUDP {

  public static void main(String[] args) throws Exception {

      byte tml = 9;
      short requestID;
      byte errorCode = 0;
      int result;
      byte checksum = 0;

      if (args.length != 1 && args.length != 2)  // Test for correct # of args        
	  throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");
      
      int port = Integer.parseInt(args[0]);   // Receiving Port
      
      DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving      
      DatagramPacket packet = new DatagramPacket(new byte[9],9);

      for(;;) {
          sock.receive(packet);

          RequestDecoder decoder = (args.length == 2 ?   // Which encoding
                  new RequestDecoderBin(args[1]) :
                  new RequestDecoderBin());

          Request receivedRequest = decoder.decode(packet);

          System.out.println("Received Request.");
          System.out.println(receivedRequest);

          byte[] recCheck = new byte[] {receivedRequest.tml, receivedRequest.x, receivedRequest.a3, receivedRequest.a2, receivedRequest.a1, receivedRequest.a1, receivedRequest.a0};
          byte checkReceived = calcChecksum(recCheck);

          if (receivedRequest.tml == packet.getLength()) {
              errorCode = 0;
          }
          if (receivedRequest.checksum == checkReceived) {
              errorCode = 63;
          }
          if (receivedRequest.tml != 9) {
              errorCode = 127;
          }

          result = (receivedRequest.a3 * (int) Math.pow(receivedRequest.x, 3)) + (receivedRequest.a2 * (int) Math.pow(receivedRequest.x, 2)) +
                  (receivedRequest.a1 * receivedRequest.x) + receivedRequest.a0;

          byte[] check = new byte[] {tml, (byte) receivedRequest.requestID, errorCode, (byte) result};
          checksum = calcChecksum(check);

          System.out.println("Result = " + result);
          System.out.println("Sending to client: ");

          Response response = new Response(tml, receivedRequest.requestID, errorCode, result, checksum);
          RequestEncoder encoder = new RequestEncoderBin();
          byte[] encodedResponse = encoder.encode(response);

          for (byte hexValue : encodedResponse) {
              System.out.print(" 0x" + String.format("%02x", hexValue).toUpperCase());
          }

          DatagramPacket resultPacket = new DatagramPacket(encodedResponse, encodedResponse.length, packet.getAddress(), packet.getPort());

          sock.send(resultPacket);

      }
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
