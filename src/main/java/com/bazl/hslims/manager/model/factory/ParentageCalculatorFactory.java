package com.bazl.hslims.manager.model.factory;


import com.bazl.hslims.manager.model.basedata.impl.CachedPopulationAlleleFrequencyProvider;
import com.bazl.hslims.manager.model.core.InitPopulation;
import com.bazl.hslims.manager.model.core.Pop_Allele;
import com.bazl.hslims.manager.model.core.Pop_Marker;
import com.bazl.hslims.manager.model.core.Pop_Population;
import com.bazl.hslims.manager.model.relative.ParentageCalculator;
import com.bazl.hslims.manager.model.relative.impl.EssenMollerParentageCalculator;

public class ParentageCalculatorFactory {

	private CachedPopulationAlleleFrequencyProvider freqProvider;
	private EssenMollerParentageCalculator essenMollerParentageCalculator;
	
	private ParentageCalculatorFactory() {
		
		// 构造EssenMollerParentageCalculator:
		
		freqProvider = new CachedPopulationAlleleFrequencyProvider();

		freqProvider.clear();

		Pop_Population[] populationList = InitPopulation.getInstance().getPop_Populations();
		for(Pop_Population population : populationList) {
			Pop_Marker[] markers = population.markers;
			for (Pop_Marker marker : markers) {
				Pop_Allele[] popAelles = marker.alleles;
				for (Pop_Allele popAelle : popAelles) {
					freqProvider.set(population.populationID, marker.markername, popAelle.allelename,
							popAelle.alleleFrequncy);
				}
			}
		}
		
		
		essenMollerParentageCalculator = new EssenMollerParentageCalculator();
		essenMollerParentageCalculator.setPopulationAlleleFrequencyProvider(freqProvider);
	}

	static private ParentageCalculatorFactory instance = new ParentageCalculatorFactory();
	
	static public ParentageCalculatorFactory getInstance() {
		return instance;
	}
	
	public ParentageCalculator getCalculator(String calculatorName) {
		if (calculatorName == "EssenMollerParentageCalculator") {
			return essenMollerParentageCalculator;
		} else
			return null;
	}
}
