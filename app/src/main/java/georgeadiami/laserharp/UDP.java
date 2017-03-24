package georgeadiami.laserharp;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Owner on 3/24/2017.
 */
public class UDP {
    public static DatagramSocket ds;
    private static UDP UDPModule = new UDP();
    private UDP(){
        try {
            ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static UDP getInstance() {
        return UDPModule;
    }

}
