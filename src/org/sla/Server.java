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
                // Find out how much data client is sending
                int dataCount = -1;
                while (dataCount == -1) {
                    // Server may NOT have written dataCount yet; so we have to keep reading until it appears
                    try {
                        dataCount = (Integer) dataReader.readObject();
                    } catch (EOFException ex) {
                        // EOFException means dataCount has NOT been written yet; so yield and try reading again
                        Thread.currentThread().yield();
                    }
                }
                System.out.println("Basic Server: RECEIVING " + dataCount + " data");

                // Read that count of data from the input side of the socket.
                for (int j = 0; j < dataCount; j = j + 1) {
                    // Server may NOT have written data yet; so we have to keep reading until it appears
                    Object data = null;
                    while (data == null) {
                        try {
                            data = dataReader.readObject();
                        } catch (EOFException ex) {
                            // EOFException means data has NOT been written yet; so yield and try reading again
                            Thread.currentThread().yield();
                        }
                    }
                    System.out.println("Basic Server: RECEIVE \"" + data + "\"");
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
