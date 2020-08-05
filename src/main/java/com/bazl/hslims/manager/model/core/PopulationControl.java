package com.bazl.hslims.manager.model.core;

import com.bazl.hslims.manager.model.dataBase.DBHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PopulationControl {

	public static List<Pop_Population> getPop_Populations() {
		List<Pop_Population> popPopulations = new ArrayList<Pop_Population>();

		List<DB_Population> dbPopulations = getDB_Populations();
		for (int i = 0; i < dbPopulations.size(); i++) {
			DB_Population dbPopulation = dbPopulations.get(i);
			Pop_Population popPopulation = dbPopulation.convert2Pop_Population();
			popPopulations.add(popPopulation);

			List<DB_Pop_marker> dbPopMarkers = getDB_Pop_markers(dbPopulation.populationid);
			popPopulation.markers = new Pop_Marker[dbPopMarkers.size()];
			for (int j = 0; j < dbPopMarkers.size(); j++) {
				DB_Pop_marker dbPopMarker = dbPopMarkers.get(j);
				Pop_Marker popMarker = dbPopMarker.convert2Pop_Marker();
				popPopulation.markers[j] = popMarker;

				List<DB_AlleleFrequency> dbAlleles = getDB_AlleleFrequency(dbPopulation.populationid, dbPopulation.populationname,
						dbPopMarker.markerName);
				popMarker.alleles = new Pop_Allele[dbAlleles.size()];
				popMarker.minAlleleFreq = 1e-3f;
				double minFreq = 100;
				for (int k = 0; k < dbAlleles.size(); k++) {
					if (dbAlleles.get(k).allelefrequency < minFreq) {
						minFreq = dbAlleles.get(k).allelefrequency;
					}
					popMarker.alleles[k] = dbAlleles.get(k)
							.convert2Pop_Allele();
				}
				if (dbAlleles.size() > 0) {
					popMarker.minAlleleFreq = minFreq;
				}
			}
		}
		return popPopulations;
	}

	public static List<DB_Population> getDB_Populations() {
		List<DB_Population> dbPopulations = new ArrayList<DB_Population>();
		DBHelper db = null;
		ResultSet rs = null;
		String sql = null;

		try {
			sql = "SELECT * FROM race ";
			db = new DBHelper(sql);
			rs = db.pst.executeQuery();

			while (rs.next()) {
				DB_Population dbPopulation = new DB_Population();
				dbPopulation.populationid = rs.getString("id");
				dbPopulation.populationname = rs.getString("race_name");
				dbPopulation.samplingtime = null;
				dbPopulation.producer = rs.getString("create_person");
				dbPopulation.comments = rs.getString("comments");
				dbPopulation.area = 0;
				dbPopulation.samplingnumber =0;
				dbPopulations.add(dbPopulation);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				db.close();
			} catch (Exception e) {
			}
		}
		return dbPopulations;
	}



	public static List<DB_Pop_marker> getDB_Pop_markers(String populationId) {
		List<DB_Pop_marker> markers = new ArrayList<DB_Pop_marker>();
		DBHelper db = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "SELECT * FROM marker_info ";
			db = new DBHelper(sql);
			rs = db.pst.executeQuery(sql);

			while(rs.next())
			{
				DB_Pop_marker marker = new DB_Pop_marker();
				marker.populationID = populationId;
				marker.markerName = rs.getString("marker_name");
				marker.checkNum = 0;
				marker.comments = rs.getString("marker_desc");
				markers.add(marker);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try{
				rs.close();
				db.close();
			}
			catch(Exception e){}
		}
		return markers;
	}

	public static List<DB_AlleleFrequency> getDB_AlleleFrequency(String populationid, String populationname,String markerName) {
		List<DB_AlleleFrequency> alleleFrequencys = new ArrayList<DB_AlleleFrequency>();
		DBHelper db = null;
		ResultSet rs = null;
		String sql = null;
		try {
			sql = "SELECT * FROM allele_frequency where RACE_NAME='"+ populationname +"' and MARKER_NAME='"+ markerName+"'";
			db = new DBHelper(sql);
			rs = db.pst.executeQuery(sql);

			while(rs.next())
			{
				DB_AlleleFrequency alleleFrequency = new DB_AlleleFrequency();
				alleleFrequency.populationid = populationid;
				alleleFrequency.markername = markerName;
				alleleFrequency.alleleSize = 0;
				alleleFrequency.allelefrequency = rs.getDouble("allele_frequency");
				alleleFrequency.allelename = rs.getString("allele_name");

				alleleFrequencys.add(alleleFrequency);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			try{
				rs.close();
				db.close();
			}
			catch(Exception e){}
		}
		return alleleFrequencys;
	}

}
