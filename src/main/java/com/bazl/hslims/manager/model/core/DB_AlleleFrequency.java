package com.bazl.hslims.manager.model.core;


import java.io.Serializable;

public class DB_AlleleFrequency  implements Serializable {

     public String markername;
     public String populationid;
     public String allelename;
     public float alleleSize;
     public double allelefrequency;
     public int checkNumber;
    
     public DB_AlleleFrequency(){}
     
	
	public DB_AlleleFrequency(String markername, String populationid, String allelename, float alleleSize, double allelerequency) {
		this.markername = markername;
		this.populationid = populationid;
		this.allelename = allelename;
		this.alleleSize = alleleSize;
		this.allelefrequency = allelerequency;
	}


	public String getAllelename() {
		return allelename;
	}
	public void setAllelename(String allelename) {
		this.allelename = allelename;
	}
	public double getAllelerequency() {
		return allelefrequency;
	}
	public void setAllelerequency(double allelerequency) {
		this.allelefrequency = allelerequency;
	}
	
	public String getMarkername() {
		return markername;
	}
	public void setMarkername(String markername) {
		this.markername = markername;
	}
	public String getPopulationid() {
		return populationid;
	}
	public void setPopulationid(String populationid) {
		this.populationid = populationid;
	}
	
	public Pop_Allele convert2Pop_Allele()
	{
		return new Pop_Allele(allelename,alleleSize,allelefrequency,markername);
	}
   
 }