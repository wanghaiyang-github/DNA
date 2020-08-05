package com.bazl.hslims.manager.model.core;

@SuppressWarnings("serial")
public class Pop_Allele implements java.io.Serializable{

	public String allelename;
	public float alleleSize;
	public double alleleFrequncy;
	public String markername;
    public String populationID;
    //public String markerid;
    public String comments;
    
    public Pop_Allele()
    {
    }

	public Pop_Allele(String allelename, float alleleSize, double alleleFrequncy, String markername) {
		//super();
		this.allelename = allelename;
		this.alleleSize = alleleSize;
		this.alleleFrequncy = alleleFrequncy;
		this.markername = markername;
		
	}
	
}
