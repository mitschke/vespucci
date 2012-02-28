package de.tud.cs.st.vespucci.processor.constraintchecker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import sae.LazyView;
import sae.Observable;
import sae.Observer;
import sae.collections.Conversions;
import sae.collections.QueryResult;
import sae.operators.LazySelection;
import scala.collection.immutable.List;
import de.tud.cs.st.scala.utilitites.ScalaFunction1Wrapper;
import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;

public class SummaryView implements IDataView<IViolationSummary>,
		Observer<IViolationSummary> {

	private final Set<IDataViewObserver<IViolationSummary>> observers;

	private final LazyView<IViolationSummary> diagramSummary;

	private QueryResult<IViolationSummary> cachedResults;

	private final IFile diagramFile;

	private final String diagramFileName;

	public SummaryView(LazyView<IViolationSummary> databaseSummary,
			IFile diagramFile) {
		this.observers = new HashSet<IDataViewObserver<IViolationSummary>>();
		this.diagramSummary = new LazySelection<IViolationSummary>(
				new ScalaFunction1Wrapper<IViolationSummary, Object>() {

					@Override
					public Object apply(IViolationSummary arg0) {
						return Boolean.valueOf(diagramFileName.equals(arg0
								.getDiagramFile()));
					}

				}, databaseSummary);
		this.diagramFile = diagramFile;
		this.diagramFileName = diagramFile.getFullPath().makeAbsolute()
				.toPortableString();
		this.diagramSummary.addObserver(this);
	}

	@Override
	public Iterator<IViolationSummary> iterator() {
		if (cachedResults == null)
			cachedResults = Conversions.lazyViewToResult(diagramSummary);

		List<IViolationSummary> list = cachedResults.asList();

		return scala.collection.JavaConversions.asJavaIterator(list.iterator());
	}

	@Override
	public void dispose() {
		IProject project = diagramFile.getProject();
		// 1. remove all violations by removing the diagram
		ArchitectureDatabaseProvider.getInstance().removeModelFileFromProject(
				diagramFile, project);
		// 2. tear down all observers
		cachedResults = null;
		observers.clear();
		// remove my diagram violations observers
		diagramSummary.clearObservers();
		// remove my diagram violations selection from the database
		diagramSummary
				.clearObserversForChildren(new ScalaFunction1Wrapper<Observable<?>, Object>() {

					@Override
					public Object apply(Observable<?> arg0) {
						// don't recurse removals into the databaseViolations (only child), even if no more observers are there
						return Boolean.FALSE;
					}

				});
	}

	@Override
	public void register(IDataViewObserver<IViolationSummary> observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(IDataViewObserver<IViolationSummary> observer) {
		observers.remove(observer);
	}

	@Override
	public void added(IViolationSummary arg0) {

		// System.out.println("  added: " + arg0);

		for (IDataViewObserver<IViolationSummary> observer : observers) {
			observer.added(arg0);
		}
	}

	@Override
	public void removed(IViolationSummary arg0) {

		// System.out.println("removed: " + arg0);

		for (IDataViewObserver<IViolationSummary> observer : observers) {
			observer.deleted(arg0);
		}
	}

	@Override
	public void updated(IViolationSummary arg0, IViolationSummary arg1) {

		// System.out.println("updated: " + arg0 + " => " + arg1);

		for (IDataViewObserver<IViolationSummary> observer : observers) {
			observer.updated(arg0, arg1);
		}
	}

}
