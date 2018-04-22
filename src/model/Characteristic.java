package model;

public class Characteristic {

	private long characteristicID;
	private String name;
	private int unit;
	private long categoryID;
	
	//===========CONSTRUCTORS===========
	
	public Characteristic(String name, long categoryID) {
		if(name!= null && !name.isEmpty()) {
			this.name = name;
		}
		this.categoryID = categoryID;
	}
	
	public Characteristic(String name, int unit, long categoryID) {
		this(name, categoryID);
		this.unit = unit;
	}
	
	//===========GETTERS===========
	
	public long getCharacteristicID() {
		return characteristicID;
	}
	
	public String getName() {
		return name;
	}
	
	public long getCategoryID() {
		return categoryID;
	}
	
	public int getUnit() {
		return unit;
	}
}
