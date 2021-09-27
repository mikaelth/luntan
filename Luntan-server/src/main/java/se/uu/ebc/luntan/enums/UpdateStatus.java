package se.uu.ebc.luntan.enums;

public enum UpdateStatus {

	UPDATED("Posten uppdaterad"),
	EXISTINGVALUE("Posten har tidigare värde"),
	NOMATCH("Hittade ingen post"),
	MULTIPLEQUERY("Ej entydigt tillfälle från kurskod"),
	MULTIPLEMATCHES("Flera poster funna");


	private final String displayName;
	
	private UpdateStatus(final String displayName) {
		this.displayName = displayName;
	}
	
	public String displayName() { return displayName; }
	
    @Override 
    public String toString() { return displayName; }

}