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
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;
import de.tud.cs.st.vespucci.interfaces.IViolationView;

/**
 * 
 * 
 * @author Ralf Mitschke
 * 
 */
public class ViolationView implements IViolationView, Observer<IViolation> {

	private final LazyView<IViolation> diagramViolations;

	private final IDataView<IViolationSummary> summaryView;

	private final Set<IDataViewObserver<IViolation>> observers;

	private QueryResult<IViolation> cachedResults;

	private final IFile diagramFile;

	private final String diagramFileName;

	public ViolationView(LazyView<IViolation> databaseViolations, IFile diagramFile,
			IDataView<IViolationSummary> summaryView) {
		this.diagramFile = diagramFile;
		this.diagramFileName = diagramFile.getFullPath().makeAbsolute()
				.toPortableString();
		this.summaryView = summaryView;
		this.diagramViolations = new LazySelection<IViolation>(
				new ScalaFunction1Wrapper<IViolation, Object>() {

					@Override
					public Object apply(IViolation arg0) {
						return Boolean.valueOf(diagramFileName.equals(arg0.getDiagramFile()));
					}

				}, databaseViolations);
		observers = new HashSet<IDataViewObserver<IViolation>>();

		this.diagramViolations.addObserver(this);
	}

	@Override
	public Iterator<IViolation> iterator() {
		if (cachedResults == null)
			cachedResults = Conversions.lazyViewToResult(diagramViolations);

		List<IViolation> list = cachedResults.asList();

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
		summaryView.dispose();
		// remove my diagram violations observers
		diagramViolations.clearObservers();
		// remove my diagram violations selection from the database
		diagramViolations
				.clearObserversForChildren(new ScalaFunction1Wrapper<Observable<?>, Object>() {

					@Override
					public Object apply(Observable<?> arg0) {
						// don't recurse removals into the databaseViolations (only child), even if no more observers are there
						return Boolean.FALSE;
					}

				});
	}

	@Override
	public void register(IDataViewObserver<IViolation> observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(IDataViewObserver<IViolation> observer) {
		observers.remove(observer);
	}

	@Override
	public void added(IViolation arg0) {

		// System.out.println("  added: " + arg0);

		for (IDataViewObserver<IViolation> o : observers) {
			o.added(arg0);
		}
	}

	@Override
	public void removed(IViolation arg0) {

		// System.out.println("removed: " + arg0);

		for (IDataViewObserver<IViolation> o : observers) {
			o.deleted(arg0);
		}
	}

	@Override
	public void updated(IViolation arg0, IViolation arg1) {

		// System.out.println("updated: " + arg0 + " => " + arg1);

		for (IDataViewObserver<IViolation> o : observers) {
			o.updated(arg0, arg1);
		}
	}

	@Override
	public IDataView<IViolationSummary> getSummaryView() {
		return summaryView;
	}

}
