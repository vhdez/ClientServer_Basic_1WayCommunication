package org.sla;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;

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

            // A client has connected!  Let's read data from it.
            while (!Thread.interrupted()) {
                try {
                    // Find out how much data it's sending
                    int dataCount = (Integer) dataReader.readObject();
                    System.out.println("Basic Server: RECEIVING " + dataCount + " data");
                    // Read that count of data from the input side of the socket.
                    for (int j = 0; j < dataCount; j = j + 1) {
                        Object data = dataReader.readObject();
                        System.out.println("Basic Server: RECEIVE \"" + data + "\"");
                    }
                } catch (EOFException ex) {
                    // EOFException happens when there is no data to read in dataReader
                    // Just yield until there is some more data
                    Thread.currentThread().yield();
                }
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
