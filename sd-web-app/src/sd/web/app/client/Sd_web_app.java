package sd.web.app.client;

import sd.web.app.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sd_web_app implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.add(verticalPanel, 0, 10);
		verticalPanel.setSize("310px", "393px");
		
		Image image = new Image("verisure.jpg");
		verticalPanel.add(image);
		
		TabPanel tabPanel = new TabPanel();
		tabPanel.setAnimationEnabled(true);
		verticalPanel.add(tabPanel);
		tabPanel.setSize("211px", "156px");
		
		VerticalPanel receivePanel = new VerticalPanel();
		receivePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		receivePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		tabPanel.add(receivePanel, "Receive ASCII", false);
		receivePanel.setSize("5cm", "3cm");
		
		Button btnReceive = new Button("Receive");
		btnReceive.setStyleName("sendButton");
		receivePanel.add(btnReceive);
		
		final Label lblReceivedText = new Label("Received Text");
		receivePanel.add(lblReceivedText);
		
		VerticalPanel sendPanel = new VerticalPanel();
		sendPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		sendPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		tabPanel.add(sendPanel, "Send ASCII", false);
		sendPanel.setSize("5cm", "3cm");
		
		TextArea txtrTextToSend = new TextArea();
		txtrTextToSend.setText("Text to send");
		sendPanel.add(txtrTextToSend);
		
		Button btnSend = new Button("Send");
		btnSend.setStyleName("sendButton");
		sendPanel.add(btnSend);
		
		Label lblAMasterThesis = new Label("A Master thesis project by Pétur and David");
		verticalPanel.add(lblAMasterThesis);

		
		// Create a handler for the btnReceive
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				queryDatabase();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					queryDatabase();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void queryDatabase() {
				// First, we validate the input.
				
				String textDb = "hej";
			

				// Then, we send the input to the server.
				
				
				greetingService.greetServer(textDb,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								
								/*
								 * RCP funkade inte
								 */
								
								lblReceivedText.setText("Fail");
								
							}

							public void onSuccess(String result) {
								/*
								 * RPC funkade
								 * 
								 * 
								 */
								lblReceivedText.setText(result);
								
								
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		btnReceive.addClickHandler(handler);
	}
}
