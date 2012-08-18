import java.io.*;
import java.net.*;

public class HW3Client {

    /**
     * The UDP socket used by the client to communicate with the
     * server.
     */
    private DatagramSocket socket;
    /**
     * The IP address of the server
     */
    private InetAddress serverAddress;
    /**
     * The port the server is listening on
     */
    private int serverPort;

    /**
     * Constructs a HW3 Client object that will use UDP messages to
     * communicate with a HW3Server listening runing on
     * <code>hose</code> and listening on port number <code>port</code>.
     *
     * @param host the host name where the server is running.
     * @param port a number between 1024 and 65535 that the server is
     * listening on.  No parameter checking is performed.
     * @throws SocketException if the socket could not be opened, or
     * the socket could not bind to the specified local port.
     * @throws UnknownHostExpression if no IP address for the host
     * could be found.
     * @throws SecurityException if a security manager exists and its
     * checkListen method doesn't allow the operation.
     */
    public HW3Client(String host, int port)
            throws SocketException, UnknownHostException {
        //DEFINE ME
        // Create the socket for communication with the host.
        socket = new DatagramSocket();
        serverPort = port;
        serverAddress = InetAddress.getByName(host);
    }

    /**
     * Starts up the client.  The client must first print out a
     * listing of files available at the server and prompts the user
     * for the desired file.  It then gets the file from the server
     * and saves it locally in the working directory.  If the file is
     * not actually available at the server, the client should instead
     * print a message to the client console.
     *
     * @throws IOException whenever the underlying DatagramSocket used
     * by the client throws an IOException
     */
    public void run() throws IOException {
        //DEFINE ME
        // Request a listing of files from the server.
        // Display the listing on the console window
        // Prompt for a file
        // Request file from server
        // Save file locally with the same name
        //     OR display error message if no such file exists.
        //
        // You do not have to worry about what happens if either the
        //      request or the reply is dropped.        

        // Some I/O objects are initialized.
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out;

        byte[] receiveData = new byte[1024];

        String sentence = "";
        String process = "";
        String modifiedSentence = "";
        //  DatagramPacket objects are set to default values.
        DatagramPacket sendPacket = new DatagramPacket(new byte[0], 0, serverAddress, serverPort);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        System.out.println("Please enter 'request' to request a listing of files from the server...");
        sentence = inFromUser.readLine();
        if (sentence.toUpperCase().equals("REQUEST")) {
            System.out.println("Requesting...");

        } else {
            System.out.println("No Request sent. Exiting...");
            System.exit(0);
        }
        modifiedSentence = sendAndReceive(process, sentence, new byte[1024], receiveData, sendPacket, receivePacket);

        sentence = prompt(inFromUser);
        process = "file";
        modifiedSentence = sendAndReceive(process, sentence, new byte[1024], receiveData, sendPacket, receivePacket);

        if (!modifiedSentence.equals("File Does Not Exist")) {
            //File aFile = new File(fileName);
            out = new BufferedWriter(new FileWriter(sentence));
            out.write(modifiedSentence);
            out.close();
            System.out.println("File: " + sentence + " has been copied.");
        } else {
            System.out.println("FROM SERVER: " + modifiedSentence);
        }
    }

    /**
     * A private helper method that prompts the user for a
     * filename. You might find it useful.
     */
    private static String prompt(BufferedReader inFromUser) throws IOException {
        System.out.println("Enter a fileName...");//
        //System.console().printf("Enter a remote file name or QUIT to exit: ");
        return inFromUser.readLine();
    }

    //  Convenient method for code reuse
    private String sendAndReceive(String process, String sentence, byte[] sendData, byte[] receiveData, DatagramPacket sendPacket, DatagramPacket receivePacket) throws IOException {
        {
            sendData = sentence.getBytes();

            //  Packet is prepared to be sent to the server.
            sendPacket.setData(sendData);
            sendPacket.setLength(sendData.length);

            //  Packet is sent
            socket.send(sendPacket);

            receivePacket.setData(receiveData);
            receivePacket.setLength(receiveData.length);
            //  Packet is received from the server
            socket.receive(receivePacket);

            int length = receivePacket.getLength();
            String modifiedSentence = new String(receivePacket.getData(), 0, length);
            if (!process.equals("file")) {
                System.out.println("FROM SERVER: " + modifiedSentence);
            }
            return modifiedSentence;
        }
    }

    /**
     * Creates a HW3Client object that will connect to the server and
     * port listed as command line arguments and then starts the
     * client.
     *
     * DO NOT CHANGE ANYTHING IN MAIN!
     */
    public static void main(String[] args) throws Exception {
        HW3Client client = null;
        int port = -1;

        if (args.length < 2) {
            System.out.println("usage: java HW3Client <serverName> <port>");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[1]);
            if (port < 1024 || port > 65535) {
                System.out.println("port number must be between"
                        + " 1024 and 65535");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.out.println("Second argument was not an integer");
            System.exit(1);
        }

        client = new HW3Client(args[0], port);
        client.run();

    }
}