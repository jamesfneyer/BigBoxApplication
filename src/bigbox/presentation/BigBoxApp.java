package bigbox.presentation;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

import bigbox.business.Store;
import bigbox.db.*;
import bigbox.util.*;
import neyer.db.*;

public class BigBoxApp {
	
	static StoreDAO stores = null;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args){
		// TODO Auto-generated method stub

		//print welcome message
		System.out.println("Welcome to the Big Box application\n");
		
		//Create the variables needed
		Scanner sc = new Scanner(System.in);
		String choice = "";
		stores = DAOFactory.getStoreDAO();
		
		//print out the display menu
		displayMenu();
		
		while(!choice.equalsIgnoreCase("exit")){
			choice = Validator.getString(sc, "Enter a command: ");
			if(choice.equalsIgnoreCase("list"))
				displayAllStores();
			else if(choice.equalsIgnoreCase("div")||choice.equalsIgnoreCase("division"))
				displayAllDivStores();
			else if(choice.equalsIgnoreCase("add"))
				addStore();
			else if(choice.equalsIgnoreCase("div average"))
				divAverage();
			else if(choice.equalsIgnoreCase("div total"))
				divSum();
			else if(choice.equalsIgnoreCase("del")||choice.equalsIgnoreCase("delete"))
				delStore();
			else if(choice.equalsIgnoreCase("help"))
				displayMenu();
			else if(choice.equalsIgnoreCase("update"))
				updateStore();
			else if(choice.equalsIgnoreCase("exit"))
				break;
			else
				System.out.println("Invalid command.\n");
		}
		sc.close();
	
		//print goodbye message
		System.out.println("Thanks for using the BigBoxApp. Bye!");
	}

	private static void divSum(){
    	NumberFormat currency = NumberFormat.getCurrencyInstance();
		System.out.println("The total sales for this district is "+currency.format(divAverage()));
	}
	private static double divAverage() {
		String divN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		ArrayList<Store> stores = null;
		double sum = 0;
		int i = 0;
		try {
            stores = StoreDB.getDivStore(divN);
        } catch (DBException e) {
           System.out.println(e + "\n");
        }
        
        if (stores == null) {
            System.out.println("Error! Unable to get stores.\n");
        }else{		
        	for(Store s:stores){
			i++;
			sum += s.getSales();
        	}
        	double average = sum/i;
        	NumberFormat currency = NumberFormat.getCurrencyInstance();
        	System.out.println("The average sales for this district is "+currency.format(average));
       }
        return sum;
	}

	private static void updateStore() {
		String updateInfoSN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		String updateInfoDN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		String updateObject = Validator.getString(sc, "What part would you like to update?(sales, name, address, city, state, or zip)");
		ArrayList<Store> divStores = stores.getAllStores();
		Store store = null;
		for(Store s:divStores){
			if ((s.getStorenumber().equalsIgnoreCase(updateInfoSN))&&(s.getDivisionnumber().equalsIgnoreCase(updateInfoDN))) {					
					store = s;
			}
			/*if(updateObject.equalsIgnoreCase("id")){
				int i = Validator.getInt(sc, "Enter id: ", 0,100);
				updatedInfo = Integer.toString(i);
			}*/
			if(updateObject.equalsIgnoreCase("sales")){
				double sales = Validator.getDouble(sc, "Enter sales: ", 0, 1000000);
				s.setSales(sales);
				}
			else if(updateObject.equalsIgnoreCase("name")){
				String name = Validator.getString(sc, "Enter name: ");
				s.setName(name);
			}
			else if(updateObject.equalsIgnoreCase("address")){
				String address = Validator.getString(sc, "Enter address: ");
				s.setAddress(address);
			}
			else if(updateObject.equalsIgnoreCase("city")){
				String city = Validator.getString(sc, "Enter city: ");
				s.setCity(city);
			}
			else if(updateObject.equalsIgnoreCase("state")){
				String state = Validator.getString(sc, "Enter state: ");
				s.setState(state);
			}
			else if(updateObject.equalsIgnoreCase("zip")||updateObject.equalsIgnoreCase("zip-code")||updateObject.equalsIgnoreCase("zip code")||updateObject.equalsIgnoreCase("zipcode")){
				String zipCode = Validator.getString(sc, "Enter zip-code: ");
				s.setZipcode(zipCode);
			}
			else{
				System.out.println("Error! Invalid entry.");
				updateInfoSN = "-1";
			}	
		}
			System.out.println("The file has been updated");
	}

	private static void delStore() {
		String deleteSN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
//		String deleteDN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		Store store = null;
		try {
            store = StoreDB.get(deleteSN);
            if (store == null) {
                throw new Exception("Product not found.");
            }
        } catch (Exception e) {
            System.out.println("Error! Unable to delete product.");
            System.out.println(e + "\n");
            return;
        }
        
        try {
            StoreDB.delete(store);
        } catch (DBException e) {
            System.out.println("Error! Unable to delete product.");
            System.out.println(e + "\n");
        }
        
        System.out.println(store.getStorenumber() + " was deleted from the database.\n");
	}

	private static void addStore() {
//		String id = Integer.toString(Validator.getInt(sc, "Enter id: ", 0,100));
		String divN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		String storeN = Validator.getStringNumeric(sc, "Enter store number: ", 5);
		double sales = Validator.getDouble(sc, "Enter sales: ", 0, 1000000);
		String name = Validator.getString(sc, "Enter name: ");
		String address = Validator.getString(sc, "Enter address: ");
		String city = Validator.getString(sc, "Enter city: ");
		String state = Validator.getString(sc, "Enter state: ");
		String zip = Validator.getString(sc, "Enter zip-code: ");
		Store s = new Store(-1,divN,storeN,sales,name,address,city,state,zip);
		try {
			StoreDB.add(s);
			System.out.println("Store was added to the database.\n");
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void displayAllDivStores() {
		String divN = Validator.getStringNumeric(sc, "Enter division number: ", 3);
		ArrayList<Store> stores = null;
		try {
            stores = StoreDB.getDivStore(divN);
        } catch (DBException e) {
           System.out.println(e + "\n");
        }
        
        if (stores == null) {
            System.out.println("Error! Unable to get stores.\n");
        }else{		
        	for(Store s:stores){
			String message = "\n"+"[Store: store#= "+s.getStorenumber()
					+"\n div#= "+s.getDivisionnumber()+
					"\n sales= "+s.getFormattedSales()+"]"+
					"\n \n"+"[Facility: id= " + s.getId() + 
		               "\n name: " + s.getName()+
		               "\n address= " + s.getAddress() +
		               "\n city = " +s.getCity()+
		               "\n state= "+s.getState()+
		               "\n zip= "+s.getZipcode()+"]"+"\n";
			System.out.println(message);
		}
        }
		
	}

	private static void displayAllStores() {
		ArrayList<Store> stores = null;
		try {
			stores = StoreDB.getAll();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (stores == null) {
            System.out.println("Error! Unable to get products.\n");
		}
		else {
			for (Store s : stores) {
				String message = "\n" + "[Store: store#= " + s.getStorenumber() + "\n div#= " + s.getDivisionnumber()
						+ "\n sales= " + s.getFormattedSales() + "]" + "\n \n" + "[Facility: id= " + s.getId()
						+ "\n name: " + s.getName() + "\n address= " + s.getAddress() + "\n city = " + s.getCity()
						+ "\n state= " + s.getState() + "\n zip= " + s.getZipcode() + "]" + "\n";
				System.out.println(message);
			}
		}
	}

	private static void displayMenu() {
		System.out.println("COMMAND MENU");
		System.out.println("list\t-List all stores");
		System.out.println("div\t-List all stores for a division");
		System.out.println("add\t-Add a store");
		System.out.println("del\t-Delete a store");
		System.out.println("help\t-Show this menu");
		System.out.println("exit\t-Exit this application");
		System.out.println("update\t-Update a store");
		System.out.println("div average\t-List the average sales for a division");
		System.out.println("div total\t-List the average sales for a division\n");
	}
}
