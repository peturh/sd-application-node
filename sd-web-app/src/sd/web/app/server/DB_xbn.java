package sd.web.app.server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
//import sd.web.app.client.MessageData;

public class DB_xbn extends DB_Connection {

	private int latest;
	private int seq;
	private String ack;
	private boolean first;
	private String timeDB;
private String timeGW;
	
	public DB_xbn() {

		try {
			String query = "select * from shard03.node_faults where installation_id = 5778";

			Connection conn = getConn();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);
			result.last();

			latest = Integer.valueOf(result.getString(4));
			System.out.println("Värdet på latest när vi skapar konstruktorn: "+latest);
			seq = 0;
			ack = "";
			timeDB="";
			timeGW="";
			first = true;
			conn.close();
			result.close(); 

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String firstAck(String text) {
		return text;

	}

	public boolean getAck(String theText, RestClient rest) {

		String query = "select * from shard03.node_faults where installation_id = 5778";

	
		try {
			
			Connection conn = getConn();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);
			result.last();
			ack = result.getString(9);
			seq = Integer.valueOf(result.getString(4));
			System.out.println("Gjorde precis ett databasanrop och kollar om:  '"+theText+"' har kommit fram.");
			System.out.println("Sekvensnummret i databasen är: "+seq+" och latest är: "+latest);
			timeDB = result.getString(5);
			timeGW = result.getString(3);
			System.out.println("Tiden i gw är: "+timeGW+". Tiden i db är: "+timeDB);
			//System.out.println("Skriver ut sista värdet i nodefaults: " + ack
				//	+ " och seq: " + seq + " och latest: " + latest);

			if (ack.equals("1094929235") && latest == (seq - 1)) {
				latest = seq;
				System.out
						.println("Lyckat: '"+theText+"' kom fram. Sekvensnummret är: "+seq+" och latest är: "+latest);
				return true;
			}

			else {
				System.out.println("Misslyckat: '"+theText+"' kom inte fram. Inne i omsändning och seq är: " + seq
						+ " och latest är: " + latest);

				while (latest >= seq) {
					rest.doPut("ÉÉÉ");
					Thread.sleep(2000);
					rest.doPut(theText);
					Thread.sleep(3000);
					result = select.executeQuery(query);
					result.last();
					ack = result.getString(9);
					seq = Integer.valueOf(result.getString(4));
					System.out.println("Inne i while för omsändning: seq är: " + seq
							+ " och latest är: " + latest);
				}
				System.out.println("Färdigt! '"+theText+"' har kommit fram till nod och det har blivit ackat. \n");
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

	public String retrieveMessage() {
		String query = "select * from shard03.node_faults where installation_id = 5778";
		ArrayList<String> nodeFaults = null;
		String messageData = "";

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
			// System.out.println("Decimaler: " + nodeFaults.toString());

			messageData = getMessages(nodeFaults);

			result.close();
			// System.out.println("Stänger result");
			conn.close();
			// System.out.println("Stänger conn");
		} catch (Exception e) {
			// System.err.print("My SQL error " + query);
			e.printStackTrace();
		}
		return messageData;
	}

	private boolean deleteMessages() {
		String query = "delete from shard03.node_faults where installation_id = 5778";

		try {
			Connection conn = getConn();
			Statement select = conn.createStatement();
			ResultSet result = select.executeQuery(query);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getMessages(ArrayList<String> nodeFaults) {
		// ArrayList<String> textFaults = new ArrayList<String>();
		String hex = "";
		String acks = "1094929235";
		
		StringBuilder hexToText = new StringBuilder();
		StringBuilder hexValue = new StringBuilder();
		for (int i = 0; i < nodeFaults.size(); i++) {

			// Kollar om nodefaults är ett ACK, isåfall skriver vi inte ut den i
			// receive rutan.
			System.out.println("Decimalvärdet är: "+nodeFaults.get(i));
			
			if (!nodeFaults.get(i).equals(acks)) {
				hex = Long.toHexString(Long.parseLong(nodeFaults.get(i)));
				if(hex.length() == 7){
					hex = "0"+hex;
				}
				// System.out.println("Skriver ut hex: " + hex);
				hexValue.append(hex);
				System.out.println("Såhär långt har vi hexat om decen: "+hexToText.toString());
				
				/**
				 * The code for representing hex as ascii.
				 */
			}
		
				
				for (int j = 0; j < hex.length(); j += 2) {
					System.out.println("Den här fixas just nu "+hex);
					String str = hex.substring(j, j + 2);
					System.out.println("Bokstaven är: " + str);	
					if(!str.equals("0d")&&!str.equals("3f")){
						hexValue.append(str);
						//hexToText.append((char) Long.parseLong(str, 16));
					}	
				}
	
				

		}

		
	//	type filter texttype filter text
		// System.out.println("Texten i hexToText; " + hexToText.toString());

		// String mess = getLastMessageData(hexToText.toString());

		
			int position = hexValue.lastIndexOf("82");
			System.out.println("hexvalue: "+hexValue);
			
			String bloodValue = hexValue.substring(position+4, position+10);
			long blood1 = Long.decode("0x"+bloodValue.substring(0,2));
			long blood2 = Long.decode("0x"+bloodValue.substring(2,4));
			long blood3 =  Long.decode("0x"+bloodValue.substring(4,6));
			
			
			String base64Value = String.valueOf(base64lite_dec((int)blood1,(int)blood2,(int)blood3));
		return "Senast mätta blodvärde är: "+base64Value+" g/L";
		
		

	}
	public static long base64lite_dec(int... numbers) {
        long tmp; //unsigned short tmp;
        tmp = numbers[0] & 0x3F;//tmp = *psrc & 0x3f;
        tmp += (numbers[1] & 0x3f) << 6; //tmp += ((unsigned short)(*(psrc + 1) & 0x3f)) << 6;
        tmp += (numbers[2] & 0x3f) << 12; //tmp += ((unsigned short)(*(psrc + 2) & 0x3f)) << 12;
        return tmp;
    }

	private String getLastMessageData(String message) {

		// ArrayList<String> temp = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(message);

		int separator = 0;
		// int start = 0;
		String lastTextMessage = "";

		// Find the separator ';'

		separator = sb.lastIndexOf(":");
		if (separator == -1) {
			return "Inget meddelande att hämta";
		}
		System.out.println("Värdet på separator: " + separator);
		// Get the message into a String

		lastTextMessage = sb.substring(separator + 1, message.length());

		// Put the message intop the array
		// Change start position of the string
		// Delete message that we gathered from it

		// Create size of array

		// Traverse through arraylist to put it into array.

		return lastTextMessage;

	}

}
