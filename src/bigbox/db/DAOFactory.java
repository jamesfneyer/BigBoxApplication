package bigbox.db;

import bigbox.division.db.DivisionDAO;
import bigbox.division.db.DivisionDB;
import bigbox.stores.db.*;
import bigbox.storesales.db.StoreSalesDAO;
import bigbox.storesales.db.StoreSalesDB;

public class DAOFactory {
	public static StoreDAO getStoreDAO() {

		StoreDAO mc = new StoreDB();
		return mc;
	}

	public static StoreSalesDAO getStoreSalesDAO() {

		StoreSalesDAO mc = new StoreSalesDB();
		return mc;
	}

	public static DivisionDAO getDivisionsDAO() {

		DivisionDAO mc = new DivisionDB();
		return mc;
	}
}
