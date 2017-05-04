package bigbox.business;

public class StoreSales {
	private int storeID;
	private int Year;
	private int Week;
	private double sales;

	public StoreSales() {
		storeID = 0;
		Year = 0;
		Week = 0;
		sales = 0.0;
	}

	public StoreSales(int si, int y, int w, double s) {
		setStoreID(si);
		setYear(y);
		setWeek(w);
		setSales(s);
	}

	public int getStoreID() {
		return storeID;
	}

	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}

	public int getYear() {
		return Year;
	}

	public void setYear(int year) {
		Year = year;
	}

	public int getWeek() {
		return Week;
	}

	public void setWeek(int week) {
		Week = week;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}

}
