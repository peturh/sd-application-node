package ui;

import javax.swing.ImageIcon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;
import backend.Rest;
import backend.XBNConnection;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;



public class GUI {
	public int type = 0;
	public int advType = 1;
	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	
	public static void main(String[] args) {
		try {
			
			GUI window = new GUI();
			window.open();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose();
		System.exit(1);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(447, 310);
		shell.setText("Verisure ASCII Node");
		shell.setLayout(new BorderLayout(0, 0));
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		
		TabItem basicItem = new TabItem(tabFolder, SWT.NONE);
		basicItem.setText("Basic");
		
		Composite basicComposite = new Composite(tabFolder, SWT.NONE);
		basicItem.setControl(basicComposite);
		basicComposite.setLayout(null);
		
		Button btnDelMeasurments = new Button(basicComposite, SWT.NONE);
		
		btnDelMeasurments.setToolTipText("Delete the measurements from the device.");
		btnDelMeasurments.setBounds(269, 192, 159, 33);
		btnDelMeasurments.setText("Delete measurements");
		
		Button btnGetMeasurement = new Button(basicComposite, SWT.NONE);
		btnGetMeasurement.setToolTipText("Get the latest measurement from the device.");
		
		btnGetMeasurement.setBounds(0, 36, 159, 33);
		btnGetMeasurement.setText("Get new bloodvalue");
		
		final StyledText receiveArea = new StyledText(basicComposite, SWT.BORDER | SWT.WRAP);
		receiveArea.setLocation(167, 30);
		receiveArea.setSize(259, 92);
		receiveArea.setToolTipText("This is where the measurement will be displayd.");
		
		Button btnBase64 = new Button(basicComposite, SWT.RADIO);
		btnBase64.setToolTipText("Show the latest blood value (base64 encoded).");
		btnBase64.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
		
		btnDelMeasurments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0){
				receiveArea.setText("Deleting measurements, please wait...");

			}
			public void mouseUp(MouseEvent arg0) {
				
				Rest rest = new Rest();
				XBNConnection db = new XBNConnection();
				String theText = "DEL";
				do {
					// System.out.println("Längded på theText i restCallImpl "+theText.length()
					// +" och det står " +theText);

					try {
						//System.out.println("Hej");

						//rest.doPut("lol");
						rest.doPut(theText);
						System.out.println("Sov i tre sekunder.");
						Thread.sleep(3000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				while (!db.getAck(theText, rest));
				receiveArea.setText("Deleted measurements from device.");
			}
		});
		btnBase64.setSelection(true);
		btnBase64.setBounds(137, 130, 74, 25);
		btnBase64.setText("base64");
		
		Button btnHex = new Button(basicComposite, SWT.RADIO);
		btnHex.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				type = 2;
				System.out.println("Bytte type till: "+type);

			}
		});
		btnHex.setToolTipText("Show the measurement in hex.");
		btnHex.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
		btnHex.setBounds(217, 130, 50, 25);
		btnHex.setText("hex");
		
		Button btnAscii = new Button(basicComposite, SWT.RADIO);
		
		btnAscii.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				type = 1;
				System.out.println("Bytte type till: "+type);

			}
		});
		
		/*
		 * Return that the value should be represented as base64.
		 * 
		 */
		
		btnBase64.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				type = 0;
			}
		});
		
		btnAscii.setToolTipText("Show the measurement in ascii.");
		btnAscii.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
		btnAscii.setBounds(269, 130, 53, 25);
		btnAscii.setText("ascii");
		
		Label label = new Label(basicComposite, SWT.NONE);
		label.setText("A master thesis project by Pétur and David");
		label.setFont(SWTResourceManager.getFont("Cantarell", 7, SWT.NORMAL));
		label.setBounds(10, 204, 192, 21);
		
		Button btnGetDbValue = new Button(basicComposite, SWT.NONE);
		btnGetDbValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				
				XBNConnection db = new XBNConnection();
				receiveArea.setText(db.getNodeFaults(type));
			}
			@Override
			public void mouseDown(MouseEvent arg0) {
				receiveArea.setText("Getting latest measurements from db, please wait...");
			}
		});
		btnGetDbValue.setBounds(0, 71, 159, 33);
		btnGetDbValue.setText("Get latest bloodvalue");
		
		/*
		 *Get measurement from device event handler 
		 *
		 *@param The Mouse Adapter
		 * 
		 */
		
		
		btnGetMeasurement.addMouseListener(new MouseAdapter() {
			
			public void MouseDown(MouseEvent arg0){
				receiveArea.setText("Getting measurement from device, please wait...");

			}
			@Override
			public void mouseUp(MouseEvent arg0) {
				Rest rest = new Rest();
				XBNConnection db = new XBNConnection();
				String theText = "LOG";
				
				do {
					// System.out.println("Längded på theText i restCallImpl "+theText.length()
					// +" och det står " +theText);

					try {
						//System.out.println("Hej");

						//rest.doPut("lol");
						rest.doPut(theText);
						
						Thread.sleep(3000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				while (!db.getAck(theText, rest));
				
				receiveArea.setText(db.getNodeFaults(type));

			}
			@Override
			public void mouseDown(MouseEvent arg0) {
				receiveArea.setText("Getting measurements from device, please wait...");
			}
		});

		
		/*
		 * The advanced tab section
		 */
		
		TabItem advancedItem = new TabItem(tabFolder, SWT.NONE);
		advancedItem.setText("Advanced");
		
		Composite advComposite = new Composite(tabFolder, SWT.NONE);
		advancedItem.setControl(advComposite);
		advComposite.setLayout(null);
		
		Button advSendButton = new Button(advComposite, SWT.NONE);
		
	
		advSendButton.setToolTipText("Send arbitary data to the device.");
	
		advSendButton.setBounds(307, 31, 121, 33);
		advSendButton.setText("Send Data");
		
		Label lblNewLabel = new Label(advComposite, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Cantarell", 7, SWT.NORMAL));
		lblNewLabel.setBounds(10, 204, 192, 21);
		lblNewLabel.setText("A master thesis project by Pétur and David");
		
		final StyledText advSendArea = new StyledText(advComposite, SWT.BORDER | SWT.WRAP);
		advSendArea.setToolTipText("This is where you type your arbitary data to send to the device.");
		advSendArea.setBounds(10, 31, 291, 33);
		
		Button advReceiveButton = new Button(advComposite, SWT.NONE);
		

		
		advReceiveButton.setToolTipText("Receive data from the database.");
		advReceiveButton.setBounds(10, 70, 99, 33);
		advReceiveButton.setText("Receive Data");
		
		final StyledText advReceiveArea = new StyledText(advComposite, SWT.BORDER | SWT.WRAP);
		advReceiveArea.setToolTipText("This is where the receive data will be presented.");
		advReceiveArea.setBounds(115, 67, 313, 70);
		
		Button advAscii = new Button(advComposite, SWT.RADIO);
		advAscii.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				advType = 1;
			}
		});
		
		advReceiveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				advReceiveArea.setText("Receiving the data, please wait...");
				XBNConnection xbn = new XBNConnection();
				String text = xbn.getNodeFaults(advType);
				advReceiveArea.setText(text);
			}
			@Override
			public void mouseDown(MouseEvent arg0) {
				advReceiveArea.setText("Receivng data from device, please wait");
			}
		});
		advAscii.setSelection(true);
		advAscii.setToolTipText("Show the database results as ascii.");
		advAscii.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
		advAscii.setBounds(10, 109, 54, 25);
		advAscii.setText("ascii");
		
		Button advHex = new Button(advComposite, SWT.RADIO);
		advHex.setToolTipText("Show the database results as hex.");
		advHex.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
		advHex.setBounds(66, 109, 43, 25);
		advHex.setText("hex");
		advHex.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				advType = 2;
			}
		});
		
		Button btnDeleteDatabase = new Button(advComposite, SWT.NONE);
		btnDeleteDatabase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				XBNConnection db = new XBNConnection();
				db.deleteMessages();
				advReceiveArea.setText("Deleted data from database");
				
			}
		});
		btnDeleteDatabase.setToolTipText("Delete entries in the database.");
		btnDeleteDatabase.setText("Delete database");
		btnDeleteDatabase.setBounds(269, 192, 159, 33);
		
	
		

		/*
		 * This is the advanced send button. Can send arbitary text to the device.
		 */
		
		advSendButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				
				//advSendArea.setText("Sending: '"+advSendArea.getText()+"' to the device, please wait...");
				String theText = advSendArea.getText();
				System.out.println("Texten som ska skickas är: " + theText);
				
				Rest rest = new Rest();
				String chunkText = "";
				XBNConnection db = new XBNConnection();
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
				advSendArea.setText("Data sent to the device.");
				
			}
		
			
		});

	}

}
