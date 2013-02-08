package sd.web.app.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DatabaseCallAsync {
	void queryDB(AsyncCallback<String> callback) throws IllegalArgumentException;
;
}
