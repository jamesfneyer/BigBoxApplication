package bigbox.db;

import java.util.ArrayList;

import bigbox.business.Store;

public interface StoreReader {
	Store getStoreByDivisionAndStoreNumber(String indiv, String inStoreNbr);
	ArrayList<Store>getAllStores();
	ArrayList<Store>getAllStoresByDivision(String inDiv);
}
