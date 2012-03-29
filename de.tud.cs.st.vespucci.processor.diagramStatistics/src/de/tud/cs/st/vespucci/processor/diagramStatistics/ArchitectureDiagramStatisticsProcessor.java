package de.tud.cs.st.vespucci.processor.diagramStatistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

import sae.bytecode.model.dependencies.Dependency;
import sae.collections.Conversions;
import sae.collections.QueryResult;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.JavaConversions;
import unisson.model.UnissonDatabase;
import unisson.model.kinds.DependencyKind;
import unisson.query.code_model.SourceElement;
import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.ModelUtils;
import de.tud.cs.st.vespucci.utilities.Util;

public class ArchitectureDiagramStatisticsProcessor implements IModelProcessor {

	private static String constraintHeader = "FromFull;From;ToFull;To;Type;Kinds;Diagram";

	private static String ensembleHeader = "Name;FullName;Parent;isTopLevel;numberOfChildren";

	private static String ensembleElementsHeader = "FullName;Element";

	private static String dependenciesHeader = "Source;Target;Kind";

	@Override
	public Object processModel(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);
		if (diagramFile.getLocation() == null)
			return null;
		IArchitectureModel model = Util.adapt(diagramModel,
				IArchitectureModel.class);

		printConstraintStatistics(model, diagramFile);

		printConstraintSubsumtions(model, diagramFile);

		printEnsembleStatistics(model, diagramFile);
		// printEnsembleElements(diagramFile);
		// printDependencies(diagramFile);
		return null;
	}

	private void printConstraintSubsumtions(IArchitectureModel model,
			IFile diagramFile) {
		
		IPath constraintSubsumptionsPath = diagramFile.getLocation()
				.removeLastSegments(1).append("constraint-subsumptions")
				.addFileExtension("csv");
		
		IPath constraintSubsumptionsAggregatedPath = diagramFile.getLocation()
				.removeLastSegments(1).append("constraint-subsumptions-aggregated")
				.addFileExtension("csv");

		PrintWriter subsumptionsWriter = openOrCreateFile(constraintSubsumptionsPath,  "From;To;Type;SubFrom;SubTo");
		PrintWriter subsumptionsAggregatedWriter = openOrCreateFile(constraintSubsumptionsAggregatedPath,  "From;To;Type;SubsumptionCount");
		
		if(subsumptionsWriter == null || subsumptionsAggregatedWriter == null)
			return;
		
		UnissonDatabase database = ArchitectureDatabaseProvider.getInstance()
				.getArchitectureDatabase(diagramFile.getProject());
		QueryResult<Tuple3<IEnsemble, IEnsemble, Object>> ensembleDependencies = Conversions
				.lazyViewToResult(database.ensembleDependencies());
		scala.collection.immutable.List<Tuple3<IEnsemble, IEnsemble, Object>> scalaList = ensembleDependencies
				.asList();
		List<Tuple3<IEnsemble, IEnsemble, Object>> dependencies = JavaConversions
				.seqAsJavaList(scalaList);

		Set<IConstraint> constraints = model.getConstraints();

		for (IConstraint constraint : constraints) {
			IPair<IEnsemble, IEnsemble> constraintRelation = ModelUtils.Pair(
					constraint.getSource(), constraint.getTarget());
			
			int aggregatedCount = 0;
			
			for (Tuple3<IEnsemble, IEnsemble, Object> dependency : dependencies) {
				IPair<IEnsemble, IEnsemble> dependencyRelation = ModelUtils
						.Pair(dependency._1(), dependency._2());
				if (constraintRelation.equals(dependencyRelation)
						|| subsumes(constraintRelation, dependencyRelation)) {
					aggregatedCount ++;
					subsumptionsWriter.print(constraintRelation.getFirst());
					subsumptionsWriter.print(";");
					subsumptionsWriter.print(constraintRelation.getSecond());
					subsumptionsWriter.print(";");
					subsumptionsWriter.print(ModelUtils.getConstraintType(constraint));
					subsumptionsWriter.print(";");
					subsumptionsWriter.print(dependencyRelation.getFirst());
					subsumptionsWriter.print(";");
					subsumptionsWriter.print(dependencyRelation.getSecond());
					subsumptionsWriter.println();
				}
			}
			
			subsumptionsAggregatedWriter.print(constraintRelation.getFirst());
			subsumptionsAggregatedWriter.print(";");
			subsumptionsAggregatedWriter.print(constraintRelation.getSecond());
			subsumptionsAggregatedWriter.print(";");
			subsumptionsAggregatedWriter.print(ModelUtils.getConstraintType(constraint));
			subsumptionsAggregatedWriter.print(";");
			subsumptionsAggregatedWriter.print(aggregatedCount);
			subsumptionsAggregatedWriter.println();
		}
		subsumptionsWriter.close();
		subsumptionsAggregatedWriter.close();
	}

	private PrintWriter openOrCreateFile(IPath filePath, String header) {
		File statisticsFile = filePath.toFile();
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
			return null;
		}

		if (writeHeader) {
			writer.println(header);
		}
		return writer;
	}

	private static boolean subsumes(IPair<IEnsemble, IEnsemble> parent,
			IPair<IEnsemble, IEnsemble> child) {
		boolean subsumesFirst = false;
		IEnsemble firstParent = child.getFirst().getParent();
		while (firstParent != null) {
			if (firstParent.equals(parent.getFirst())) {
				subsumesFirst = true;
				break;
			}
			firstParent = firstParent.getParent();
		}
		boolean subsumesSecond = false;
		IEnsemble secondParent = child.getSecond().getParent();
		while (secondParent != null) {
			if (secondParent.equals(parent.getSecond())) {
				subsumesSecond = true;
				break;
			}
			secondParent = secondParent.getParent();
		}
		return (subsumesFirst && parent.getSecond().equals(child.getSecond())) || (parent.getFirst().equals(child.getFirst()) && subsumesSecond) || (subsumesFirst && subsumesSecond); 
	}

	private void printDependencies(IFile diagramFile) {
		IProject project = diagramFile.getProject();
		UnissonDatabase database = ArchitectureDatabaseProvider.getInstance()
				.getArchitectureDatabase(project);

		QueryResult<Tuple2<DependencyKind, Dependency<Object, Object>>> dependencies = Conversions
				.lazyViewToResult(database.kind_and_dependency());

		scala.collection.immutable.List<Tuple2<DependencyKind, Dependency<Object, Object>>> scalaList = dependencies
				.asList();
		List<Tuple2<DependencyKind, Dependency<Object, Object>>> list = JavaConversions
				.seqAsJavaList(scalaList);
		IPath elememtStatisticsPath = diagramFile.getLocation()
				.removeLastSegments(1).append("dependencies")
				.addFileExtension("csv");

		File statisticsFile = elememtStatisticsPath.toFile();
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
			writer.println(dependenciesHeader);
		for (Tuple2<DependencyKind, Dependency<Object, Object>> entry : list) {
			writer.print(entry._2.source().toString());
			writer.print(";");
			writer.print(entry._2.target().toString());
			writer.print(";");
			writer.print(entry._1.designator());
			writer.println();
		}
		writer.close();

	}

	private void printEnsembleElements(IFile diagramFile) {
		IProject project = diagramFile.getProject();
		UnissonDatabase database = ArchitectureDatabaseProvider.getInstance()
				.getArchitectureDatabase(project);
		QueryResult<Tuple2<IEnsemble, SourceElement<Object>>> elements = Conversions
				.lazyViewToResult(database.leaf_ensemble_elements());
		scala.collection.immutable.List<Tuple2<IEnsemble, SourceElement<Object>>> scalaList = elements
				.asList();
		List<Tuple2<IEnsemble, SourceElement<Object>>> list = JavaConversions
				.seqAsJavaList(scalaList);
		IPath elememtStatisticsPath = diagramFile.getLocation()
				.removeLastSegments(1).append("ensemble-elements")
				.addFileExtension("csv");

		File statisticsFile = elememtStatisticsPath.toFile();
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
			writer.println(ensembleElementsHeader);
		for (Tuple2<IEnsemble, SourceElement<Object>> entry : list) {
			writer.print(ModelUtils.getFullEnsembleName(entry._1));
			writer.print(";");
			writer.print(entry._2.toString());
			writer.println();
		}
		writer.close();
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
			writer.println(ModelUtils.getFullEnsembleName(constraint
					.getSource())
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
					+ ";" + (ensemble.getParent() != null ? "0" : "1") + ";"
					+ allChildren(ensemble).size());
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
