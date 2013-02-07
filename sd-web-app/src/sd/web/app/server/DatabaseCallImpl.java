package sd.web.app.server;

import sd.web.app.client.DatabaseCall;
import sd.web.app.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseCallImpl extends RemoteServiceServlet implements
		DatabaseCall {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		
		Database db = new Database();
		db.openConnection("dm","654321");
	//	db.openConnection("root","1234");
		//String result = db.doStatement("select * from test.dev;");
		String result = db.doStatement("select * from shard03.node_faults where installation_id = 5768");
		//Escape data from the client to avoid cross-site script vulnerabilities.
	//	String result = db.doStatement("select * from test.dev");
		db.closeConnection();

		return result; //"Hello, " + input + "!<br><br>I am running " + serverInfo
				//+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
