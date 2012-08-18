import java.io.*;
import java.net.*;

/**
 * A <b><u>toy</u></b> file transfer server running on UDP.  It only
 * works on files smaller than 1KB in size.  Behavior is
 * undefined for files that are 1KB in size or larger.  Nothing is
 * done about dropped packets and no exception handling is performed.
 * <p>
 * To complete the assignmnet you must define both the constructor and
 * the <code>run</code> method.
 */
public class HW3Server {

    /**
     * The UDP socket used by the server to listen for and respond to
     * file requests.
     */
    private DatagramSocket socket;

    /**
     * Constructs a HW3 Server object that will use a UDP socket listening
     * on port number <code>port</code>.
     *
     * @param port a number between 1024 and 65535 to be used as a
     * port number.  No parameter checking is performed.
     * @throws SocketException if the socket could not be opened, or
     * the socket could not bind to the specified local port.
     * @throws SecurityException if a security manager exists and its
     * checkListen method doesn't allow the operation.
     */
    public HW3Server(int port) throws SocketException {
        //DEFINE ME
        // A socket for listening for requests is created with the specified port.
        socket = new DatagramSocket(port);
    }

    /**
     * Starts up the server listening on its port.  Whenever a message
     * arrives, it must first determine if it is a request for a
     * listing of files or for a specific file to download.  If the
     * file with that name is not found, it must send some kind of
     * error code to the client so that the client can display an
     * error message to the user.
     *
     * @throws IOException whenever the underlying DatagramSocket
     * throws an IOException
     */
    public void run() throws IOException {
        
        //  Initialize variables to default values
        byte[] receiveData = new byte[1024];
        byte[] sendData;
        
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        InetAddress IPAddress = receivePacket.getAddress();
        DatagramPacket sendPacket = new DatagramPacket(new byte[0], 0, IPAddress, 0);

        /*  Loop to reveive the data packet and to define the data to be sent back to 
         *  the client. If client sends a request the server will show all files within
         *  the Server source directory. Then a client can send a filename and the server  
         *  copies the file associated with the filename back to the client
         */
        while (true) {
            //  Packet is received.
            receivePacket.setData(receiveData);
            receivePacket.setLength(receiveData.length);
            socket.receive(receivePacket);

            
            int length = receivePacket.getLength();
            String sentence = new String(receivePacket.getData(), 0, length);
            IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

           // System.out.println("Server Sentence: " + sentence);
            
            // The usre requests a list of files
            if (sentence.equals("request")) {

                File dir = new File(System.getProperty("user.dir"));
                String[] children = dir.list();
                String textFiles = "";
                
                // Loop to scan through the directory and list all files which are not
                //  folders.
                for (int i = 0; i < children.length; i++) {
                    // Get filename of file or directory
                    File aFile = new File(children[i]);
                    if (aFile.isFile()) {
                        textFiles += children[i] + ", ";
                    }
                }
                sendData = textFiles.substring(0, textFiles.length() - 2).getBytes();

            } else if ((new File(sentence).exists())) {
                File file = new File(sentence);

                // determine amount of bytes needed. A maximum of 1024 bytes are assumed
                sendData = new byte[(int) file.length()];

                // Create input stream to read file
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                // Read contents of file into byte array
                in.readFully(sendData);
                in.close();

            } else {
                sendData = "File Does Not Exist".getBytes();
            }

            //  Update the DatagramPacket  with new data.
            sendPacket.setData(sendData);
            sendPacket.setLength(sendData.length);
            sendPacket.setPort(port);
            sendPacket.setAddress(IPAddress);
            
            //  Send the packet to the Client.
            socket.send(sendPacket);
        }
    }

    /**
     * Creates a HW3Server object that listens on the port listed as a
     * command line arguments and then starts the server.
     */
    public static void main(String[] args) throws Exception {
        HW3Server server = null;
        int port = -1;

        if (args.length < 1) {
            System.out.println("usage: java HW3Server <port>");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[0]);
            if (port < 1024 || port > 65535) {
                System.out.println("port number must be between"
                        + " 1024 and 65535");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Argument was not an integer");
            System.exit(1);
        }

        server = new HW3Server(port);
        server.run();
    }
}
