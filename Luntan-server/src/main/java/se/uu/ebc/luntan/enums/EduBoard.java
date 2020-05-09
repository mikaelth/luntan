package se.uu.ebc.luntan.enums;

public enum EduBoard
{
	EDU("Institutionen för pedagogik, didaktik och utbildningsstudier"),
	NUN("Naturvetenskapliga utbildningsnämnden"),
	TUN("Tekniska utbildningsnämnden");

	private final String name;

	private EduBoard(final String name){
		this.name = name;
	}

	public String boardName(){
		return this.name;
	}

    @Override 
    public String toString() { return name; }

}