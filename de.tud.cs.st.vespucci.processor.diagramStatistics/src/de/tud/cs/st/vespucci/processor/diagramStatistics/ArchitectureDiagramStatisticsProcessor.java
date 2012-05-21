package de.tud.cs.st.vespucci.processor.diagramStatistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

	private static String constraintHeader = "FromFull;From;ToFull;To;Type;Kinds;Diagram;FromOuter;ToOuter;FromIsTop;ToIsTop";

	private static String ensembleHeader = "Diagram;Name;FullName;Parent;isTopLevel;numberOfChildren;incoming;outgoing;global incoming;global outgoing;not allowed;documented violation;total;isSourceOrTarget";

	private static String ensembleElementsHeader = "FullName;TopLevelEnsemble;Element";

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

		printEnsemblesPerDiagrams(model, diagramFile);
		printEnsembleElements(diagramFile);
		// printDependencies(diagramFile);
		printEnsembleOverview(model, diagramFile);
		return null;
	}

	private void printConstraintSubsumtions(IArchitectureModel model,
			IFile diagramFile) {

		IPath constraintSubsumptionsPath = diagramFile.getLocation()
				.removeLastSegments(1).append("constraint-subsumptions")
				.addFileExtension("csv");

		IPath constraintSubsumptionsAggregatedPath = diagramFile.getLocation()
				.removeLastSegments(1)
				.append("constraint-subsumptions-aggregated")
				.addFileExtension("csv");

		PrintWriter subsumptionsWriter = openOrCreateFile(
				constraintSubsumptionsPath, "From;To;Type;SubFrom;SubTo");
		PrintWriter subsumptionsAggregatedWriter = openOrCreateFile(
				constraintSubsumptionsAggregatedPath,
				"From;;To;;Type;SubsumptionCount");

		if (subsumptionsWriter == null || subsumptionsAggregatedWriter == null)
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

			Map<IEnsemble, Integer> targets = new HashMap<IEnsemble, Integer>();
			Map<IEnsemble, Integer> sources = new HashMap<IEnsemble, Integer>();

			for (Tuple3<IEnsemble, IEnsemble, Object> dependency : dependencies) {
				IPair<IEnsemble, IEnsemble> dependencyRelation = ModelUtils
						.Pair(dependency._1(), dependency._2());
				if (constraintRelation.equals(dependencyRelation)
						|| subsumes(constraintRelation, dependencyRelation)) {
					if (!targets.containsKey(dependency._2()))
						targets.put(dependency._2(), new Integer(0));
					targets.put(dependency._2(),
							targets.get(dependency._2()) + 1);

					if (!sources.containsKey(dependency._1()))
						sources.put(dependency._1(), new Integer(0));
					sources.put(dependency._1(),
							sources.get(dependency._1()) + 1);

					String type = ModelUtils.getConstraintType(constraint);

					if (!type.equals("in/out")) {

						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(constraintRelation
										.getFirst()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(constraintRelation
										.getSecond()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(type);
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(dependencyRelation
										.getFirst()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(dependencyRelation
										.getSecond()));
						subsumptionsWriter.println();
					} else {
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(constraintRelation
										.getFirst()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(constraintRelation
										.getSecond()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print("incoming");
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(dependencyRelation
										.getFirst()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(dependencyRelation
										.getSecond()));
						subsumptionsWriter.println();
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(constraintRelation
										.getFirst()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(constraintRelation
										.getSecond()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print("outgoing");
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(dependencyRelation
										.getFirst()));
						subsumptionsWriter.print(";");
						subsumptionsWriter.print(ModelUtils
								.getFullEnsembleName(dependencyRelation
										.getSecond()));
						subsumptionsWriter.println();
					}
				}
			}

			for (IEnsemble target : targets.keySet()) {
				if (targets.get(target) == 1)
					continue;
				subsumptionsAggregatedWriter.print(ModelUtils
						.getFullEnsembleName(constraintRelation.getFirst()));
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(ModelUtils
						.getFullEnsembleName(constraintRelation.getSecond()));
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(ModelUtils
						.getFullEnsembleName(target));
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(ModelUtils
						.getConstraintType(constraint));
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(targets.get(target));
				subsumptionsAggregatedWriter.println();

			}

			for (IEnsemble source : sources.keySet()) {
				if (sources.get(source) == 1)
					continue;
				subsumptionsAggregatedWriter.print(ModelUtils
						.getFullEnsembleName(constraintRelation.getFirst()));
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(ModelUtils
						.getFullEnsembleName(source));
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(ModelUtils
						.getFullEnsembleName(constraintRelation.getSecond()));
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(ModelUtils
						.getConstraintType(constraint));
				subsumptionsAggregatedWriter.print(";");
				subsumptionsAggregatedWriter.print(sources.get(source));
				subsumptionsAggregatedWriter.println();

			}

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
		return (subsumesFirst && parent.getSecond().equals(child.getSecond()))
				|| (parent.getFirst().equals(child.getFirst()) && subsumesSecond)
				|| (subsumesFirst && subsumesSecond);
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
			writer.print(ModelUtils.getFullEnsembleName(ModelUtils.getOuterMostEnclosingEnsemble(entry._1)));
			writer.print(";");
			writer.print(entry._2.getClass().getName());
			writer.print(";");
			writer.print("\"" + entry._2.toString() + "\"");
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
			
			if(ModelUtils.getConstraintType(constraint).equals("in/out"))
			{
				String line = getConstraintStatisticLine(diagramFile, constraint);
				String incoming = line.replace("in/out", "incoming");
				String outgoing = line.replace("in/out", "outgoing");
				writer.println(incoming);
				writer.println(outgoing);
			}
			else
			{
				writer.println(getConstraintStatisticLine(diagramFile, constraint));
			}
		}

		writer.close();
	}

	private String getConstraintStatisticLine(IFile diagramFile,IConstraint constraint) {
		return ModelUtils.getFullEnsembleName(constraint
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
				+ diagramFile.getFullPath().lastSegment()
				+ ";"
				+ ModelUtils.getOuterMostEnclosingEnsemble(constraint.getSource()).getName()
				+ ";"
				+ ModelUtils.getOuterMostEnclosingEnsemble(constraint.getTarget()).getName()
				+ ";"
				+ (constraint.getSource().getParent() == null ? "1" : "0")
				+ ";"
				+ (constraint.getTarget().getParent() == null ? "1" : "0");
	}

	private void printEnsemblesPerDiagrams(IArchitectureModel model,
			IFile diagramFile) {
		IPath constraintStatisticsPath = diagramFile.getLocation()
				.removeLastSegments(1).append("ensembles-per-diagram")
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
			writer.println("Diagram;FullName;numberOfChildren");

		Set<IEnsemble> topLevelEnsembles = model.getEnsembles();

		for (IEnsemble ensemble : topLevelEnsembles) {

			writer.print(model.getName() + ";");
			writer.print(ModelUtils.getFullEnsembleName(ensemble) + ";");
			writer.print(allChildren(ensemble).size());
			writer.println();
		}

		writer.close();
	}

	
	private void printEnsembleOverview(IArchitectureModel model,
			IFile diagramFile) {
		IPath path = diagramFile.getLocation()
				.removeLastSegments(1).append("ensemble-overview")
				.addFileExtension("csv");

		PrintWriter writer = openOrCreateFile(path, "Diagram;Name;FullName;Has Parent;Parent;TopParent;Num.Children");

		Set<IEnsemble> topLevelEnsembles = model.getEnsembles();

		Set<IEnsemble> ensembles = new HashSet<IEnsemble>();

		for (IEnsemble ensemble : topLevelEnsembles) {
			ensembles.add(ensemble);
			ensembles.addAll(allChildren(ensemble));
		}

		for (IEnsemble ensemble : ensembles) {

			writer.print(diagramFile.getProjectRelativePath().lastSegment() + ";");
			writer.print(ensemble.getName() + ";");
			writer.print(ModelUtils.getFullEnsembleName(ensemble) + ";");
			writer.print((ensemble.getParent() != null ? "1" : "0") + ";");
			writer.print((ensemble.getParent() != null ? ModelUtils
					.getFullEnsembleName(ensemble.getParent()) : "") + ";");
			writer.print(ModelUtils.getFullEnsembleName(ModelUtils.getOuterMostEnclosingEnsemble(ensemble)) + ";");
			writer.print(allChildren(ensemble).size() + ";");

			writer.println();
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

			writer.print(model.getName() + ";");
			writer.print(ensemble.getName() + ";");
			writer.print(ModelUtils.getFullEnsembleName(ensemble) + ";");
			writer.print((ensemble.getParent() != null ? ModelUtils
					.getFullEnsembleName(ensemble.getParent()) : "") + ";");
			writer.print((ensemble.getParent() != null ? "0" : "1") + ";");
			writer.print(allChildren(ensemble).size() + ";");

			Set<IConstraint> constraints = ensemble.getTargetConnections();
			Map<String, List<IConstraint>> constraintsByType = mapConstraintsByType(constraints);
			writer.print(constraintsByType.get("incoming").size() + ";");
			writer.print(constraintsByType.get("outgoing").size() + ";");
			writer.print(constraintsByType.get("global incoming").size() + ";");
			writer.print(constraintsByType.get("global outgoing").size() + ";");
			writer.print(constraintsByType.get("not allowed").size() + ";");
			writer.print(constraintsByType.get("documented violation").size() + ";");
			writer.print(constraints.size() + ";");
			writer.print((isSourceOrTarget(ensemble, model.getConstraints()) ? "1"
					: "0"));
			writer.println();
		}

		writer.close();
	}

	private boolean isSourceOrTarget(IEnsemble ensemble,
			Set<IConstraint> constraints) {
		for (IConstraint constraint : constraints) {
			if (constraint.getSource().equals(ensemble)
					|| constraint.getTarget().equals(ensemble))
				return true;
		}
		return false;
	}

	private static Map<String, List<IConstraint>> mapConstraintsByType(
			Set<IConstraint> constraints) {
		Map<String, List<IConstraint>> result = new HashMap<String, List<IConstraint>>();
		result.put("incoming", new LinkedList<IConstraint>());
		result.put("outgoing", new LinkedList<IConstraint>());
		result.put("global incoming", new LinkedList<IConstraint>());
		result.put("global outgoing", new LinkedList<IConstraint>());
		result.put("not allowed", new LinkedList<IConstraint>());
		result.put("documented violation", new LinkedList<IConstraint>());
		for (IConstraint constraint : constraints) {
			String type = ModelUtils.getConstraintType(constraint);
			if (type.equals("in/out")) {
				result.get("incoming").add(constraint);
				result.get("outgoing").add(constraint);
			} else {
				List<IConstraint> list = result.get(type);
				list.add(constraint);
			}
		}
		return result;
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
