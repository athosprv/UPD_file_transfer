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
public class HW3Server
{
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
    public HW3Server(int port)throws SocketException
    {
        //DEFINE ME
        // Create the socket for listening for requests
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
    public void run() throws IOException
    {
        //DEFINE ME
        //Loop for ever doing the following:
        //    Wait for a packet
        //    Check to see if it is a listing request or a file request
        //    Send the appropriate response
    }


    /**
     * Creates a HW3Server object that listens on the port listed as a
     * command line arguments and then starts the server.
     */
    public static void main(String[] args) throws Exception
    {
        HW3Server server = null;
        int port = -1;

        if (args.length < 1) {
            System.out.println("usage: java HW3Server <port>");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[0]);
            if (port < 1024 || port > 65535) {
                System.out.println("port number must be between" +
                                   " 1024 and 65535");
                System.exit(1);
            }
        }
        catch(NumberFormatException e) {
            System.out.println("Argument was not an integer");
            System.exit(1);
        }

        server = new HW3Server(port);
        server.run();
    }
}
