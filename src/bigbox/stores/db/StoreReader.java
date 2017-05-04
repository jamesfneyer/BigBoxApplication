package bigbox.stores.db;

import java.util.ArrayList;

import bigbox.business.Store;
import neyer.db.DBException;

public interface StoreReader {

	Store get(String storeNumber) throws DBException;

	ArrayList<Store> getDivStore(String divisionCode) throws DBException;

	int getStoreID(String storeNumber) throws DBException;

	ArrayList<Store> getAllStores() throws DBException;

}
