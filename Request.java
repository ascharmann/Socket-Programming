public class Request {

    public byte tml;            // Total Message Length
    public short requestID;     // Request ID
    public byte x;              // x in polynomial equation
    public byte a1;
    public byte a2;
    public byte a3;
    public byte a0;
    public byte checksum;
    

  public Request(byte tml, short ID , byte x,
                 byte a1, byte a2, byte a3, byte a0, byte checksum)  {
      this.tml          = tml;
      this.requestID    = ID;
      this.x            = x;
      this.a1           = a1;
      this.a2           = a2;
      this.a3           = a3;
      this.a0           = a0;
      this.checksum     = checksum;
  }

  public String toString() {
    final String EOLN = java.lang.System.getProperty("line.separator");
    String value = "Total Message Length: " + tml + EOLN +
                   "Request ID: " + requestID + EOLN +
                   "P(x) = a3(x^3) + a2(x^2) + a1(x) + a0: " + EOLN +
                   "P(" + x + ") = " + a3 + "*" + x + "^3 + " + a2 + "*" + x + "^2 + " + a1 + "*" + x + " + " + x + EOLN;

    return value;
  }
}
