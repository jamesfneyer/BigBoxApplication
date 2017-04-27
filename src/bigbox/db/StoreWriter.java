package bigbox.db;

import bigbox.business.Store;

public interface StoreWriter {
	boolean addStore(Store inStore);
	boolean deleteStore(Store inStore);
	boolean updateStore(Store inStore, String updateObject, String updatedInfo);
}
