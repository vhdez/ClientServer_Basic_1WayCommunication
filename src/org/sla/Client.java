package org.sla;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        try {
            // Connect to a server that is listening at IP address 127.0.0.1 (the same computer) on port 5000
            Socket socketClientSide = new Socket("127.0.0.1", 5000);
            System.out.println("Basic Client: connected to server at IP address 127.0.0.1 on port 5000");

            // The Socket provides 2 separate streams for 2-way communication:
            //   the InputStream is for communication FROM server TO client
            //   the OutputStream is for communication TO server FROM client
            // Create data reader and writer from those streams (NOTE: ObjectOutputStream MUST be created FIRST)
            ObjectOutputStream dataWriter = new ObjectOutputStream(socketClientSide.getOutputStream());
            ObjectInputStream dataReader = new ObjectInputStream(socketClientSide.getInputStream());

            // We're connected to server!  Let's say hi.
            // Let server know how much data will be sent
            Integer dataCount = 3;
            dataWriter.writeObject(dataCount);
            dataWriter.flush();
            System.out.println("Basic Client SENDING: " + dataCount + " data");

            // Write 3 messages to output side of the socket
            String data1 = "HI FROM CLIENT #" + args[0];
            dataWriter.writeObject(data1);
            dataWriter.flush();
            System.out.println("Basic Client SENT: " + data1);

            String data2 = "HI AGAIN FROM CLIENT #" + args[0];
            dataWriter.writeObject(data2);
            dataWriter.flush();
            System.out.println("Basic Client SENT: " + data2);

            String data3 = "WHAT'S UP FROM CLIENT #" + args[0];
            dataWriter.writeObject(data3);
            dataWriter.flush();
            System.out.println("Basic Client SENT: " + data3);

            // Done with communication: close sockets
            System.out.println("Basic Client: closing sockets");
            dataReader.close();
            dataWriter.close();
            socketClientSide.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Basic Client: networking failed. Exiting....");
        }

    }
}