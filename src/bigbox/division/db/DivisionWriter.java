package bigbox.division.db;

import bigbox.business.Division;
import neyer.db.DBException;

public interface DivisionWriter {
	void addDivision(Division division) throws DBException;

}
