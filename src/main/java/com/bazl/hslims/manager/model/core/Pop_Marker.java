package com.bazl.hslims.manager.model.core;

public class Pop_Marker {

	// public String markerid;
	public String markername;
	public int checkNumber;
	public String comments;
	public double minAlleleFreq;

	public Pop_Allele[] alleles;

	public double GetAlleleFreqs(String allelename) {
		for (int j = 0; j < alleles.length; j++) {
			if (alleles[j].allelename.compareTo(allelename) == 0) {
				return alleles[j].alleleFrequncy;
			}
		}
		
		// Cannot find the allele frequency. Wait for further operations.
		return 1.0d;
	}

	public Pop_Marker() {
	}

	public Pop_Marker(String markername) {
		this.markername = markername;
	}

	public Pop_Marker(String markername, int checkNumber, String comments) {
		// super();
		this.markername = markername;
		this.checkNumber = checkNumber;
		this.comments = comments;
	}

	/**
	 * get minimum allele frequency
	 * 
	 * @return allele frequency if succeeded; 1.0d if no allele is found
	 */
	public Double getMinAlleleFreq() {
		return getTopAlleleFreq(AF_MIN);
	}

	/**
	 * get maximum allele frequency
	 * 
	 * @return allele frequency if succeeded; 1.0d if no allele is found
	 */
	public Double getMaxAlleleFreq() {
		return getTopAlleleFreq(AF_MAX);
	}
	
	final int AF_MIN = 0;
	final int AF_MAX = 1;
	private Double getTopAlleleFreq(int type) {
		Double af = 0.0d;
		
		if(alleles.length > 0)
			af = alleles[0].alleleFrequncy;		
		for (int i = 1; i < alleles.length; i++)
			if (alleles[i].alleleFrequncy > af) {
				if(type == AF_MAX)
					af = alleles[i].alleleFrequncy;
			} else {
				if(type == AF_MIN)
					af = alleles[i].alleleFrequncy;
			}
		
		return (af <= 0.0) ? Double.NaN : af;
	}
}
