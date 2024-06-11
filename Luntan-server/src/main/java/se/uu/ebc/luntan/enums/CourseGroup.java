package se.uu.ebc.luntan.enums;

public enum CourseGroup {

	BASE("Baskurser",false),
	BASEYEAR("Basåret",false),
	EXTERNAL("Externa kurser",false),
	RESEARCH("Forskarskola",false),
	GOTLANDNET("Gotland, distanskurser",false),
	GOTLAND("Gotlandskurser",false),
	ENGINEER("Ingenjörskurser",false),
	KNO("KNO-kurser",false),
	CROSS("Tvärvetenskapliga kurser",false),
	TEACHERS("Lärarkurser",false),
	LIBART("Liberal Arts",false),
	BIOINF("Master Bioinformatik",false),
	BIOTECH("Master Tillämpad bioteknik",false),
	BIOPHYS("Master Biofysik",false),
	ENVIRONMENT("Miljövetenskap",false),
	ADVANCED("Påbyggnadskurser",false),
	PROGRAM("Programansvar",false),
	SUMMER("Sommarkurser",false),
	DEFREEPROJ("Individuella kurser",true),
	COMMISIONED("Uppdragsutbildning",false),
	LLL("Livslångt lärande",false),
	MISC("Övrigt",false);


	private final String displayName;
	private final boolean splitGrant;
	
	private CourseGroup(final String displayName, final boolean splitGrant) {
		this.displayName = displayName;
		this.splitGrant = splitGrant;
	}
	
	public String displayName() { return displayName; }
	
	public boolean isSplitGrant() {
		return this.splitGrant;
	}

    @Override 
    public String toString() { return displayName; }

}