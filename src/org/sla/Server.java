package org.sla;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        // Set up server-side networking
        try {
            // Start listening for client connections on port 5000
            ServerSocket connectionSocket = new ServerSocket(5000);
            System.out.println("Basic Server: server is listening on port 5000");
            // Wait until a client tries to connect
            Socket clientSocket = connectionSocket.accept();
            System.out.println("Basic Server: a client has connected!");

            // The clientSocket provides 2 separate streams for 2-way communication
            // the InputStream is for communication FROM client TO server
            InputStream communicationIn = clientSocket.getInputStream();
            // the OutputStream is for communication TO client FROM server
            OutputStream communicationOut = clientSocket.getOutputStream();
            // Create reader and writer from those streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(communicationIn));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(communicationOut));

            // A client has connected!  Let's read from the input side of the socket.
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Basic Server: RECEIVE \"" + message + "\"");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Basic Server: networking failed.  Exiting...");
        }

    }
}
