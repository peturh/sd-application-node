package backend;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//import sd.web.app.client.MessageData;

public class XBNConnection extends DbConnection {

	private int latest;
	private int seq;
	private String ack;
	private String timeDB;
	private String timeGW;

	/**
	 * The connection to the database. Initiate sequence number from the
	 * database, start different variables.
	 */
	public XBNConnection() {

		try {
			System.out.println("Lyckades skapa XBNConnection");
			String query = "select * from shard03.node_faults where installation_id = 5778";

			Connection conn = getConn();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);
			result.last();

			latest = Integer.valueOf(result.getString(4));
			System.out.println("Värdet på latest när vi skapar konstruktorn: "
					+ latest);
			seq = 0;
			ack = "";
			timeDB = "";
			timeGW = "";
			conn.close();
			result.close();
			System.out.println("Stängde db");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * Accesses the db and requests to get the column node_faults 
	 * @param the type of measurement wanted.
	 * @return the String desired, Raw hex data, ascii representation or blood value decoded in base64lite
	 */
	public String getNodeFaults(int type) {
		String query = "select * from shard03.node_faults where installation_id = 5778";
		ArrayList<String> nodeFaults = null;
		String text = "";

		try {
			Connection conn = getConn();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);
			nodeFaults = new ArrayList<String>();
			// int resultSize = getResultSetSize(result); //size the array
			// int i = 0;
			while (result.next()) {
				nodeFaults.add(result.getString(9));
			}
			
			//Parse out the latest blooddata in the nodeFaults
			
			//text = Parser.getBloodValue(nodeFaults);
			//To be able to get the ASCII values from the nodefaults (warning base64 encoded characters will not be decoded).
			//text = Parser.getText(nodeFaults);
			
			//To be able to show the raw hexdata
			
			switch(type){
			case 0 : text = Parser.getBloodValue(nodeFaults);
			break;
			case 1 : text = Parser.getText(nodeFaults);
			break;
			case 2: text = Parser.getRawData(nodeFaults);
			break;
			}
			
			result.close();
			conn.close();
			System.out.println("Stänger conn");
		} catch (Exception e) {
			// System.err.print("My SQL error " + query);
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * A function for determine if the bytes sent to the device really has
	 * gotten there. A one sided retransmission.
	 * 
	 * @param theText
	 *            is the 3 byte chunk that should be resent if no ACK is present
	 * @param rest
	 *            is the Rest client that should be used to send the 3 byte chunk
	 *            if no ACK is present.
	 * @return true if the ACK is found in the database.
	 */
	public boolean getAck(String theText, Rest rest) {

		String query = "select * from shard03.node_faults where installation_id = 5778";

		try {
			System.out.println("Sover i en sekund i db");
			Thread.sleep(1000);
			Connection conn = getConn();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);
			result.last();
			ack = result.getString(9);
			seq = Integer.valueOf(result.getString(4));
			System.out
					.println("Gjorde precis ett databasanrop och kollar om:  '"
							+ theText + "' har kommit fram.");
			System.out.println("Sekvensnummret i databasen är: " + seq
					+ " och latest är: " + latest);
			timeDB = result.getString(5);
			timeGW = result.getString(3);
			System.out.println("Tiden i gw är: " + timeGW + ". Tiden i db är: "
					+ timeDB);
			// System.out.println("Skriver ut sista värdet i nodefaults: " + ack
			// + " och seq: " + seq + " och latest: " + latest);

			if (ack.equals("1094929235") && latest == (seq - 1)) {
				latest = seq;
				System.out.println("Lyckat: '" + theText
						+ "' kom fram. Sekvensnummret är: " + seq
						+ " och latest är: " + latest);
				return true;
			}

			else {
				System.out.println("Misslyckat: '" + theText
						+ "' kom inte fram. Inne i omsändning och seq är: "
						+ seq + " och latest är: " + latest);

				while (latest >= seq) {
					rest.doPut("ÉÉÉ");
					Thread.sleep(2000);
					rest.doPut(theText);
					Thread.sleep(3000);
					result = select.executeQuery(query);
					result.last();
					ack = result.getString(9);
					seq = Integer.valueOf(result.getString(4));
					System.out.println("Inne i while för omsändning: seq är: "
							+ seq + " och latest är: " + latest);
				}
				System.out
						.println("Färdigt! '"
								+ theText
								+ "' har kommit fram till nod och det har blivit ackat. \n");
				latest = seq;
				return true;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Det gick inte att hämta ack, för att latest är: "
				+ latest + " och seq är: " + seq);
		return false;// Integer.toString(latest);

	}

	/**
	 * 
	 * 
	 * A method for deleting the node_faults section in the database. Can be functional if not access to SQL editor.
	 * 
	 * @return true if the messeges was deleted from the database
	 */
	public boolean deleteMessages() {
		String query = "delete from shard03.node_faults where installation_id = 5778";

		try {
			Connection conn = getConn();
			Statement select = conn.createStatement();
			boolean result = select.execute(query);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}



}