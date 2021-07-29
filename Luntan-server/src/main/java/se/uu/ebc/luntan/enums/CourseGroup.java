package se.uu.ebc.luntan.enums;

public enum CourseGroup {

	BASE("Baskurser"),
	BASEYEAR("Basåret"),
	DEFREEPROJ("Examensarbeten"),
	EXTERNAL("Externa kurser"),
	RESEARCH("Forskarskola"),
	GOTLANDNET("Gotland, distanskurser"),
	GOTLAND("Gotlandskurser"),
	ENGINEER("Ingenjörskurser"),
	KNO("KNO-kurser"),
	CROSS("Tvärvetenskapliga kurser"),
	TEACHERS("Lärarkurser"),
	LIBART("Liberal Arts"),
	BIOINF("Master Bioinformatik"),
	BIOTECH("Master Tillämpad bioteknik"),
	BIOPHYS("Master Biofysik"),
	ENVIRONMENT("Miljövetenskap"),
	ADVANCED("Påbyggnadskurser"),
	PROGRAM("Programansvar"),
	SUMMER("Sommarkurser"),
	COMMISIONED("Uppdragsutbildning"),
	LLL("Livslångt lärande"),
	MISC("Övrigt");


	private final String displayName;
	
	private CourseGroup(final String displayName) {
		this.displayName = displayName;
	}
	
	public String displayName() { return displayName; }
	
    @Override 
    public String toString() { return displayName; }

}