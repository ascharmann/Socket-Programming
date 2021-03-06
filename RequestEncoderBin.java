import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class RequestEncoderBin implements RequestEncoder, RequestBinConst {

  private String encoding;  // Character encoding

  public RequestEncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public RequestEncoderBin(String encoding) {
    this.encoding = encoding;
  }

  public byte[] encode(Request request) throws Exception {

    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);
    out.writeByte(request.tml);
    out.writeShort(request.requestID);
    out.writeByte(request.x);
    out.writeByte(request.a3);
    out.writeByte(request.a2);
    out.writeByte(request.a1);
    out.writeByte(request.a0);
    out.writeByte(request.checksum);

    out.flush();
    return buf.toByteArray();
  }

  public byte[] encode(Response response) throws Exception {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);
    out.writeByte(response.tml);
    out.writeShort(response.requestID);
    out.writeByte(response.errorCode);
    out.writeInt(response.result);
    out.writeByte(response.checksum);

    out.flush();
    return buf.toByteArray();
  }


}
