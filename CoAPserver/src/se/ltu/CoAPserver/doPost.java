package se.ltu.CoAPserver;

import java.util.logging.Logger;
import java.util.logging.Level;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.coap.LinkFormat;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.*;

public class doPost extends CoapResource {

  /** The logger. */
  private static final Logger logger = Logger.getLogger(CoapClient.class.getCanonicalName());

  public doPost() {
	this("dopost");
  }

  public doPost(String ri) {
	super(ri);
	getAttributes().setTitle("Handle POST with two-way blockwise transfer");
	getAttributes().addResourceType("block");
	logger.log(Level.INFO, "doPost started");
  }

  @Override
  public void handleGET(CoapExchange exchange) {
	logger.log(Level.INFO, "Got get request");
	exchange.respond(CONTENT, LinkFormat.serializeTree(this), APPLICATION_LINK_FORMAT);
  }

  @Override
  public void handlePOST(CoapExchange exchange) {
	logger.log(Level.INFO, "Got message from: " + exchange.getSourcePort());
	logger.log(Level.INFO, "Message: " + exchange.getRequestText());

	Request request = exchange.advanced().getRequest();

	String query = request.getOptions().getUriQueryString();
    	String[] str = query.split("=");
    	int temp;
    	if (str[0].equals("temp")) {
      		temp = Integer.parseInt(str[1]);
      		logger.log(Level.INFO, "Got temperature: " + temp + "C");
    	}

	if (exchange.getRequestOptions().hasContentFormat()) {
	  exchange.respond(CHANGED, exchange.getRequestText().toUpperCase(), TEXT_PLAIN);
	} else {
	  logger.log(Level.WARNING, "No content format");
	  exchange.respond(BAD_REQUEST, "Content-Format not set");
	}
  }
}
