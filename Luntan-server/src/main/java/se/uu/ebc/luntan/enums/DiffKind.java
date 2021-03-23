package se.uu.ebc.luntan.enums;

public enum DiffKind
{
	NONE(""),
	CHANGE("Ändrad"),
	NEW("Ny"),
	DELETE("Utgår");

	private final String kind;

	private DiffKind(final String kind){
		this.kind = kind;
	}

	public String chgType(){
		return this.kind;
	}

    @Override 
    public String toString() { return kind; }

}