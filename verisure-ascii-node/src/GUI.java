import java.awt.Toolkit;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;

/*
 * Uses swt.jar
 * 
 * 
 */

public class GUI {

	protected Shell shell;
	private Button btnRadioButton;
	private Button btnRadioButton_1;
	private Button btnRadioButton_2;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			SplashScreen splash = new SplashScreen(new ImageIcon("/home/pethja/workspace/verisure-ascii-node/verisure.jpg"));
			splash.setLocationRelativeTo(null);
			splash.setVisible(true);
			//Toolkit.getDefaultToolkit().getScreenSize();
			
			for(int i = 0; i<100; i++){
				splash.setProgress(i);
				Thread.sleep(25);
			}
			
			splash.setVisible(false);

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
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(541, 301);
		shell.setText("Verisure ASCII Node");
		shell.setLayout(new GridLayout(5, false));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button logButton = new Button(shell, SWT.NONE);
		logButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				
				
				
			}
		});
		logButton.setToolTipText("Get the latest measurement from device.");
		GridData gd_logButton = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_logButton.heightHint = 41;
		logButton.setLayoutData(gd_logButton);
		logButton.setText("Get latest measurement");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		btnRadioButton_2 = new Button(shell, SWT.RADIO);
		btnRadioButton_2.setText("base64");
		
		btnRadioButton_1 = new Button(shell, SWT.RADIO);
		btnRadioButton_1.setText("hex");
		
		btnRadioButton = new Button(shell, SWT.RADIO);
		btnRadioButton.setText("ascii");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button delButton = new Button(shell, SWT.NONE);
		delButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
			}
		});
		delButton.setToolTipText("Deletes the measurements from device.");
		delButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		delButton.setText("Delete measurements");

	}

}
