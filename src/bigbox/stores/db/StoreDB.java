package bigbox.stores.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bigbox.business.Store;
import neyer.db.DBException;
import neyer.db.DBUtil;

public class StoreDB implements StoreDAO {
	static Connection connection;

	public StoreDB() {
	}

	public ArrayList<Store> getAllStores() throws DBException {
		String sql = "select DivNumber, StoreNumber, s.StoreName, sum(sales), s.Address, s.City, s.State, s.ZipCode "
				+ "from stores s, store_sales ss, divisions d " + "where s.ID = ss.StoreID "
				+ "and d.ID = s.DivisionID " + "group by(StoreNumber);";
		ArrayList<Store> stores = new ArrayList<>();
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {

				stores.add(getStoreFromRow(rs));
			}
			rs.close();
			return stores;
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public int getStoreID(String storeNumber) throws DBException {
		String sql = "select ID, StoreNumber " + "from stores " + "where StoreNumber = ?;";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ArrayList<Store> stores = getAllStores();
			int p = 0;
			for (Store a : stores) {
				if (storeNumber.equalsIgnoreCase(a.getStorenumber())) {
					ps.setString(1, storeNumber);
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						p = rs.getInt("ID");
						rs.close();
						return p;

					} else {
						rs.close();
					}
				}
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		}
	}

	public Store get(String storeNumber) throws DBException {
		String sql = "select DivNumber, StoreNumber, s.StoreName, sum(sales), s.Address, s.City, s.State, s.ZipCode "
				+ "from stores s, store_sales ss, divisions d" + "where s.ID = ss.StoreID " + "and d.ID = s.DivisionID "
				+ "and StoreNumber = ?" + "group by(StoreNumber);";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, storeNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				/*
				 * long productId = rs.getLong(1); String description =
				 * rs.getString(3); double price = rs.getDouble(4); rs.close();
				 * 
				 * Product p = new Product(); p.setId(productId);
				 * p.setCode(productCode); p.setDescription(description);
				 * p.setPrice(price);
				 */
				Store p = getStoreFromRow(rs);
				rs.close();
				return p;

			} else {
				rs.close();
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		}
	}

	public ArrayList<Store> getDivStore(String divisionCode) throws DBException {
		String sql = "select DivNumber, StoreNumber, s.StoreName, sum(sales), s.Address, s.City, s.State, s.ZipCode "
				+ "from stores s, store_sales ss, divisions d " + "where s.ID = ss.StoreID "
				+ "and d.ID = s.DivisionID " + "and DivNumber = ? " + "order by(StoreNumber);";
		connection = DBUtil.getConnection();
		ArrayList<Store> stores = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, divisionCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				Store p = getStoreFromRow(rs);
				stores.add(p);

			} else {
				rs.close();
				return null;
			}
			rs.close();
			return stores;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e);
		}
	}

	public void add(Store store) throws DBException {
		String sql = "INSERT INTO Stores (DivisionID, StoreNumber, StoreName, Address, City, State, ZipCode) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, store.getDivisionID());
			ps.setString(2, store.getStorenumber());
			ps.setString(4, store.getName());
			ps.setString(5, store.getAddress());
			ps.setString(6, store.getCity());
			ps.setString(7, store.getState());
			ps.setString(8, store.getZipcode());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public void update(Store store) throws DBException {
		String sql = "UPDATE stores SET " 
				+ "StoreName = ?, " 
				+ "Address = ?, " 
				+ "City = ?, "
				+ "State = ?, " 
				+ "ZipCode = ? " 
				+ "where StoreNumber = ?";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, store.getName());
			ps.setString(2, store.getAddress());
			ps.setString(3, store.getCity());
			ps.setString(4, store.getState());
			ps.setString(5, store.getZipcode());
			ps.setString(6, store.getStorenumber());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	public void delete(Store product) throws DBException {
		connection = DBUtil.getConnection();
		int storeID = getStoreID(product.getStorenumber());

		String sq2 = "DELETE FROM store_sales" + "WHERE StoreID = ?";
		try (PreparedStatement ps = connection.prepareStatement(sq2)) {
			ps.setInt(1, storeID);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		String sql = "DELETE FROM Stores " + "WHERE StoreNumber = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, product.getStorenumber());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	private Store getStoreFromRow(ResultSet rs) throws SQLException, DBException {
		String sql = "select DivNumber, StoreNumber, s.StoreName, sum(sales), s.Address, s.City, s.State, s.ZipCode "
				+ "from stores s, store_sales ss, divisions d " + "where s.ID = ss.StoreID "
				+ "and d.ID = s.DivisionID " + "group by(StoreNumber);";
		connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			String storeNumber = rs.getString("StoreNumber");
			String divNumber = rs.getString("DivNumber");
			double sales = rs.getDouble("sum(sales)");
			String StoreName = rs.getString("StoreName");
			String Address = rs.getString("Address");
			String City = rs.getString("City");
			String State = rs.getString("State");
			String ZipCode = rs.getString("ZipCode");
			Store p = new Store();
			p.setDivisionnumber(divNumber);
			p.setStorenumber(storeNumber);
			p.setSales(sales);
			p.setName(StoreName);
			p.setAddress(Address);
			p.setCity(City);
			p.setState(State);
			p.setZipcode(ZipCode);
			return p;

		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

}