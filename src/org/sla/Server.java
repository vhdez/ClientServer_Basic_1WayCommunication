package org.sla;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        // Set up server-side networking

        try {
            // Use a ServerSocket to start listening for client connections on port 5000
            ServerSocket connectionSocket = new ServerSocket(5000);
            System.out.println("Basic Server: server is listening on port 5000");

            // Wait until a client tries to connect
            Socket socketServerSide = connectionSocket.accept();
            System.out.println("Basic Server: a client has connected!");

            // The Socket provides 2 separate streams for 2-way communication:
            //   the InputStream is for communication FROM client TO server
            //   the OutputStream is for communication TO client FROM server
            // Create data reader and writer from those stream (NOTE: ObjectOutputStream MUST be created FIRST)
            ObjectOutputStream dataWriter = new ObjectOutputStream(socketServerSide.getOutputStream());
            ObjectInputStream dataReader = new ObjectInputStream(socketServerSide.getInputStream());

            // A client has connected!  Let's find out how much data it's sending
            int dataCount = (Integer)dataReader.readObject();
            System.out.println("Basic Server: RECEIVING " + dataCount + " data");
            // Read that count of data from the input side of the socket.
            for (int i = 0; i < dataCount; i = i + 1) {
                Object data = dataReader.readObject();
                System.out.println("Basic Server: RECEIVE \"" + data + "\"");
            }

            // Done with communication: close sockets
            System.out.println("Basic Server: closing sockets");
            dataReader.close();
            dataWriter.close();
            socketServerSide.close();
            connectionSocket.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Basic Server: networking failed.  Exiting...");
        }

    }
}
