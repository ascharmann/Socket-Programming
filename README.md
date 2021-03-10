# Socket-Programming
Socket Programming projects in Java:
These projects explore the implementation of TCP and UDP Sockets in Java. 

simpleTCPServer.java and simpleTCPClient.java: 

These programs implement a simple TCP client-server application. 
The TCP Server takes 1 command line arguement: any valid port number. The TCP Client takes 3 command arguments: the hostname of the server (a decimal or 
dotted-quad IP address), a port number that the server is bound to, and a string
(any sentence of at most 255 characters).
The Server receives the string from the client, "deVowelizes" (removes the vowels) the string, and sends the altered string back
to the client. The client logs the round-trip time of the interaction.

simpleUDPServer.java and simpleUDPClient.java:

These programs implement a simple UDP client-server application.
The UDP Server and UDP Client take the same command line arguments and perform the same actions as the TCP client-server application above, 
but use UDP sockets instead. 

ClientUDP.java and ServerUDP.java:

Supporting Classes: Request.java, Response.java, RequestDecoder.java, RequestDecoderBin.java, RequestEncoder.java, RequestEncoderBin.java, RequestBinConst.java
These programs implement a UDP client-server application with an encoding scheme.
ServerUDP takes as command line arguments: any valid port number, and an optional encoding scheme (UTF-16, UTF-16BE, etc). If no encoding scheme is entered as an argument, the default encoding scheme is used. 
ClientUDP takes as command line arguments: the hostname of the server (a decimal or dotted-quad IP address), the port number that the server is bound to, and the same encoding scheme that the server is using.
The Client prompts the user to enter values for a polynomial: a3(x^3) + a2(x^2) + a1(x) + x. The client then sends the values to the server, where the solution is calculated and sent back to the client. A checksum is used to verify that the datagram is delivered correctly.
