import database.Database;

public class dbtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Database db = new Database();
		//db.openConnection("dm","654321");
		db.openConnection("root","1234");
		long t= System.currentTimeMillis();
		long end = t+60000;
		while(System.currentTimeMillis() < end) {
	//	db.doStatement("select * from shard03.node_faults where installation_id = 5768");
			//db.doStatement("select * from test.dev");
			System.out.print(db.testStatement("select * from test.dev"));
		  try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		}
		
		db.closeConnection();
		

	}

}