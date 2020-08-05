package com.bazl.hslims.manager.model.core;

import java.sql.Date;


public class Pop_Population implements java.io.Serializable{
	
	private static final long serialVersionUID = -8594196261651545410L;
	
	public String populationID;
    public String populationname;
    public int area;
    public String producer;
    public Date samplingtime;
    //public int samplingnumber;
    public String comments;
    
	public Pop_Marker[] markers ;
	
	public Pop_Population()
	{}
	
	public Pop_Population(String populationID, String populationname, int area, String producer, Date samplingtime)
	{
		super();
		this.populationID = populationID;
		this.populationname = populationname;
		this.area = area;
		this.producer = producer;
		this.samplingtime = samplingtime;
	}
	
	/**
	 * Copy all the data of the DB_Population object to create a new Pop_Population object
	 * @param dbPopulation
	 */
	public Pop_Population(DB_Population dbPopulation) {
		super();
		this.populationID = dbPopulation.populationid;
		this.populationname = dbPopulation.populationname;
		this.area = dbPopulation.area;
		this.producer = dbPopulation.producer;
		this.samplingtime = dbPopulation.samplingtime;
		this.comments = dbPopulation.comments;
	}

	/**
	 * Get allele frequency by marker name and allele name.
	 * 
	 * @param markername
	 * @param allelename
	 * @return allele frequency if found the allele; 1.0d if not
	 */
	public double GetAlleleFreqs(String markername, String allelename) {
		for (int i = 0; i < markers.length; i++) {
			if (markers[i].markername.compareToIgnoreCase(markername) == 0) {
				return markers[i].GetAlleleFreqs(allelename);
			}
		}

		// Cannot find the allele frequency. Wait for further operations.
		return 1.0d;
	}
	
	/**
	 * get minimum allele frequency of the specified marker
	 * 
	 * @param markerName
	 * @return allele frequency if succeeded; 1.0d if no marker is found
	 */
	public Double GetMinAlleleFreq(String markerName) {
		for (int i = 0; i < markers.length; i++)
			if (markers[i].markername.compareToIgnoreCase(markerName) == 0)
				return markers[i].getMinAlleleFreq();

		return Double.NaN;
	}

	/**
	 * get maximum allele frequency of the specified marker
	 * 
	 * @param markerName
	 * @return allele frequency if succeeded; 1.0d if no marker is found
	 */
	public Double GetMaxAlleleFreq(String markerName) {
		for (int i = 0; i < markers.length; i++)
			if (markers[i].markername.compareToIgnoreCase(markerName) == 0)
				return markers[i].getMaxAlleleFreq();

		return Double.NaN;
	}
}
