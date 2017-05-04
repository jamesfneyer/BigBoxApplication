package bigbox.business;

public class Division {
	private String DivNumber;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zipcode;

	public Division() {
		DivNumber = "";
		name = "";
		address = "";
		city = "";
		state = "";
		zipcode = "";
	}

	public Division(String d, String n, String a, String c, String s, String z) {
		setDivNumber(d);
		setName(n);
		;
		setAddress(a);
		;
		setCity(c);
		;
		setState(s);
		setZipcode(z);
	}

	public String getDivNumber() {
		return DivNumber;
	}

	public void setDivNumber(String divNumber) {
		DivNumber = divNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
