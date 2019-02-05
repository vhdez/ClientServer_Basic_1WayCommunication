package org.sla;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.ConnectException;

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
            // Let server know how much data will be sent to it
            Integer dataCount = 3;
            dataWriter.writeObject(dataCount);
            dataWriter.flush();
            System.out.println("Basic Client SENDING: " + dataCount + " data");

            // Write 3 messages to output side of the socket
            String data = "HI FROM CLIENT";
            dataWriter.writeObject(data);
            dataWriter.flush();
            System.out.println("Basic Client SENT: " + data);

            data = "Hi again!";
            dataWriter.writeObject(data);
            dataWriter.flush();
            System.out.println("Basic Client SENT: " + data);

            data = "WHAT'S UP?";
            dataWriter.writeObject(data);
            dataWriter.flush();
            System.out.println("Basic Client SENT: " + data);

            dataCount = 2;
            dataWriter.writeObject(dataCount);
            dataWriter.flush();
            System.out.println("Basic Client SENDING: " + dataCount + " data");

            // Write 3 messages to output side of the socket
            data = "You still there?";
            dataWriter.writeObject(data);
            dataWriter.flush();
            System.out.println("Basic Client SENT: " + data);

            data = "Great!";
            dataWriter.writeObject(data);
            dataWriter.flush();
            System.out.println("Basic Client SENT: " + data);

            // Done with communication: close sockets
            System.out.println("Basic Client: closing sockets");
            dataReader.close();
            dataWriter.close();
            socketClientSide.close();

        } catch (ConnectException ex) {
            System.out.println("Basic Client: ERROR There's no Server running yet at IP address 127.0.0.1 (the same computer) on port 5000");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Basic Client: networking failed. Exiting....");
        }

    }
}