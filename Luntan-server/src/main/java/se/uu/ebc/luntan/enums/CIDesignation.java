package se.uu.ebc.luntan.enums;

public enum CIDesignation
{
	NONE(""),
	VT("-vt"),
	HT("-ht"),
	ST("-st"),
	P1("-period-1"),
	P2("-period-2"),
	P3("-period-3"),
	P4("-period-4");

	private final String designation;

	private CIDesignation(final String designation){
		this.designation = designation;
	}


	public String getDesignation(){
		return this.designation;
	}

    @Override 
    public String toString() { return designation; }

}