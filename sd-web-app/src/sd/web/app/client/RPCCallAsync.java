package sd.web.app.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RPCCallAsync {
	void queryDB(AsyncCallback<String> callback) throws IllegalArgumentException;

	void restCall(String theText, AsyncCallback<String> callback) throws IllegalArgumentException;
	
}
