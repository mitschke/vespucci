package de.tud.cs.st.vespucci.processor.ensembletosourcemap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import sae.LazyView;
import sae.bytecode.Database;
import sae.operators.BagProjection;
import sae.operators.BagUnion;
import sae.operators.Projection;
import sae.operators.Union;
import scala.util.parsing.combinator.Parsers.Failure;
import scala.util.parsing.combinator.Parsers.ParseResult;
import unisson.query.UnissonQuery;
import unisson.query.code_model.SourceElement;
import unisson.query.compiler.QueryCompiler;
import unisson.query.parser.QueryParser;
import de.tud.cs.st.vespucci.database.bytecode.provider.BytecodeDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.Util;

public class EnsembleElementModelProcessor implements IModelProcessor {

	/**
	 * TODO refactoring
	 * 
	 */
	@Override
	public Object processModel(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);

		IArchitectureModel model = Util.adapt(diagramModel,
				IArchitectureModel.class);

		IProject project = diagramFile.getProject();

		Database database = BytecodeDatabaseProvider.getInstance().getBytecodeDatabase(
				project);

		List<Projection<SourceElement<Object>, IPair<IEnsemble, ICodeElement>>> mappings = new ArrayList<Projection<SourceElement<Object>, IPair<IEnsemble, ICodeElement>>>(
				model.getEnsembles().size());

		for (final IEnsemble ensemble : model.getEnsembles()) {

			UnissonQuery query;
			try {
				query = getQuery(ensemble);
			} catch (UnissonParserException e) {
				reportParserError(diagramFile, ensemble, e.getMessage());
				continue;
			}

			LazyView<SourceElement<Object>> view = getView(query, database);

			Projection<SourceElement<Object>, IPair<IEnsemble, ICodeElement>> ensembleToCode = createEnsembleToCodeProjection(
					ensemble, view);

			mappings.add(ensembleToCode);
		}

		BytecodeDatabaseProvider.getInstance().initializeDatabase(project);

		if (mappings.size() == 0) {
			return new EmptyEnsembleElementList();
		}
		if (mappings.size() == 1) {
			
			EnsembleElementListView view = new EnsembleElementListView(mappings.get(0));
			
			for (Iterator<IPair<IEnsemble, ICodeElement>> iterator = view.iterator(); iterator.hasNext();) {
				IPair<IEnsemble, ICodeElement> next = (IPair<IEnsemble, ICodeElement>) iterator.next();
				System.out.println(next.getFirst().getName() + " :: " + next.getSecond());
			}

			return view;
		}
		// we have two or more mappings

		Union<IPair<IEnsemble, ICodeElement>, IPair<IEnsemble, ICodeElement>, IPair<IEnsemble, ICodeElement>> union = createUnion(
				mappings.get(0), mappings.get(1));

		for (int i = 2; i < mappings.size(); i++) {
			union = createUnion(mappings.get(i), union);
		}

		EnsembleElementListView view = new EnsembleElementListView(union);
		
		for (Iterator<IPair<IEnsemble, ICodeElement>> iterator = view.iterator(); iterator.hasNext();) {
			IPair<IEnsemble, ICodeElement> next = (IPair<IEnsemble, ICodeElement>) iterator.next();
			System.out.println(next.getFirst().getName() + " :: " + next.getSecond());
		}
		return view;
	}

	/**
	 * 
	 * @param diagramFile
	 * @param msg
	 */
	private void reportParserError(IFile diagramFile, IEnsemble ensemble, String msg) {
		try {
			IMarker marker = diagramFile
					.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, msg);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.TRANSIENT, true);
			marker.setAttribute(IMarker.LOCATION, ensemble.getName());
			marker.setAttribute(IMarker.PROBLEM, "Query");
		} catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

	}

	/**
	 * Returns the databse view for the given ensemble or null.
	 * 
	 * @param ensemble
	 */
	private LazyView<SourceElement<Object>> getView(UnissonQuery query,
			Database database) {

		QueryCompiler compiler = new QueryCompiler(database);
		LazyView<SourceElement<Object>> view = compiler.compile(query);
		return view;
	}

	private static class UnissonParserException extends Exception {

		private static final long serialVersionUID = -5057631942702026567L;

		public UnissonParserException(String message) {
			super(message);
		}

	}

	/**
	 * 
	 * @param ensemble
	 * @return
	 * @throws UnissonParserException
	 */
	private UnissonQuery getQuery(IEnsemble ensemble)
			throws UnissonParserException {
		String query = ensemble.getQuery();
		QueryParser parser = new QueryParser();
		ParseResult<UnissonQuery> result = parser.parse(query);
		
		
		if (result.getClass().isAssignableFrom(Failure.class)) {
			Failure failure = Failure.class.cast(result);
			
			// if printed in this way the second line will have a nice arrow
			// marker at the position of the error
			// System.out.println(failure.msg());
			// System.out.println(failure.next().pos().longString());

			throw new UnissonParserException(failure.msg());

		}
		UnissonQuery unissonQuery = result.get();
		return unissonQuery;
	}

	/**
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	Union<IPair<IEnsemble, ICodeElement>, IPair<IEnsemble, ICodeElement>, IPair<IEnsemble, ICodeElement>> createUnion(
			LazyView<IPair<IEnsemble, ICodeElement>> left,
			LazyView<IPair<IEnsemble, ICodeElement>> right) {
		return new BagUnion<IPair<IEnsemble, ICodeElement>, IPair<IEnsemble, ICodeElement>, IPair<IEnsemble, ICodeElement>>(
				left, right);
	}

	/**
	 * Creates a database view that pairs the results of an ensemble query with
	 * the ensemble itself.
	 * 
	 * @param ensemble
	 * @param view
	 * @return
	 */
	private Projection<SourceElement<Object>, IPair<IEnsemble, ICodeElement>> createEnsembleToCodeProjection(
			final IEnsemble ensemble, LazyView<SourceElement<Object>> view) {
		return new BagProjection<SourceElement<Object>, IPair<IEnsemble, ICodeElement>>(
				new ScalaFunction1Wrapper<SourceElement<Object>, IPair<IEnsemble, ICodeElement>>() {

					@Override
					public IPair<IEnsemble, ICodeElement> apply(
							final SourceElement<Object> arg0) {
						return new IPair<IEnsemble, ICodeElement>() {

							@Override
							public IEnsemble getFirst() {
								return ensemble;
							}

							@Override
							public ICodeElement getSecond() {
								return (ICodeElement) arg0;
							}
						};
					}
				}, view);
	}

	/**
	 * This processor does not return any values
	 */
	@Override
	public Class<?> resultClass() {
		return IEnsembleElementList.class;
	}

}
