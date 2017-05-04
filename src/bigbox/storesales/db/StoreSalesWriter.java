package bigbox.storesales.db;

import bigbox.business.StoreSales;
import neyer.db.DBException;

public interface StoreSalesWriter {
	void update(StoreSales storeSales) throws DBException;

	void addStoreSales(StoreSales storeSales) throws DBException;

	void delete(StoreSales storeSales) throws DBException;

	void deleteSalesWeek(StoreSales storeSales) throws DBException;
}
