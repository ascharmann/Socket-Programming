public class Response {

    public byte tml;
    public short requestID;
    public byte errorCode = 0;
    public int result;
    public byte checksum;

    public Response(byte tml, short requestID, byte errorCode, int result, byte checksum) {
        this.tml = tml;
        this.requestID = requestID;
        this.errorCode = errorCode;
        this.result = result;
        this.checksum = checksum;
    }

    public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");

        String value = "Total Message Length: " + tml + EOLN +
                "Request ID: " + requestID + EOLN +
                "Error Code: " + errorCode + EOLN +
                "Result: " + result + EOLN;

        return value;
    }
}
