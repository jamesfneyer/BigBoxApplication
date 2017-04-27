package bigbox.business;

public abstract class Facility {
	private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zipcode;
	
    public Facility(){
    	id = 0;
    	name = "";
    	address = "";
    	city = "";
    	state = "";
    	zipcode = "";
    }
    
    public Facility(int i, String dn, String sn, double s, String n, String a, String c, String st, String z){
    	setId(i);
    	setName(n);
    	setAddress(a);
    	setCity(c);
    	setState(st);
    	setZipcode(z);
    	
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	@Override
	public String toString()
    {
		String s ="\n"+"[Facility: id= " + id + 
               "\n name: " + name+
               "\n address= " + address +
               "\n city = " +city+
               "\n state= "+state+
               "\n zip= "+zipcode+"]"+"\n";
		return s;
    }
	public String getDisplayText(){
		return null;
	}
	
}
