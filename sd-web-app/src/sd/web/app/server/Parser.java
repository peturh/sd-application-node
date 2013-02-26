package sd.web.app.server;

import java.util.ArrayList;

public class Parser {

    public String rawData(ArrayList<String> nodeFaults){
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
		return hexValue.toString();
    }

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
			for (int j = 0; j < hex.length(); j += 2) {
				String str = hex.substring(j, j + 2);
				if (!str.equals("0d") && !str.equals("3f")) {
					hexValue.append(str);
					// hexToText.append((char) Long.parseLong(str, 16));
				}
			}
		}
		int position = hexValue.lastIndexOf("82");
		System.out.println("hexvalue: " + hexValue);

		String bloodValue = hexValue.substring(position + 4, position + 10);
		long blood1 = Long.decode("0x" + bloodValue.substring(0, 2));
		long blood2 = Long.decode("0x" + bloodValue.substring(2, 4));
		long blood3 = Long.decode("0x" + bloodValue.substring(4, 6));

		System.out.println("Värdet på blood1 - 3 är: " + blood1 + " " + blood2
				+ " " + blood3);
		String base64Value = String.valueOf(base64lite_dec((int) blood1,
				(int) blood2, (int) blood3));

		return "Senast mätta blodvärde är: " + Double.parseDouble(base64Value)
				/ 10 + " g/L";
	}
	
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
		
		return hexToText.toString();
	}
    
    
    
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

		// Put the message intop the array
		// Change start position of the string
		// Delete message that we gathered from it

		// Create size of array

		// Traverse through arraylist to put it into array.

		return lastTextMessage;

	}
    
	public static long base64lite_dec(int... numbers) {
		long tmp; // unsigned short tmp;
		tmp = numbers[0] & 0x3F;// tmp = *psrc & 0x3f;
		tmp += (numbers[1] & 0x3f) << 6; // tmp += ((unsigned short)(*(psrc + 1)
											// & 0x3f)) << 6;
		tmp += (numbers[2] & 0x3f) << 12; // tmp += ((unsigned short)(*(psrc +
											// 2) & 0x3f)) << 12;
		return tmp;
	}
}

