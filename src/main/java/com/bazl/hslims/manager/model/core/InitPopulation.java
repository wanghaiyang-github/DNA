package com.bazl.hslims.manager.model.core;

import java.util.ArrayList;
import java.util.List;

public class InitPopulation
{
	private static InitPopulation m_instance = null;
	protected List<Pop_Population> populations =null;
	protected List<Population> populationList = null;

	private InitPopulation() 
	{
		refresh();
	}

	public void refresh(){
		populations = PopulationControl.getPop_Populations();
		populationList = new ArrayList<Population>();
		for (int i = 0; i < populations.size(); i++)
		{
			Population population = new Population();
			population.populationID = populations.get(i).populationID;
			population.populationname = populations.get(i).populationname;
			population.producer = populations.get(i).producer;
			population.samplingTime = populations.get(i).samplingtime;
			population.comment = populations.get(i).comments;
			populationList.add(population);
		}
		
	}

	synchronized public static InitPopulation getInstance()
	{
		if (m_instance == null) {
			m_instance = new InitPopulation();
		}
		return m_instance;
	}
	
	public Pop_Population[] getPop_Populations()
	{
		return (Pop_Population[])populations.toArray(new Pop_Population[populations.size()]);
	}
	public String[] getPopulationNames()
	{
		String[] populationNames = new String[populations.size()];
		for (int i = 0; i < populationNames.length; i++) {
			populationNames[i] = populations.get(i).populationname;
		}
		return populationNames;
	}
	public  Pop_Population getPopulationByID(String populationID)
	{
		Pop_Population population = new Pop_Population();
		for(int i = 0; i < populations.size();i++)
		{
			//if(populations.get(i).populationname.compareTo(populationName)==0)
			if(populations.get(i).populationID.compareTo(populationID)==0)
			{
				population = populations.get(i);
				break;
			}
		}
		return population;
	}
	
	public Population[] getPopulationList()
	{
		return (Population[]) populationList.toArray(new Population[populationList.size()]);
	}
}
