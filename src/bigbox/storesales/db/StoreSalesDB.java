package bigbox.storesales.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bigbox.business.StoreSales;
import bigbox.db.DAOFactory;
import bigbox.stores.db.StoreDAO;
import neyer.db.DBException;
import neyer.db.DBUtil;

public class StoreSalesDB implements StoreSalesDAO {
	static Connection connection;
	public StoreDAO stores;

	public StoreSalesDB() {
		stores = DAOFactory.getStoreDAO();
	}

	public ArrayList<StoreSales> getStoreSales(int storeID) throws DBException {
		String sql = "select * from store_sales " + "where " + "StoreID = ? ;";
		connection = DBUtil.getConnection();
		ArrayList<StoreSales> storeSales = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			ps.setInt(1, storeID);
			while (rs.next()) {
				storeSales.add(getSalesFromRow(rs));
			}
			rs.close();
			return storeSales;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public StoreSales getStoreSales(int storeID, int year, int week) throws DBException {
		String sql = "select * from store_sales " 
				+ "where " + "StoreID = ? " 
				+ "and Year = ? " 
				+ "and Week = ?;";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, storeID);
			ps.setInt(2, year);
			ps.setInt(3, week);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				StoreSales d = getSalesFromRow(rs);
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

	private StoreSales getSalesFromRow(ResultSet rs) throws SQLException, DBException {
		String sql = "select * " + "from store_sales;";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			int storeID = rs.getInt("StoreID");
			int year = rs.getInt("Year");
			int week = rs.getInt("Week");
			double sales = rs.getDouble("Sales");
			StoreSales d = new StoreSales();
			d.setStoreID(storeID);
			d.setYear(year);
			d.setWeek(week);
			d.setSales(sales);
			return d;

		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public void addStoreSales(StoreSales storeSales) throws DBException {
		String sql = "INSERT INTO store_sales (StoreID, Year, Week, Sales) " + "VALUES (?, ?, ?, ?)";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, storeSales.getStoreID());
			ps.setInt(2, storeSales.getYear());
			ps.setInt(3, storeSales.getWeek());
			ps.setDouble(4, storeSales.getSales());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public void update(StoreSales storeSales) throws DBException {
		String sql = "UPDATE store_sales SET "
				+ "Sales = ? " 
				+ "WHERE Year = ? " 
				+ "and Week = ? " + "and StoreID = ?";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(4, storeSales.getStoreID());
			ps.setInt(2, storeSales.getYear());
			ps.setInt(3, storeSales.getWeek());
			ps.setDouble(1, storeSales.getSales());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public double getSalesForDivision(String divisionNumber) throws DBException {
		String sql = "select d.DivNumber, sum(sales) " + "from stores s, store_sales ss, divisions d "
				+ "where s.ID = ss.StoreID " + "and d.ID = s.DivisionID " + "and divisionNumber = ?;";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, divisionNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int p = rs.getInt("StoreID");
				rs.close();
				return p;
			} else {
				rs.close();
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		}
	}

	public double getWeeklySalesForStore(StoreSales s) throws DBException {
		String sql = "select Year, Week, Sales " + "from store_sales ss " + "where ID = ? " + "and Year = ? "
				+ "and Week = ?";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, s.getStoreID());
			ps.setInt(2, s.getYear());
			ps.setInt(3, s.getWeek());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				double p = rs.getDouble("StoreID");
				rs.close();
				return p;

			} else {
				rs.close();
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		}
	}

	public void delete(StoreSales storeSales) throws DBException {
		connection = DBUtil.getConnection();
		String sq2 = "DELETE FROM store_sales " + "WHERE StoreID = ?";
		try (PreparedStatement ps = connection.prepareStatement(sq2)) {
			ps.setInt(1, storeSales.getStoreID());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public void deleteSalesWeek(StoreSales storeSales) throws DBException {
		connection = DBUtil.getConnection();
		String sq2 = "DELETE FROM store_sales " + "WHERE StoreID = ? " + "and Year = ? " + "and Week = ?;";
		try (PreparedStatement ps = connection.prepareStatement(sq2)) {
			ps.setInt(1, storeSales.getStoreID());
			ps.setInt(2, storeSales.getYear());
			ps.setInt(3, storeSales.getWeek());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
}
