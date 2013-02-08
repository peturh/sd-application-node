package sd.web.app.server;

import sd.web.app.client.DatabaseCall;
import sd.web.app.client.MessageData;
import sd.web.app.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DatabaseCallImpl extends RemoteServiceServlet implements
		DatabaseCall {

	public String queryDB() throws IllegalArgumentException {
		// Verify that the input is valid. 
		
		//Database db = new Database();
		//db.openConnection("dm","654321");
	//	db.openConnection("root","1234");
		//String result = db.doStatement("select * from test.dev;");
	//	String result = db.doStatement("select * from shard03.node_faults where installation_id = 5778");
		//Escape data from the client to avoid cross-site script vulnerabilities.
	//	String result = db.doStatement("select * from test.dev");
	//	db.closeConnection();

		DB_xbn db = new DB_xbn();
		
		String message = db.retrieveMessage();
		//MessageData message = db.queryDB();
		
		
		return message; 
	}

}
