package sd.web.app.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//import sd.web.app.client.MessageData;

public class DB_xbn extends DB_Connection {

	public DB_xbn(){}
	
	public String retrieveMessage(){
		String query = "select * from shard03.node_faults where installation_id = 5778";
		ArrayList<String> nodeFaults = null;
		String messageData = "";

		try {
			 Connection conn = getConn();
			 Statement select = conn.createStatement();
             ResultSet result = select.executeQuery(query);
             nodeFaults = new ArrayList<String>();
           //  int resultSize = getResultSetSize(result); //size the array
           //  int i = 0;
             while(result.next()){
               nodeFaults.add(result.getString(9));
             }
             System.out.println("Decimaler: " +nodeFaults.toString());

             messageData = getMessages(nodeFaults);
             
             result.close();
             System.out.println("Stänger result");
             conn.close();
             System.out.println("Stänger conn");
		}
		catch(Exception e){
			//System.err.print("My SQL error " + query);
			e.printStackTrace();
		}
		return messageData;
	}
	
	private String getMessages(ArrayList<String> nodeFaults){
		//ArrayList<String> textFaults = new ArrayList<String>();
		String hex = "";
		StringBuilder hexToText = new StringBuilder();
		
		for(int i = 0; i<nodeFaults.size(); i++){
			
			hex = Integer.toHexString(Integer.parseInt(nodeFaults.get(i)));
			System.out.println("Skriver ut hex: " +hex);
			
			for (int j = 0; j < hex.length(); j+=2) {
			        String str = hex.substring(j, j+2);
			       // System.out.println("str i getMessages "+str);
			        hexToText.append((char)Integer.parseInt(str, 16));
			        //textFaults.add(hexToText.toString());
			       // System.out.println(hexToText.toString());
			        
			       // System.out.println("textFaults" + textFaults.toString());
			    }
			
			
		}
		
		System.out.println("Texten i hexToText; "+hexToText.toString());

		String mess = getLastMessageData(hexToText.toString());
		
		return mess;
		
		
	}
	
	private String getLastMessageData(String message){
		
		//ArrayList<String> temp = new ArrayList<String>();
		StringBuilder sb = new StringBuilder(message);
		
		int separator = 0;
	//	int start = 0;
		String lastTextMessage = "";
		

			//Find the separator ';'
		
			separator = sb.lastIndexOf(":");
			if(separator==-1){
				return "Inget meddelande att hämta";
			}
			System.out.println("Värdet på separator: "+separator);
			//Get the message into a String
			
			lastTextMessage = sb.substring(separator+1, message.length());
			
			//Put the message intop the array
			//Change start position of the string
			//Delete message that we gathered from it
			
		
		//Create size of array
		
		//Traverse through arraylist to put it into array.
	
		return lastTextMessage;
		
	}
	
}
