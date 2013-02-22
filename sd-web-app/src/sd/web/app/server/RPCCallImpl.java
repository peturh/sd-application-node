package sd.web.app.server;

import sd.web.app.client.RPCCall;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RPCCallImpl extends RemoteServiceServlet implements RPCCall {

	public String queryDB() throws IllegalArgumentException {

		DB_xbn db = new DB_xbn();

		String message = db.retrieveMessage();
		// MessageData message = db.queryDB();

		return message;
	}

	@Override
	public String restCall(String theText) throws IllegalArgumentException {
		System.out.println("Du ska precis inititera en sändning till en applications nod i Verisures säkra hemnätverk.");
		
		System.out.println("Texten som ska skickas är: " + theText);
		
		RestClient rest = new RestClient();
		String chunkText = "";
		DB_xbn db = new DB_xbn();
		StringBuilder sb = new StringBuilder(theText);
		// Add and @ to be sure that there will never be two strings that are
		// the same in a row.
		// for (int i = 3; i < sb.toString().length(); i += 6) {
		// sb.insert(i, "@");
		// }
		// theText = sb.toString();
		System.out.println(theText);

		do {
			// System.out.println("Längded på theText i restCallImpl "+theText.length()
			// +" och det står " +theText);

			if (theText.length() < 3) {
				chunkText = theText.substring(0, theText.length());
				int pad = theText.length() % 3;
				if (pad == 1) {
					chunkText = chunkText + "  ";
					theText = theText + "  ";
				} else {
					chunkText = chunkText + " ";
					theText = theText + " ";
				}
			} else {
				chunkText = theText.substring(0, 3);
			}

			theText = theText.substring(3);
			try {

				rest.doPut(chunkText);
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (theText.length() > 0 && db.getAck(chunkText, rest));

		return "Done";

	}

}
