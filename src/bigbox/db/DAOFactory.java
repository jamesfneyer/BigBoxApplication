package bigbox.db;

public class DAOFactory {
	public static StoreDAO getStoreDAO(){
		
		StoreDAO mc = new StoreArray();
		return mc;
	}
}
