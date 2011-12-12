package de.tud.cs.st.vespucci.processor.ensembletosourcemap;

import scala.util.parsing.combinator.Parsers;
import scala.util.parsing.combinator.Parsers.Failure;
import scala.util.parsing.combinator.Parsers.ParseResult;
import unisson.query.UnissonQuery;
import unisson.query.parser.QueryParser;
import de.tud.cs.st.vespucci.diagram.model.output.spi.Ensemble;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

public class EnsembleElementModelProcessor implements IModelProcessor {

	@Override
	public Object processModel(Object diagramModel) {
		
		IArchitectureModel model = Util.adapt(diagramModel, IArchitectureModel.class);
		
		for (IEnsemble ensemble : model.getEnsembles()) {
			System.out.println("parsing query for ensemble: " + ensemble.getName());
			
			String query = ensemble.getQuery();
			System.out.println("query: " + ensemble.getQuery());
			
			QueryParser parser = new QueryParser();
			ParseResult<UnissonQuery> result = parser.parse(query);
			
			if( result.$outer instanceof Parsers.Failure )
			{
				Failure failure = (Failure) result.$outer;
				System.out.println(failure.msg());
				System.out.println(failure.next().pos().longString());
				
				
			}
			else
			{
				UnissonQuery unissonQuery = result.get();
				System.out.println(unissonQuery.toString());
			}
			
		}
		
		return null;
	}

	
	
	
	
	/**
	 * This processor does not return any values 
	 */
	@Override
	public Class<?> resultClass() {
		return null;
	}

}
