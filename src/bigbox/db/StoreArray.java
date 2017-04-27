package bigbox.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import bigbox.business.Facility;
import bigbox.business.Store;

public class StoreArray extends Facility implements StoreDAO{
	
	private ArrayList<Store> stores = null;
	private Path storesPath = null;
	private File storesFile = null;
	final String FIELD_SEP = "\t";

	public StoreArray(){
		storesPath = Paths.get("stores.txt");
		storesFile = storesPath.toFile();
		stores = this.getAllStores();
	}
	
/*	public static Store getStoreByDivisionStore(String inDiv, String inStore) {
		Store store=null;
		if (inDiv.equals("001")) {
			if (inStore.equals("00011")) {
				store = new Store(1,"001","00011",25000,"Mason BigBox","5711 Fields Ertel Rd.", "Mason", "OH","45249");
			}
			else if (inStore.equals("00255")) {
				store = new Store(2,"001","00255",27500,"Downtown BigBox","9330 Main St.", "Cincinnati", "OH","45202");
			}
			else if (inStore.equals("00172")) {
				store = new Store(3,"001","00172",32555.55,"Goshen BigBox","6777 Goshen Rd.", "Goshen", "OH","45122");
			}
			else if (inStore.equals("00075")) {
				store = new Store(4,"001","00075",21425.37,"Bridgetown BigBox","3888 Race Rd.", "Cincinnati", "OH","45211");
			}
			
		}
		else if (inDiv.equals("111")) {
			if (inStore.equals("00001")) {
				store = new Store(5,"111","00001",14432.77,"Louisville BigBox","1111 Washington St.", "Louisville", "KY","40206");
			}
			else if (inStore.equals("00044")) {
				store = new Store(6,"111","00044",17555.11,"Lawrenceburg BigBox","8000 Liberty St.", "Louisville", "KY","40204");
			}
		}
		
		return store;
	}
*/
	
	@Override
	public boolean addStore(Store inStore) {
		if(inStore.getId()==-1)
			inStore.setId(getNextID());
		stores.add(inStore);
		return this.saveStores();
	}

	private int getNextID() {
	int maxID = 1;
	for(Store s:stores){
		maxID = Math.max(maxID, s.getId());
	}
	return maxID++;
	}

	@Override
	public boolean deleteStore(Store inStore) {
		stores.remove(inStore);	
		return this.saveStores();
	}

	private boolean saveStores() {
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(storesFile)))){
			for(Store s:stores){
				out.print(s.getId()+FIELD_SEP);
				out.print(s.getDivisionnumber()+FIELD_SEP);
				out.print(s.getStorenumber()+FIELD_SEP);
				out.print(s.getSales()+FIELD_SEP);
				out.print(s.getName()+FIELD_SEP);
				out.print(s.getAddress()+FIELD_SEP);
				out.print(s.getCity()+FIELD_SEP);
				out.print(s.getState()+FIELD_SEP);
				out.println(s.getZipcode());
			}
		}
			catch (IOException e){
				System.out.println(e);
				return false;
			}
		
		return true;
	}

	@Override
	public Store getStoreByDivisionAndStoreNumber(String indiv, String inStoreNbr) {
		for (Store s: stores){
			if(s.getDivisionnumber().equalsIgnoreCase(indiv)){
				if(s.getStorenumber().equalsIgnoreCase(inStoreNbr))
					return s;
			}
		}
		
		return null;
	}

	@Override
	public ArrayList<Store> getAllStores() {
		// checking to see if it's been read before
				if (stores != null)
					return stores;

				stores = new ArrayList<>();

				if (Files.exists(storesPath)) {
					try (BufferedReader in = new BufferedReader(new FileReader(storesFile))) {
						String line = in.readLine();
						while (line != null) {
							String[] columns = line.split(FIELD_SEP);
							String id = columns[0];
							String storen = columns[1];
							String divn = columns[2];
							String sales = columns[3];
							String name = columns[4];
							String address = columns[5];
							String city = columns[6];
							String state = columns[7];
							String zip = columns[8];
							
							Store s = new Store(Integer.parseInt(id),divn,storen,Double.parseDouble(sales),name,address,city,state,zip);
							stores.add(s);
							
							line = in.readLine();
						}
					} catch (IOException e) {
						System.out.println(e);
						return null;
					}
				}
				return stores;
	}

	@Override
	public ArrayList<Store> getAllStoresByDivision(String inDiv) {
 
		ArrayList<Store> storesByDiv = new ArrayList<>();
		for (Store s: stores){
			if(s.getDivisionnumber().equalsIgnoreCase(inDiv))
				storesByDiv.add(s);
		}
		return storesByDiv;
	}

	@Override
	public boolean updateStore(Store inStore, String updateObject, String updatedInfo) {
		for (Store s : stores) {
			
			if ((s.getStorenumber().equalsIgnoreCase(inStore.getStorenumber()))&&(s.getDivisionnumber().equalsIgnoreCase(inStore.getDivisionnumber()))) {
				
				if (updateObject.equalsIgnoreCase("store number"))
					inStore.setStorenumber(updatedInfo);
				else if (updateObject.equalsIgnoreCase("div number")||updateObject.equalsIgnoreCase("div")||updateObject.equalsIgnoreCase("division"))
					inStore.setDivisionnumber(updatedInfo);
				else if (updateObject.equalsIgnoreCase("sales"))
					inStore.setSales(Double.parseDouble(updatedInfo));
				else if (updateObject.equalsIgnoreCase("name"))
					inStore.setName(updatedInfo);
				else if (updateObject.equalsIgnoreCase("address"))
					inStore.setAddress(updatedInfo);
				else if (updateObject.equalsIgnoreCase("city"))
					inStore.setCity(updatedInfo);
				else if (updateObject.equalsIgnoreCase("state"))
					inStore.setState(updatedInfo);
				else if (updateObject.equalsIgnoreCase("zip")||updateObject.equalsIgnoreCase("zip-code")||updateObject.equalsIgnoreCase("zip code")||updateObject.equalsIgnoreCase("zipcode"))
					inStore.setZipcode(updatedInfo);
			}
		}
		return this.saveStores();
	}
}
