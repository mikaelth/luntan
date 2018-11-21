package se.uu.ebc.luntan.enums;

public enum CourseGroup {

	BASE("Baskurser"),
	ADVANCED("Påbyggnadskurser"),
	KNO("KNO-kurser"),
	SUMMER("Sommarkurser"),
	ENGINEER("Ingenjörskurser"),
	BIOINF("Master Bioinformatik"),
	BIOTECH("Master Tillämpad bioteknik"),
	MISC("Övrigt"),
	RESEARCH("Forskarskola"),
	DEFREEPROJ("Examensarbeten"),
	BASEYEAR("Basåret"),
	TEACHERS("Lärarkurser"),
	EXTERNAL("Externa kurser"),
	GOTLAND("Gotlandskurser"),
	GOTLANDNET("Gotland, distanskurser"),
	ENVIRONMENT("Miljövetenskap"),
	LIBART("Liberal Arts"),
	COMMISIONED("Uppdragsutbildning"),
	PROGRAM("Programansvar");


	private final String displayName;
	
	private CourseGroup(final String displayName) {
		this.displayName = displayName;
	}
	
	public String displayName() { return displayName; }
	
    @Override 
    public String toString() { return displayName; }

}