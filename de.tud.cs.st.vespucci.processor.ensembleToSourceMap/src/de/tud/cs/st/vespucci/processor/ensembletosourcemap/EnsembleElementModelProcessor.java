package de.tud.cs.st.vespucci.processor.ensembletosourcemap;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import sae.LazyView;
import sae.operators.BagProjection;
import sae.operators.Projection;
import scala.Tuple2;
import unisson.model.UnissonDatabase;
import unisson.query.code_model.SourceElement;
import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.Util;

public class EnsembleElementModelProcessor implements IModelProcessor {

	/**
	 * 
	 */
	@Override
	public Object processModel(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);

		IProject project = diagramFile.getProject();

		UnissonDatabase database = ArchitectureDatabaseProvider.getInstance()
				.getArchitectureDatabase(project);

		ArchitectureDatabaseProvider.getInstance().initializeDatabase(project);

		ArchitectureDatabaseProvider.getInstance().addModelFileToProject(
				diagramFile, project);

		LazyView<Tuple2<IEnsemble, SourceElement<Object>>> global_ensemble_elements = database
				.leaf_ensemble_elements();

		EnsembleElementListView ensembleElementListView = new EnsembleElementListView(
				createEnsembleToCodeProjection(global_ensemble_elements));

		Iterator<IPair<IEnsemble, ICodeElement>> iterator = ensembleElementListView
				.iterator();
		while (iterator.hasNext()) {
			IPair<IEnsemble, ICodeElement> next = iterator.next();
			System.out.println(next.getFirst() + " :: " + next.getSecond());
		}

		return ensembleElementListView;
	}

	/**
	 * This processor does not return any values
	 */
	@Override
	public Class<?> resultClass() {
		return IEnsembleElementList.class;
	}

	/**
	 * Creates a database view that pairs the results of an ensemble query with
	 * the ensemble itself.
	 * 
	 * @param ensemble
	 * @param view
	 * @return
	 */
	private Projection<Tuple2<IEnsemble, SourceElement<Object>>, IPair<IEnsemble, ICodeElement>> createEnsembleToCodeProjection(
			final LazyView<Tuple2<IEnsemble, SourceElement<Object>>> view) {
		return new BagProjection<Tuple2<IEnsemble, SourceElement<Object>>, IPair<IEnsemble, ICodeElement>>(
				new ScalaFunction1Wrapper<Tuple2<IEnsemble, SourceElement<Object>>, IPair<IEnsemble, ICodeElement>>() {

					@Override
					public IPair<IEnsemble, ICodeElement> apply(
							final Tuple2<IEnsemble, SourceElement<Object>> arg0) {
						return new IPair<IEnsemble, ICodeElement>() {

							@Override
							public IEnsemble getFirst() {
								return arg0._1;
							}

							@Override
							public ICodeElement getSecond() {
								return arg0._2;
							}
						};
					}

				}, view);
	}

}
