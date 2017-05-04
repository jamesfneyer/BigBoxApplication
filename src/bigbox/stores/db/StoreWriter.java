package bigbox.stores.db;

import bigbox.business.Store;
import neyer.db.DBException;

public interface StoreWriter {

	void add(Store store) throws DBException;

	void update(Store store) throws DBException;

	void delete(Store product) throws DBException;
}
