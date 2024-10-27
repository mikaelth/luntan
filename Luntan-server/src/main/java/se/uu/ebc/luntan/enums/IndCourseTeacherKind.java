package se.uu.ebc.luntan.enums;

public enum IndCourseTeacherKind
{
	Supervisor("Handledare"),
	Reader("Ämnesgranskare"),
	Coordinator("Koordinator");

	private final String designation;

	private IndCourseTeacherKind(final String name){
		this.designation = name;
	}

}