package bigbox.division.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bigbox.business.Division;
import neyer.db.DBException;
import neyer.db.DBUtil;

public class DivisionDB implements DivisionDAO {
	static Connection connection;

	public DivisionDB() {
	}

	public ArrayList<Division> getAllDivisions() throws DBException {
		String sql = "select * " + "from divisions;";
		ArrayList<Division> divisions = new ArrayList<>();
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				divisions.add(getDivisionFromRow(rs));
			}
			rs.close();
			return divisions;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public Division getDivision(String divNumber) throws DBException {
		String sql = "select * " + "from divisions;";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				Division d = getDivisionFromRow(rs);
				rs.close();
				return d;
			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public int getDivisionID(String divNumber) throws DBException {
		String sql = "select ID, DivNumber " + "from divisions " + "where DivNumber = ?;";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ArrayList<Division> divisions = getAllDivisions();
			int p = 0;
			for (Division d : divisions) {
				if (d.getDivNumber().equalsIgnoreCase(divNumber)) {
					ps.setString(1, divNumber);
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						p = rs.getInt("ID");
						rs.close();
					} else
						rs.close();
				}
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		}
	}

	public void addDivision(Division division) throws DBException {
		String sql = "INSERT INTO divisions (DivNumber, StoreName, Address, City, State, ZipCode) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, division.getDivNumber());
			ps.setString(2, division.getName());
			ps.setString(3, division.getAddress());
			ps.setString(4, division.getCity());
			ps.setString(5, division.getState());
			ps.setString(5, division.getZipcode());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	private Division getDivisionFromRow(ResultSet rs) throws SQLException, DBException {
		String sql = "select * " + "from divisions " + "where DivNumber = ?";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			String DivNumber = rs.getString("DivNumber");
			String StoreName = rs.getString("StoreName");
			String Address = rs.getString("Address");
			String City = rs.getString("City");
			String State = rs.getString("State");
			String ZipCode = rs.getString("ZipCode");

			Division d = new Division();
			d.setDivNumber(DivNumber);
			d.setName(StoreName);
			d.setAddress(Address);
			d.setCity(City);
			d.setState(State);
			d.setZipcode(ZipCode);
			return d;

		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

}
