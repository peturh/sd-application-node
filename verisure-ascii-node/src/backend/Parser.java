package backend;

import java.util.ArrayList;

/**
 * @author pethja
 *
 */
public class Parser {
	
	/*
	* Returns the decimal messages in the database to raw hexadecimal data.  
	*
	*
	*/

    public static String getRawData(ArrayList<String> nodeFaults){
    		String hex = "";
		String acks = "1094929235";
		StringBuilder hexValue = new StringBuilder();
		
		for (int i = 0; i < nodeFaults.size(); i++) {

			if (!nodeFaults.get(i).equals(acks)) {
				hex = Long.toHexString(Long.parseLong(nodeFaults.get(i)));
				if (hex.length() == 7) {
					hex = "0" + hex;
				}
				hexValue.append(hex);
			}		
		}
		System.out.println(hexValue.toString());
		return hexValue.toString();
    }

	/**
	 * A function for acquire the base64 encoded blood data
	 * 
	 * @param nodeFaults is the arraylist with all the values from the database represented in 4 bytes decimal form
	 * @return the bloodvalue taken from the measurement device.
	 */
	public static String getBloodValue(ArrayList<String> nodeFaults) {
		// ArrayList<String> textFaults = new ArrayList<String>();
		String hex = "";
		String acks = "1094929235";
		StringBuilder hexValue = new StringBuilder();
		
		for (int i = 0; i < nodeFaults.size(); i++) {

			if (!nodeFaults.get(i).equals(acks)) {
				hex = Long.toHexString(Long.parseLong(nodeFaults.get(i)));
				if (hex.length() == 7) {
					hex = "0" + hex;
				}
				hexValue.append(hex);
			}
			
		}
		
		int position = hexValue.lastIndexOf("82");
		String hemocuePacket = hexValue.substring(position + 4, position + 22);
		System.out.println("The raw hexdata: " + hexValue);
		System.out.println(" The base64 encoded data: "+hemocuePacket);
		
		
		long blood1 = Long.decode("0x" + hemocuePacket.substring(0, 2));
		long blood2 = Long.decode("0x" + hemocuePacket.substring(2, 4));
		long blood3 = Long.decode("0x" + hemocuePacket.substring(4, 6));

		long bytes[] = new long[6];
		 
		bytes[0] = Long.decode("0x" + hemocuePacket.substring(6,8));
		bytes[1] = Long.decode("0x" + hemocuePacket.substring(8,10));
		bytes[2] = Long.decode("0x" + hemocuePacket.substring(10,12));
		bytes[3] = Long.decode("0x" + hemocuePacket.substring(12,14));
		bytes[4] = Long.decode("0x" + hemocuePacket.substring(14,16));
		bytes[5] = Long.decode("0x" + hemocuePacket.substring(16,18));

		for(int i=0; i<bytes.length;i++)
		System.out.println("Värdet på platsen "+i+" är " +bytes[i]);
		
		String dateValue = dateParser(bytes);
		
		System.out.println("Värdet på blood1 - 3 är: " + blood1 + " " + blood2
				+ " " + blood3);
		double base64Value = base64lite_dec((int) blood1,
				(int) blood2, (int) blood3);
	
		return "Senast mätta blodvärde är: " + String.valueOf(base64Value)+ " g/L " + dateValue;
	}
	/*
	 * 
	 * This method will take values from a long vector and parse out the dates.
	 * 
	 * @param long byte vector
	 * @return the String with the date and time.
	 */
	
public static String dateParser(long ...bytes){
	
	
		long value2 = base64lite_dec((int)bytes[0],(int)bytes[1],(int)bytes[2]);
		long value3 = base64lite_dec((int)bytes[3],(int)bytes[4],(int)bytes[5]);
		value2 = value2 | (value3 << 16);
		
		System.out.println(value2);
		System.out.println(value3);
		long seconds = value2 & 0x3f;
		System.out.println("Seconds: " +seconds);
		value2= value2 >> 6;
		long minutes = value2 & 0x3f;
		System.out.println("Minutes: "+minutes);
		value2= value2 >> 6;
		long hour = value2 & 0x1f;
		System.out.println("Hour "+hour);
		value2= value2 >> 5;
		long day = value2 & 0x1f;
		System.out.println("Day "+day);
		value2= value2 >> 5;
		long month = value2 & 0x0f;
		System.out.println("Month "+month);
		value2= value2 >> 4;
		long year = value2 & 0x3f;
		System.out.println("Year "+year);
		return "Time: "+String.valueOf(hour)+"-"+String.valueOf(minutes)+"-"+String.valueOf(seconds)+". Date: "+String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day);
	
}

	
	/**
	 * A method for getting the data from the node_faults in ASCII form. 
	 * 
	 * !!!Warning, the database may have base64 encoded character which will show corrupt messages.!!!
	 * 
	 * @param nodeFaults is the arraylist with all the values from the database represented in 4 bytes decimal form
	 * @return The ASCII representation of the text from nodefaults.
	 */
	 
	public static String getText(ArrayList<String> nodeFaults){
		StringBuilder hexToText = new StringBuilder();
		String hex = "";
		String acks = "1094929235";

		for (int i = 0; i < nodeFaults.size(); i++) {

			if (!nodeFaults.get(i).equals(acks)) {
				hex = Long.toHexString(Long.parseLong(nodeFaults.get(i)));
				if (hex.length() == 7) {
					hex = "0" + hex;
				}
			}
			for (int j = 0; j < hex.length(); j += 2) {
				String str = hex.substring(j, j + 2);
				if (!str.equals("0d") && !str.equals("3f")) {
					hexToText.append((char) Long.parseLong(str, 16));
				}
			}
		}	
		System.out.println(hexToText);
		return hexToText.toString();
	}
	
	/*
	*
	* Decodes the base64lite value 
	*@param three ints 
	*@return the bloodvalue 
	*/
	
	public static long base64lite_dec(int... numbers) {
	long tmp; // unsigned short tmp;
	tmp = numbers[0] & 0x3F;// tmp = *psrc & 0x3f;
	tmp += (numbers[1] & 0x3f) << 6; // tmp += ((unsigned short)(*(psrc + 1)											// & 0x3f)) << 6;
	tmp += (numbers[2] & 0x3f) << 12; // tmp += ((unsigned short)(*(psrc +										// 2) & 0x3f)) << 12;
	// The value from the device is 10 times larger then expected value
	System.out.println("tmp är "+tmp);
	return tmp;
	
	}
    
    
    
    /**
     * @deprecated because the functionality of the CC1110 is not correctly implemented to function with such data
     * 
     * @param the whole message from the db
     * @return the last message found in the db with an :
     */
    private String getLastMessage(String message) {

		StringBuilder sb = new StringBuilder(message);

		int separator = 0;
		String lastTextMessage = "";


		separator = sb.lastIndexOf(":");
		if (separator == -1) {
			return "Inget meddelande att hämta";
		}
		System.out.println("Värdet på separator: " + separator);
		// Get the message into a String

		lastTextMessage = sb.substring(separator + 1, message.length());

		return lastTextMessage;

	}
}

