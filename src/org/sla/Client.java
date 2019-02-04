package org.sla;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        try {
            // Connect to a server that is listening at IP address 127.0.0.1 (the same computer) on port 5000
            Socket serverSocket = new Socket("127.0.0.1", 5000);
            System.out.println("Basic Client: connected to server at IP address 127.0.0.1 on port 5000");

            // The serverSocket provides 2 separate streams for 2-way communication
            // the InputStream is for communication FROM server TO client
            InputStream communicationIn = serverSocket.getInputStream();
            // the OutputStream is for communication TO server FROM client
            OutputStream communicationOut = serverSocket.getOutputStream();

            // We're connected to server!  Let's say hi by writing to the output side of the socket.
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(communicationOut));
            writer.write("HI FROM CLIENT #" + args[0] + "\n");
            writer.flush();
            System.out.println("Basic Client: SENT \"HI FROM CLIENT #" + args[0]+ "\"");

            writer.write("HI AGAIN FROM CLIENT #" + args[0] + "\n");
            writer.flush();
            System.out.println("Basic Client: SENT \"HI AGAIN FROM CLIENT #" + args[0]+ "\"");

            writer.write("WHAT'S UP FROM CLIENT #" + args[0] + "\n");
            writer.flush();
            System.out.println("Basic Client: SENT \"WHAT'S UP FROM CLIENT #" + args[0]+ "\"");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Basic Client: networking failed. Exiting....");
        }

    }
}