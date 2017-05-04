package bigbox.division.db;

import java.util.ArrayList;

import bigbox.business.Division;
import neyer.db.DBException;

public interface DivisionReader {
	ArrayList<Division> getAllDivisions() throws DBException;

	Division getDivision(String divNumber) throws DBException;

	int getDivisionID(String divNumber) throws DBException;

}
