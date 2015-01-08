package se.ltu.CoAPserver;

import java.net.SocketException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.eclipse.californium.core.network.CoAPEndpoint;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.config.NetworkConfig;

import se.ltu.CoAPserver.doQuery;
import se.ltu.CoAPserver.doPost;

public class CoAPserver extends CoapServer {

  public static final int ERR_INIT_FAILED = 1;
	// allows port configuration in Californium.properties
  private static final int port = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

  /** The logger. */
  private static final Logger logger = Logger.getLogger(CoapClient.class.getCanonicalName());

  public static void main(String[] args) {
		// TODO Auto-generated method stub
	// create server
    try {
      CoapServer server = new CoAPserver();
          // ETSI Plugtest environment
//      server.addEndpoint(new CoAPEndpoint(new InetSocketAddress("::1", port)));
      server.addEndpoint(new CoAPEndpoint(new InetSocketAddress("0.0.0.0", port)));
      server.start();
      logger.log(Level.INFO, "Server started");
    } catch (Exception e) {
      e.printStackTrace();
      logger.log(Level.SEVERE, "Exiting");
      System.exit(ERR_INIT_FAILED);
    }
  }

  public CoAPserver() throws SocketException {

	NetworkConfig.getStandard() // used for plugtest
	.setInt(NetworkConfig.Keys.MAX_MESSAGE_SIZE, 64) 
	.setInt(NetworkConfig.Keys.PREFERRED_BLOCK_SIZE, 64)
	.setInt(NetworkConfig.Keys.NOTIFICATION_CHECK_INTERVAL_COUNT, 4)
	.setInt(NetworkConfig.Keys.NOTIFICATION_CHECK_INTERVAL_TIME, 30000);

    add(new doQuery());
    add(new doPost());
  }
}
