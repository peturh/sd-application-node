package sd.web.app.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC db service.
 */
@RemoteServiceRelativePath("greet")
public interface RPCCall extends RemoteService {
	
	public String queryDB() throws IllegalArgumentException;
	
    public String restCall(String theText) throws IllegalArgumentException;
	
	
}
