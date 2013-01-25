import database.Database;




public class dbtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Database db = new Database();
		db.openConnection("dm","654321");
		
		//db.doStatement("select * from dm_red.installation where installation_id = 5768");
		
		long t= System.currentTimeMillis();
		long end = t+60000;
		while(System.currentTimeMillis() < end) {
		  db.doStatement("select * from shard03.event_log where installation_id = 5768");
		  
		  try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		db.closeConnection();
		

	}

}
