package bigbox.storesales.db;

import java.util.ArrayList;

import bigbox.business.StoreSales;
import neyer.db.DBException;

public interface StoreSalesReader {
	double getSalesForDivision(String divisionNumber) throws DBException;

	double getWeeklySalesForStore(StoreSales s) throws DBException;

	ArrayList<StoreSales> getStoreSales(int storeID) throws DBException;

	StoreSales getStoreSales(int storeID, int year, int week) throws DBException;

}
