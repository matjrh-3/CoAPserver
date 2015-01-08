package se.ltu.CoAPserver;

import java.util.logging.Logger;
import java.util.logging.Level;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.server.resources.CoapExchange;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.*;

public class doQuery extends CoapResource {

  /** The logger. */
  private static final Logger logger = Logger.getLogger(CoapClient.class.getCanonicalName());
	
  public doQuery() {
	super("doquery");
	getAttributes().setTitle("Resource accepting query parameters");
    logger.log(Level.INFO, "doQuery started");
  }

  @Override
  public void handleGET(CoapExchange exchange) {
	logger.log(Level.INFO, "Got get request");
	// get request to read out details
	Request request = exchange.advanced().getRequest();

	StringBuilder payload = new StringBuilder();
	payload.append(String.format("Type: %d (%s)\nCode: %d (%s)\nMID: %d\n",
			request.getType().value, request.getType(),
			request.getCode().value, request.getCode(),
			request.getMID()));
	payload.append("?").append(request.getOptions().getUriQueryString());
	if (payload.length() > 64) {
	  payload.delete(63, payload.length());
	  payload.append('\0');
	}
		
	// complete the request
	exchange.respond(CONTENT, payload.toString(), TEXT_PLAIN);
	logger.log(Level.INFO, "Answered get request");
  }
}
