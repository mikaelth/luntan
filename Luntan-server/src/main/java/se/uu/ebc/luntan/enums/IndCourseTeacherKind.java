package se.uu.ebc.luntan.enums;

public enum IndCourseTeacherKind
{
	Coordinator("Koordinator"),
	Supervisor("Handledare"),
	Reader("Ämnesgranskare"),
	Examiner("Examinator");

	private final String designation;

	private IndCourseTeacherKind(final String name){
		this.designation = name;
	}

    @Override
    public String toString() { return designation; }

}
