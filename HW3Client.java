import java.io.*;
import java.net.*;

public class HW3Client
{
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
        throws SocketException, UnknownHostException
    {
        //DEFINE ME
        // Create the socket for communication with the host.
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
    public void run() throws IOException
    {
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
    }


    /**
     * A private helper method that prompts the user for a
     * filename. You might find it useful.
     */
    private static String prompt()
    {
        System.console().printf("Enter a remote file name or QUIT to exit: ");
        return System.console().readLine();
    }


    /**
     * Creates a HW3Client object that will connect to the server and
     * port listed as command line arguments and then starts the
     * client.
     *
     * DO NOT CHANGE ANYTHING IN MAIN!
     */
    public static void main(String[] args) throws Exception
    {
        HW3Client client = null;
        int port = -1;

        if (args.length < 2) {
            System.out.println("usage: java HW3Client <serverName> <port>");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[1]);
            if (port < 1024 || port > 65535) {
                System.out.println("port number must be between" +
                                   " 1024 and 65535");
                System.exit(1);
            }
        }
        catch(NumberFormatException e) {
            System.out.println("Second argument was not an integer");
            System.exit(1);
        }

        client = new HW3Client(args[0], port);
        client.run();
    }

}
