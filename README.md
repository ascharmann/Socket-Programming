# Socket-Programming
Socket Programming projects in Java:
These projects explore the implementation of TCP and UDP Sockets in Java. 

simpleTCPServer.java and simpleTCPClient.java:
These programs implement a simple TCP client-server application. 
The TCP Server takes 1 command line arguement: any valid port number. The TCP Client takes 3 command arguments: a hostname (a decimal or 
dotted-quad IP address of the server), a port number (any valid port number that the server is bound to), and a string
(any sentence of at most 255 characters).
The Server receives the string from the client, "deVowelizes" (removes the vowels) the string, and sends the altered string back
to the client. The client logs the round-trip time of the interaction.

simpleUDPServer.java and simpleUDPClient.java:
These programs implement a simple UDP client-server application.
The UDP Server and UDP Client take the same command line arguments and perform the same actions as the TCP client-server application above, 
but use UDP sockets instead. 
