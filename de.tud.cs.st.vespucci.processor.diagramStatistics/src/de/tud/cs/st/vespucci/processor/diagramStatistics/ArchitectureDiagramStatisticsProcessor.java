package de.tud.cs.st.vespucci.processor.diagramStatistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.ModelUtils;
import de.tud.cs.st.vespucci.utilities.Util;

public class ArchitectureDiagramStatisticsProcessor implements IModelProcessor {

	private static String constraintHeader = "FromFull;From;ToFull;To;Type;Kinds;Diagram";

	private static String ensembleHeader = "Name;FullName;Parent;isTopLevel;numberOfChildren";

	@Override
	public Object processModel(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);
		if (diagramFile.getLocation() == null)
			return null;
		IArchitectureModel model = Util.adapt(diagramModel,
				IArchitectureModel.class);

		printConstraintStatistics(model, diagramFile);
		printEnsembleStatistics(model, diagramFile);
		return null;
	}

	private void printConstraintStatistics(IArchitectureModel model,
			IFile diagramFile) {
		IPath constraintStatisticsPath = diagramFile.getLocation()
				.removeLastSegments(1).append("constraint-statistics")
				.addFileExtension("csv");

		File statisticsFile = constraintStatisticsPath.toFile();
		PrintWriter writer = null;
		boolean writeHeader = false;
		try {
			if (!statisticsFile.exists()) {
				statisticsFile.createNewFile();
				writeHeader = true;
			}
			writer = new PrintWriter(new FileWriter(statisticsFile, true));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if (writeHeader)
			writer.println(constraintHeader);
		Set<IConstraint> constraints = model.getConstraints();
		for (IConstraint constraint : constraints) {
			writer.println(
					ModelUtils.getFullEnsembleName(constraint.getSource())
					+ ";" 
					+ constraint.getSource().getName()
					+ ";"
					+ ModelUtils.getFullEnsembleName(constraint.getTarget())
					+ ";"
					+ constraint.getTarget().getName()
					+ ";"
					+ ModelUtils.getConstraintType(constraint)
					+ ";"
					+ constraint.getDependencyKind()
					+ ";"
					+ diagramFile.getFullPath().toPortableString());
		}

		writer.close();
	}

	private void printEnsembleStatistics(IArchitectureModel model,
			IFile diagramFile) {
		IPath constraintStatisticsPath = diagramFile.getLocation()
				.removeLastSegments(1).append("ensemble-statistics")
				.addFileExtension("csv");

		File statisticsFile = constraintStatisticsPath.toFile();
		PrintWriter writer = null;
		boolean writeHeader = false;
		try {
			if (!statisticsFile.exists()) {
				statisticsFile.createNewFile();
				writeHeader = true;
			}
			writer = new PrintWriter(new FileWriter(statisticsFile, true));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if (writeHeader)
			writer.println(ensembleHeader);

		Set<IEnsemble> topLevelEnsembles = model.getEnsembles();

		Set<IEnsemble> ensembles = new HashSet<IEnsemble>();

		for (IEnsemble ensemble : topLevelEnsembles) {
			ensembles.add(ensemble);
			ensembles.addAll(allChildren(ensemble));
		}

		for (IEnsemble ensemble : ensembles) {

			writer.println(ensemble.getName()
					+ ";"
					+ ModelUtils.getFullEnsembleName(ensemble)
					+ ";"
					+ (ensemble.getParent() != null ? ModelUtils
							.getFullEnsembleName(ensemble.getParent()) : "")
					+ ";"
					+ (ensemble.getParent() != null ? "0" : "1")
					+ ";"
					+ allChildren(ensemble).size()
					);
		}

		writer.close();
	}

	public static Set<IEnsemble> allChildren(IEnsemble ensemble) {
		Set<IEnsemble> children = new HashSet<IEnsemble>(ensemble
				.getInnerEnsembles().size());
		children.addAll(ensemble.getInnerEnsembles());
		for (IEnsemble child : ensemble.getInnerEnsembles()) {
			children.addAll(allChildren(child));
		}
		return children;
	}

	@Override
	public Class<?> resultClass() {
		return null;
	}

}
