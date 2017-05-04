package bigbox.presentation;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import bigbox.business.Division;
import bigbox.business.Store;
import bigbox.business.StoreSales;
import bigbox.db.*;
import bigbox.division.db.DivisionDAO;
import bigbox.stores.db.StoreDAO;
import bigbox.storesales.db.StoreSalesDAO;
import bigbox.util.*;
import neyer.db.*;

public class BigBoxApp {

	static StoreDAO stores = null;
	static DivisionDAO divisions = null;
	static StoreSalesDAO storeSales = null;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// print welcome message
		System.out.println("Welcome to the Big Box application");

		// Create the variables needed
		Scanner sc = new Scanner(System.in);
		String choice = "";
		stores = DAOFactory.getStoreDAO();
		divisions = DAOFactory.getDivisionsDAO();
		storeSales = DAOFactory.getStoreSalesDAO();

		// print out the display menu
		mainMenu();

		while (!choice.equalsIgnoreCase("exit")) {
			choice = Validator.getString(sc, "Enter a command: ");
			
			if (choice.equalsIgnoreCase("display")){
				displayMenu();
				choice = Validator.getString(sc, "Enter a command: ");
				if (choice.equalsIgnoreCase("list stores"))
					displayAllStores();
				else if (choice.equalsIgnoreCase("list div") || choice.equalsIgnoreCase("list division"))
					displayDivInformation();
				else if (choice.equalsIgnoreCase("list div stores") || choice.equalsIgnoreCase("list division stores"))
					displayAllDivStores();
				else if (choice.equalsIgnoreCase("add div") || choice.equalsIgnoreCase("add division"))
					addDivision();
				else if (choice.equalsIgnoreCase("div total"))
					divSum();
				else if (choice.equalsIgnoreCase("store sales"))
					displayStoreSales();
				else if (choice.equalsIgnoreCase("store sales total"))
					displayStoreSales();
				else if (choice.equalsIgnoreCase("back"))
					mainMenu();
				else if (choice.equalsIgnoreCase("exit"))
					break;
				else{
					System.out.println("Invalid command.\n");
					mainMenu();
				}
			}		
			else if (choice.equalsIgnoreCase("add")){
				addMenu();
				choice = Validator.getString(sc, "Enter a command: ");
				if (choice.equalsIgnoreCase("add store"))
					addStore();
				else if (choice.equalsIgnoreCase("add sales"))
					addSales();
				else if (choice.equalsIgnoreCase("add div") || choice.equalsIgnoreCase("add division"))
					addDivision();
				else if (choice.equalsIgnoreCase("back"))
					mainMenu();
				else if (choice.equalsIgnoreCase("exit"))
					break;
				else{
					System.out.println("Invalid command.\n");
					mainMenu();
				}
			}
			
			else if (choice.equalsIgnoreCase("delete")){
				deleteMenu();
				choice = Validator.getString(sc, "Enter a command: ");
				if (choice.equalsIgnoreCase("del store") || choice.equalsIgnoreCase("delete store"))
					delStore();
				else if (choice.equalsIgnoreCase("del sales") || choice.equalsIgnoreCase("delete sales")) {
					String choice2 = Validator.getStringChoice(sc, "Delete all the sales or one week?", "week",
							"all sales");
					if (choice2.equalsIgnoreCase("week"))
						delSpecificSales();
					else if (choice2.equalsIgnoreCase("all sales"))
						delAllSales();
				}
				else if (choice.equalsIgnoreCase("back"))
					mainMenu();
				else if (choice.equalsIgnoreCase("exit"))
					break;
				else{
					System.out.println("Invalid command.\n");
					mainMenu();
				}
			}
			
			else if(choice.equalsIgnoreCase("update")){
				updateMenu();
				choice = Validator.getString(sc, "Enter a command: ");
				if (choice.equalsIgnoreCase("update store"))
					updateStore();
				else if (choice.equalsIgnoreCase("update sales"))
					updateSales();
				else if (choice.equalsIgnoreCase("back"))
					mainMenu();
				else if (choice.equalsIgnoreCase("exit"))
					break;
				else{
					System.out.println("Invalid command.\n");
					mainMenu();
				}
			}			
			else{
				System.out.println("Invalid command.\n");
				mainMenu();
			}
		}
		sc.close();

		// print goodbye message
		System.out.println("Thanks for using the BigBoxApp. Bye!");
	}

	private static void delSpecificSales() {
		Calendar cal = Calendar.getInstance();
		int weekMax = cal.get(Calendar.WEEK_OF_YEAR);
		int yearMax = cal.get(Calendar.YEAR);
		String storeN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		int year = Validator.getInt(sc, "Enter year: ", 2016, yearMax + 1);
		int week = Validator.getInt(sc, "Enter week: ", 0, weekMax);
		try {
			int storeNumber = stores.getStoreID(storeN);
			if (storeNumber == 0)
				System.out.println("Error! Invalid store number.");
			else {
				StoreSales s = storeSales.getStoreSales(storeNumber, year, week);
				if (s == null) {
					System.out.println("Error! Store Sales not found.");
				} else {
					storeSales.deleteSalesWeek(s);
					System.out.println("Store's weekly sales was deleted from the database.\n");
				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void delAllSales() {
		String storeN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		try {
			int storeNumber = stores.getStoreID(storeN);
			if (storeNumber == 0)
				System.out.println("Error! Invalid store number.");
			else {
				ArrayList<StoreSales> s = storeSales.getStoreSales(storeNumber);
				if (s == null) {
					System.out.println("Error! Store Sales not found.");
				} else {
					for (StoreSales ss : s) {
						storeSales.delete(ss);
					}
					System.out.println("Store's weekly sales was added to the database.\n");

				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void displayDivInformation() {
		String divN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		try {
			Division d = divisions.getDivision(divN);
			System.out.println("\n[Division: division number= " + d.getDivNumber() + "\n division address= "
					+ d.getAddress() + "\n division city= " + d.getCity() + "\n division state= " + d.getState()
					+ "\n division zip-code= " + d.getZipcode() + "]\n");
		} catch (DBException e) {
			System.out.println(e + "\n");
		}
	}

	private static void displayStoreSales() {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		int weekMax = cal.get(Calendar.WEEK_OF_YEAR);
		int yearMax = cal.get(Calendar.YEAR);
		String storeN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		int year = Validator.getInt(sc, "Enter year: ", 2016, yearMax + 1);
		int week = Validator.getInt(sc, "Enter week: ", 0, weekMax);
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		try {
			int storeNumber = stores.getStoreID(storeN);
			if (storeNumber == 0)
				System.out.println("Error! Invalid store number.");
			else {
				StoreSales sales = storeSales.getStoreSales(storeNumber, year, week);
				System.out.println("Store sales for " + storeN + " is " + currency.format(sales.getSales()) + ".\n");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void divSum() {
		String divN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		try {
			double divSales = storeSales.getSalesForDivision(divN);
			System.out.println("The total sales for this district is " + currency.format(divSales));

		} catch (DBException e) {
			System.out.println(e + "\n");
		}
	}

	private static void updateStore() {
		// find out which store they want to update
		String updateInfoSN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		String updateInfoDN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		String updateObject = Validator.getString(sc,
				"What part would you like to update? (name, address, city, state, or zip)");
		// create the list of stores, and create the variable store to be edited
		ArrayList<Store> divStores = new ArrayList<Store>();
		try {
			divStores = stores.getAllStores();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Store store = null;
		for (Store s : divStores) {
			if ((s.getStorenumber().equalsIgnoreCase(updateInfoSN))
					&& (s.getDivisionnumber().equalsIgnoreCase(updateInfoDN))) {
				store = s;
			
			/*
			 * if(updateObject.equalsIgnoreCase("id")){ int i =
			 * Validator.getInt(sc, "Enter id: ", 0,100); updatedInfo =
			 * Integer.toString(i); }
			 */
			if (updateObject.equalsIgnoreCase("sales")) {
				double sales = Validator.getDouble(sc, "Enter sales: ", 0, 1000000);
				store.setSales(sales);
			} else if (updateObject.equalsIgnoreCase("name")) {
				String name = Validator.getString(sc, "Enter name: ");
				store.setName(name);
			} else if (updateObject.equalsIgnoreCase("address")) {
				String address = Validator.getString(sc, "Enter address: ");
				store.setAddress(address);
			} else if (updateObject.equalsIgnoreCase("city")) {
				String city = Validator.getString(sc, "Enter city: ");
				store.setCity(city);
			} else if (updateObject.equalsIgnoreCase("state")) {
				String state = Validator.getString(sc, "Enter state: ");
				store.setState(state);
			} else if (updateObject.equalsIgnoreCase("zip") || updateObject.equalsIgnoreCase("zip-code")
					|| updateObject.equalsIgnoreCase("zip code") || updateObject.equalsIgnoreCase("zipcode")) {
				String zipCode = Validator.getString(sc, "Enter zip-code: ");
				store.setZipcode(zipCode);
			} else {
				System.out.println("Error! Invalid entry.");
				updateInfoSN = "-1";
			}
			}
		}
		if(!updateInfoSN.equalsIgnoreCase("-1")){
		try {
			stores.update(store);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("The file has been updated");
		}
	}

	private static void updateSales() {
		Calendar cal = Calendar.getInstance();
		int weekMax = cal.get(Calendar.WEEK_OF_YEAR);
		int yearMax = cal.get(Calendar.YEAR);
		String storeN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		int year = Validator.getInt(sc, "Enter year: ", 2016, yearMax + 1);
		int week = Validator.getInt(sc, "Enter week: ", 0, weekMax);
		double sales = Validator.getDouble(sc, "Enter sales: ", 0, 1000000);

		try {
			int storeNumber = stores.getStoreID(storeN);
			if (storeNumber == 0)
				System.out.println("Error! Invalid store number.");
			else {
				StoreSales s = storeSales.getStoreSales(storeNumber, year, week);
				if (s == null) {
					System.out.println("Error! Store Sales not found.");
				} else {
					s.setSales(sales);
					storeSales.update(s);
					System.out.println("Store's weekly sales was added to the database.\n");

				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void delStore() {
		String deleteSN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		// String deleteDN = Validator.getStringNumeric(sc, "Enter division
		// number: ", 3);
		Store store = null;
		try {
			store = stores.get(deleteSN);
			if (store == null) {
				throw new Exception("Product not found.");
			} else {
				stores.delete(store);
				System.out.println(store.getStorenumber() + " was deleted from the database.\n");
			}
		} catch (Exception e) {
			System.out.println("Error! Unable to delete product.");
			System.out.println(e + "\n");
			return;
		}
	}

	private static void addStore() {
		// get all information necessary to add a store
		String divN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		String storeN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		double sales = Validator.getDouble(sc, "Enter sales: ", 0, 1000000);
		String name = Validator.getString(sc, "Enter name: ");
		String address = Validator.getString(sc, "Enter address: ");
		String city = Validator.getString(sc, "Enter city: ");
		String state = Validator.getString(sc, "Enter state: ");
		String zip = Validator.getString(sc, "Enter zip-code: ");
		try {
			int divNumber = divisions.getDivisionID(divN);
			Store s = new Store(-1, divNumber, storeN, sales, name, address, city, state, zip);
			if (divNumber == 0)
				System.out.println("Error! Invalid division number.");
			else {
				stores.add(s);
				System.out.println("Store was added to the database.\n");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addSales() {
		Calendar cal = Calendar.getInstance();
		int weekMax = cal.get(Calendar.WEEK_OF_YEAR);
		int yearMax = cal.get(Calendar.YEAR);
		String storeN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		int year = Validator.getInt(sc, "Enter year: ", 2016, yearMax + 1);
		int week = Validator.getInt(sc, "Enter week: ", 0, weekMax);
		double sales = Validator.getDouble(sc, "Enter sales: ", 0, 1000000);
		try {
			int storeNumber = stores.getStoreID(storeN);
			System.out.println(storeNumber);
			StoreSales s = new StoreSales(storeNumber, year, week, sales);
			if (storeNumber == 0)
				System.out.println("Error! Invalid store number.");
			else {
				storeSales.addStoreSales(s);
				System.out.println("Store's weekly sales was added to the database.\n");
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void addDivision() {
		String divN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		String name = Validator.getString(sc, "Enter name: ");
		String address = Validator.getString(sc, "Enter address: ");
		String city = Validator.getString(sc, "Enter city: ");
		String state = Validator.getString(sc, "Enter state: ");
		String zip = Validator.getString(sc, "Enter zip-code: ");
		Division division = new Division(divN, name, address, city, state, zip);
		try {
			divisions.addDivision(division);
		} catch (DBException e) {
			e.printStackTrace();
		}
	}

	private static void displayAllDivStores() {
		String divN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		ArrayList<Store> storez = null;
		try {
			storez = stores.getDivStore(divN);
		} catch (DBException e) {
			System.out.println(e + "\n");
		}

		if (storez == null) {
			System.out.println("Error! Unable to get stores.\n");
		} else {
			for (Store s : storez) {
				String message = "\n" + "[Store: store#= " + s.getStorenumber() + "\n div#= " + s.getDivisionnumber()
						+ "\n sales= " + s.getFormattedSales() + "]" + "\n \n" + "[Facility: id= " + s.getId()
						+ "\n name: " + s.getName() + "\n address= " + s.getAddress() + "\n city = " + s.getCity()
						+ "\n state= " + s.getState() + "\n zip= " + s.getZipcode() + "]" + "\n";
				System.out.println(message);
			}
		}

	}

	private static void displayAllStores() {
		ArrayList<Store> storez = null;
		try {
			storez = stores.getAllStores();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (storez == null) {
			System.out.println("Error! Unable to get products.\n");
		} else {
			System.out.println("Division Number  Store Number  Name                     Address                  City           State  Zip  ");
			for (Store s : storez) {
				System.out.println(StringUtils.padWithSpaces(s.getStorenumber(), 17) + StringUtils.padWithSpaces(s.getDivisionnumber(), 14)
						+ StringUtils.padWithSpaces(s.getName(), 25) + StringUtils.padWithSpaces(s.getAddress(),25)
						+ StringUtils.padWithSpaces(s.getCity(), 15) + StringUtils.padWithSpaces(s.getState(),7) + s.getZipcode());
			}
		}
	}
	private static void mainMenu(){
		System.out.println("\nMAIN MENU");
		System.out.println("Display\t\t\t-Display menu");
		System.out.println("Add\t\t\t-Add menu");
		System.out.println("Delete\t\t\t-Delete menu");
		System.out.println("Update\t\t\t-Update menu");
		System.out.println("exit\t\t\t-Exit this application\n");
	}
	
	private static void displayMenu(){
		System.out.println("\nDISPLAY MENU");
		System.out.println("list stores\t\t-List all stores");
		System.out.println("list div\t\t-List division information");
		System.out.println("list div stores\t\t-List all stores by division");
		System.out.println("div sales\t\t-List the total sales for a division");
		System.out.println("store sales\t\t-List a store's sales for a given week");
		System.out.println("back\t\t\t-go back to previous menu");
		System.out.println("exit\t\t\t-Exit this application\n");		
	}
	
	private static void addMenu(){
		System.out.println("\nADD MENU");
		System.out.println("add store\t\t-Add a store");
		System.out.println("add sales\t\t-Add a stores' sales for the week");
		System.out.println("add div\t\t\t-Add a division");
		System.out.println("back\t\t\t-go back to previous menu");
		System.out.println("exit\t\t\t-Exit this application\n");		
	}
	
	private static void deleteMenu(){
		System.out.println("\nDELETE MENU");
		System.out.println("del store\t\t-Delete a store");
		System.out.println("del sales\t\t-Delete a store's sales");
		System.out.println("back\t\t\t-go back to previous menu");
		System.out.println("exit\t\t\t-Exit this application\n");
	}
	
	private static void updateMenu(){
		System.out.println("\nUPDATE MENU");
		System.out.println("update stores\t\t-Udate a store");
		System.out.println("update sales\t\t-Update a store sales");
		System.out.println("back\t\t\t-go back to previous menu");
		System.out.println("exit\t\t\t-Exit this application\n");
	}
	
	private static void displayMen() {
		System.out.println("COMMAND MENU");
		System.out.println("list stores\t\t-List all stores");
		System.out.println("list div\t\t-List division information");
		System.out.println("list div stores\t\t-List all stores by division");
		System.out.println("div sales\t\t-List the total sales for a division");
		System.out.println("store sales\t\t-List a store's sales for a given week");
		System.out.println("add store\t\t-Add a store");
		System.out.println("add sales\t\t-Add a stores' sales for the week");
		System.out.println("add div\t\t\t-Add a division");
		System.out.println("del store\t\t-Delete a store");
		System.out.println("del sales\t\t-Delete a store's sales");
		System.out.println("update stores\t\t-Update a store");
		System.out.println("update sales\t\tUpdate a store sales");
		System.out.println("help\t\t\t-Show this menu");
		System.out.println("exit\t\t\t-Exit this application\n");
	}
}
