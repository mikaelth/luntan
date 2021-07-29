package se.uu.ebc.luntan.enums;


public enum StudentModelNumberCase
{
	DEFAULT("Grundantal"),
	LOCKED("Låst lunta"),
	EXPLICTISTART("Explicit antal"),
	REGISTERED("Registrerade studenter"),
	PREVIOUSMODEL("Modell föregående år"),
	PREVIOUSREG("Registrerade föregående år"),
	REG2YEARS("Registrerade två år tidigare");


	private final String designation;

	private StudentModelNumberCase(final String designation){
		this.designation = designation;
	}


	public String getDesignation(){
		return this.designation;
	}

    @Override 
    public String toString() { return designation; }

}