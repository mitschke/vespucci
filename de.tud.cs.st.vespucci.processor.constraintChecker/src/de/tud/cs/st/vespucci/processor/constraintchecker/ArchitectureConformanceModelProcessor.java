package de.tud.cs.st.vespucci.processor.constraintchecker;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import unisson.model.UnissonDatabase;
import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.utilities.Util;

public class ArchitectureConformanceModelProcessor implements IModelProcessor {

	@Override
	public Object processModel(Object diagramModel) {
		return getViolationView(diagramModel);
	}

	@Override
	public Class<?> resultClass() {
		return IViolationView.class;
	}

	// @SuppressWarnings("deprecation")
	public static IViolationView getViolationView(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);

		IProject project = diagramFile.getProject();

		UnissonDatabase db = ArchitectureDatabaseProvider.getInstance()
				.getArchitectureDatabase(project);

		// @SuppressWarnings("unchecked")
		// QueryResult<Object> constraints =
		// Conversions.lazyViewToResult(db.normalized_constraints());

		SummaryView summaryView = new SummaryView(db.violation_summary(),
				diagramFile);

		ViolationView violationView = new ViolationView(db.violations(),
				diagramFile, summaryView);

		ArchitectureDatabaseProvider.getInstance().initializeDatabase(project);

		long start = System.nanoTime();
		ArchitectureDatabaseProvider.getInstance().addModelFileToProject(
				diagramFile, project);
		long taken = System.nanoTime() - start;
		System.out
				.println("time to addd model         : "
						+ TimeUnit.SECONDS.convert(taken, TimeUnit.NANOSECONDS)
						+ " ms");

		// List<Object> list = JavaConversions.asJavaList(constraints.asList());
		// for (Object object : list) {
		// System.out.println(object);
		// }

		return violationView;
	}

}
