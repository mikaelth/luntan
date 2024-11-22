package se.uu.ebc.luntan.enums;

public enum CourseType {

	REGULAR("Normalkurs"),
	INDIVIDUAL("Individuell kurs"),
	DEGREE_T("Examensarbete T");


	private final String displayName;


	private CourseType(final String displayName) {
		this.displayName = displayName;
	}

	public String displayName() { return displayName; }

    @Override
    public String toString() { return displayName; }

}
