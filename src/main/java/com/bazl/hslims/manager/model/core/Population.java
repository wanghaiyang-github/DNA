package com.bazl.hslims.manager.model.core;

import java.sql.Date;

public class Population
{
	public String populationID;
    public String populationname;
    public String producer;
    public Date samplingTime;
    public int sampleingNum;
    public String comment;
    
    public Population(){}
    
	public Population(String populationID, String populationname)
	{
		super();
		this.populationID = populationID;
		this.populationname = populationname;
	}
    
    
}
