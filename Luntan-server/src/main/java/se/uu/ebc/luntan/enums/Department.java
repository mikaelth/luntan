package se.uu.ebc.luntan.enums;

public enum Department
{
	IBG(true, "Institutionen för biologisk grundutbildning"),
	ICM(false, "Institutionen för cell och molekylärbiologi"),
	IEG(false, "Institutionen för ekologi och genetik"),
	IEGS(false, "Institutionen för evolution, genetik och systematik"),
	IOB(false, "Institutionen för organismbiologi");

	private final boolean implicit;
	private final String name;

	private Department(final boolean implicit, final String name){
		this.implicit = implicit;
		this.name = name;
	}

	public boolean calculateImplicitly(){
		return this.implicit;
	}

	public boolean isImplicit(){
		return this.implicit;
	}

	public String deptName(){
		return this.name;
	}

}