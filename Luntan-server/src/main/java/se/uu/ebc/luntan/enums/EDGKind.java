package se.uu.ebc.luntan.enums;


public enum EDGKind
{
	EXPLICIT("Explicit fördelning",true),
	PROPORTIONAL("Proportion till kursanslag",false);


	private final String plainText;
	private final boolean explicit;

	private EDGKind(final String plainText, final boolean explicit){
		this.plainText = plainText;
		this.explicit = explicit;
	}


	public String getPlainText(){
		return this.plainText;
	}

	public boolean isExplicit() {
		return this.explicit;
	}
	
    @Override 
    public String toString() { return plainText; }

}