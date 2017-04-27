package neyer.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bigbox.business.Store;

public class StoreDB {
    static Connection connection;
    public static ArrayList<Store> getAll() throws DBException {        
        String sql = "SELECT * FROM Stores ORDER BY ID";
        ArrayList<Store> stores = new ArrayList<>();
        connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                
                stores.add(getStoreFromRow(rs));
            }
            rs.close();
            return stores;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static Store get(String storeNumber) throws DBException {
        String sql = "SELECT * FROM Stores WHERE StoreNumber = ?";
        connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, storeNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	/*long productId = rs.getLong(1);
                String description = rs.getString(3);
                double price = rs.getDouble(4);
                rs.close();
                
                Product p = new Product();
                p.setId(productId);
                p.setCode(productCode);
                p.setDescription(description);
                p.setPrice(price);*/
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
    
    public static ArrayList<Store> getDivStore(String divisionCode) throws DBException {
        String sql = "SELECT * FROM Stores WHERE DivNumber = ?";
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


    public static void add(Store store) throws DBException {
        String sql
                = "INSERT INTO Stores (StoreNumber, DivNumber, Sales, StoreName, Address, City, State, ZipCode) "
                + "VALUES (?, ?, ?, ?,?, ?, ?, ?)";
        connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, store.getStorenumber());
            ps.setString(2, store.getDivisionnumber());
            ps.setDouble(3, store.getSales());
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

    public static void update(Store store) throws DBException {
        String sql = "UPDATE Stores SET "
                + "StoreNumber = ?, "
                + "DivNumber = ?, "
                + "Sales = ? "
                + "StoreName = ?, "
                + "Address = ?, "
                + "City = ? "
                + "State = ?, "
                + "ZipCode = ? "
                + "WHERE ProductID = ?";
        connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
        	ps.setString(1, store.getStorenumber());
            ps.setString(2, store.getDivisionnumber());
            ps.setDouble(3, store.getSales());
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
    
    public static void delete(Store product) 
            throws DBException {
        String sql = "DELETE FROM Stores "
                   + "WHERE StoreNumber = ?";
        connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getStorenumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
    
    private static Store getStoreFromRow(ResultSet rs) throws SQLException, DBException{
    	String sql = "SELECT * FROM Stores WHERE StoreNumber = ?";
        connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            
        	
        		String storeNumber = rs.getString("StoreNumber");
                int productId = rs.getInt("ID");
                String divNumber = rs.getString("DivNumber");
                double sales = rs.getDouble("Sales");
                String StoreName = rs.getString("StoreName");
                String Address = rs.getString("Address");
                String City = rs.getString("City");
                String State = rs.getString("State");
                String ZipCode = rs.getString("ZipCode");
                Store p = new Store();
                p.setId(productId);
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