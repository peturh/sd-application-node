import database.Database;



public class dbtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Database db = new Database();
		db.openConnection("dm","654321");
		db.doStatement("select * from shard03.event_log where installation_id = 5768");
		
		db.closeConnection();
		

	}

}
