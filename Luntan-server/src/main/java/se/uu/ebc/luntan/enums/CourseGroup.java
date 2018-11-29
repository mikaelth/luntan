package se.uu.ebc.luntan.enums;

public enum CourseGroup {

	BASEYEAR("Basåret"),
	BASE("Baskurser"),
	DEFREEPROJ("Examensarbeten"),
	EXTERNAL("Externa kurser"),
	RESEARCH("Forskarskola"),
	GOTLANDNET("Gotland, distanskurser"),
	GOTLAND("Gotlandskurser"),
	ENGINEER("Ingenjörskurser"),
	KNO("KNO-kurser"),
	TEACHERS("Lärarkurser"),
	LIBART("Liberal Arts"),
	BIOINF("Master Bioinformatik"),
	BIOTECH("Master Tillämpad bioteknik"),
	ENVIRONMENT("Miljövetenskap"),
	MISC("Övrigt"),
	ADVANCED("Påbyggnadskurser"),
	PROGRAM("Programansvar"),
	SUMMER("Sommarkurser"),
	COMMISIONED("Uppdragsutbildning");


	private final String displayName;
	
	private CourseGroup(final String displayName) {
		this.displayName = displayName;
	}
	
	public String displayName() { return displayName; }
	
    @Override 
    public String toString() { return displayName; }

}