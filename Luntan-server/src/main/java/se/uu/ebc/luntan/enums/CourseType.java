package se.uu.ebc.luntan.enums;

public enum CourseType {

	REGULAR("Vanlig kurs",false),
	INDIVIDUAL("Individuell kurs",false),
	DEGREE_T("Examensarbete T",false);


	private final String displayName;
	private final boolean splitGrant;

	private CourseType(final String displayName, final boolean splitGrant) {
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
