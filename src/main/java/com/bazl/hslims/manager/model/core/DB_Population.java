package com.bazl.hslims.manager.model.core;

import java.io.Serializable;
import java.sql.Date;

public class DB_Population  implements Serializable {

     public String populationid;
     public String populationname;
     public int area;
     public String producer;
     public Date samplingtime;
     public int samplingnumber;
     public String comments;
     
    /** default constructor */
    public DB_Population() {

    }

	/** minimal constructor */
    public DB_Population(String populationid) {
        this.populationid = populationid;
    }
    
    /** full constructor */
    public DB_Population(String populationid, String populationname, int area, String producer,Date samplingtime, int samplingnumber, String comments) {
        this.populationid = populationid;
        this.populationname = populationname;
        this.area = area;
        this.samplingtime = samplingtime;
        this.samplingnumber = samplingnumber;
        this.comments = comments;
        this.producer = producer;
        
    }

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPopulationid() {
		return populationid;
	}

	public void setPopulationid(String populationid) {
		this.populationid = populationid;
	}

	public String getPopulationname() {
		return populationname;
	}

	public void setPopulationname(String populationname) {
		this.populationname = populationname;
	}

	public int getSamplingnumber() {
		return samplingnumber;
	}

	public void setSamplingnumber(int samplingnumber) {
		this.samplingnumber = samplingnumber;
	}

	public Date getSamplingtime() {
		return samplingtime;
	}

	public void setSamplingtime(Date samplingtime) {
		this.samplingtime = samplingtime;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

   public Pop_Population convert2Pop_Population()
   {
	   return new Pop_Population(this);
   }

}