package bigbox.business;

import java.text.NumberFormat;

public final class Store extends Facility {
	private double sales;
	private String storenumber;
	private String divisionnumber;
	private int divisionID;

	public Store() {
		super();
		sales = 0;
		storenumber = "";
		divisionnumber = "";
	}

	public Store(int i, String dn, String sn, double s, String n, String a, String c, String st, String z) {
		super(i, dn, sn, s, n, a, c, st, z);
		setSales(s);
		setStorenumber(sn);
		setDivisionnumber(dn);
	}

	public Store(int i, int dn, String sn, double s, String n, String a, String c, String st, String z) {
		super(i, dn, sn, s, n, a, c, st, z);
		setSales(s);
		setStorenumber(sn);
		setDivisionID(dn);
	}

	private void setDivisionID(int dn) {
		divisionID = dn;
	}

	public int getDivisionID() {
		return divisionID;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}

	public String getStorenumber() {
		return storenumber;
	}

	public void setStorenumber(String storenumber) {
		this.storenumber = storenumber;
	}

	public String getDivisionnumber() {
		return divisionnumber;
	}

	public void setDivisionnumber(String divisionnumber) {
		this.divisionnumber = divisionnumber;
	}

	public String getFormattedSales() {
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		return currency.format(sales);
	}

	@Override
	public String toString() {
		return "\n" + "[Store: store#= " + storenumber + "\n div#= " + divisionnumber + "]" + "\n \n"
				+ super.toString();
	}

}
