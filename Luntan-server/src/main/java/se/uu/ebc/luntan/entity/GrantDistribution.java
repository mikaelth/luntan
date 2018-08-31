package  se.uu.ebc.luntan.entity;

import java.util.Set;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Embeddable
public class GrantDistribution {

    @Min(value = 0)
    @Max(value = 1)
	private Float ibg;

    @Min(value = 0)
    @Max(value = 1)
	private Float icm;

    @Min(value = 0)
    @Max(value = 1)
	private Float ieg;

    @Min(value = 0)
    @Max(value = 1)
	private Float iob;
	

	/* Setters and getters */
	
	public Float getIcm()
	{
		return this.icm;
	}

	public void setIcm(Float icm)
	{
		this.icm = icm;
	}


	public Float getIeg()
	{
		return this.ieg;
	}

	public void setIeg(Float ieg)
	{
		this.ieg = ieg;
	}


	public Float getIob()
	{
		return this.iob;
	}

	public void setIob(Float iob)
	{
		this.iob = iob;
	}



	public Float getIbg()
	{
		return this.ibg;
	}

	public void setIbg(Float ibg)
	{
		this.ibg = ibg;
	}


	/* Constructors */
	
	public GrantDistribution(){}
	
	public GrantDistribution(Float ibg, Float icm, Float ieg, Float iob)	 {
		this.ibg=ibg;
		this.icm=icm;
		this.ieg=ieg;
		this.iob=iob;
	
		if (ibg+icm+ieg+iob > 1.0f) {throw new NumberFormatException(); }
	}
}