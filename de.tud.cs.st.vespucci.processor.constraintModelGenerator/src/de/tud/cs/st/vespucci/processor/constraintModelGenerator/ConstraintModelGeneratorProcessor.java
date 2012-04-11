package de.tud.cs.st.vespucci.processor.constraintModelGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Diagram;

import sae.MaterializedView;
import scala.Tuple3;
import scala.collection.JavaConversions;
import unisson.model.UnissonDatabase;
import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.EnsemblePair;
import de.tud.cs.st.vespucci.utilities.ModelUtils;
import de.tud.cs.st.vespucci.utilities.Util;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming;
import de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing;
import de.tud.cs.st.vespucci.vespucci_model.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelFactory;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

public class ConstraintModelGeneratorProcessor implements IModelProcessor {

	private static final String GLOBAL_VIEWS_TEMP = "global-views-temp";

	@Override
	public Object processModel(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);

		ShapesDiagram globalModel = Util.adapt(diagramModel,
				ShapesDiagram.class);

		IProject project = diagramFile.getProject();

		UnissonDatabase database = ArchitectureDatabaseProvider.getInstance()
				.getArchitectureDatabase(project);

		MaterializedView<Tuple3<IEnsemble, IEnsemble, Object>> scalaEnsembleDependencyView = database
				.ensembleDependencies();

		Collection<Tuple3<IEnsemble, IEnsemble, Object>> dependencies = JavaConversions
				.asJavaCollection(scalaEnsembleDependencyView.asList());

		Vespucci_modelFactory modelFactory = Vespucci_modelPackage.eINSTANCE
				.getVespucci_modelFactory();

		Set<IPair<IEnsemble, IEnsemble>> dependencyMapping = createRelationMapping(dependencies);

		List<Shape> ensembles = new ArrayList<Shape>(globalModel.getShapes());
		for (Shape ensemble : ensembles) {
			ShapesDiagram model = Util.adapt(diagramModel, ShapesDiagram.class);

			for (IPair<IEnsemble, IEnsemble> dependency : dependencyMapping) {
				IEnsemble source = dependency.getFirst();
				IEnsemble target = dependency.getSecond();

				IEnsemble outerMostEnclosingSource = ModelUtils
						.getOuterMostEnclosingEnsemble(source);
				IEnsemble outerMostEnclosingTarget = ModelUtils
						.getOuterMostEnclosingEnsemble(target);
				if (!(outerMostEnclosingSource.getName().equals(
						ensemble.getName()) || outerMostEnclosingTarget
						.getName().equals(ensemble.getName())))
					continue;

				System.out.println(dependency);

				Shape sourceEnsemble = getModelEnsemble(source, model);
				Shape targetEnsemble = getModelEnsemble(target, model);

				if (haveCommonAncestors(source, target)) {
					InAndOut inAndOut = modelFactory.createInAndOut();
					inAndOut.setName("all");
					inAndOut.setSource(sourceEnsemble);
					inAndOut.setTarget(targetEnsemble);
					sourceEnsemble.getTargetConnections().add(inAndOut);
				} else {
					if (ModelUtils.getOuterMostEnclosingEnsemble(target)
							.getName().equals(ensemble.getName())) {
						GlobalIncoming incoming = modelFactory
								.createGlobalIncoming();
						incoming.setName("all");
						incoming.setSource(sourceEnsemble);
						incoming.setTarget(targetEnsemble);
						sourceEnsemble.getTargetConnections().add(incoming);
					}
					if (ModelUtils.getOuterMostEnclosingEnsemble(source)
							.getName().equals(ensemble.getName())) {
						GlobalOutgoing outgoing = modelFactory
								.createGlobalOutgoing();
						outgoing.setName("all");
						outgoing.setSource(sourceEnsemble);
						outgoing.setTarget(targetEnsemble);
						sourceEnsemble.getTargetConnections().add(outgoing);
					}
				}

			}

			IPath outpath = diagramFile.getLocation().removeLastSegments(1)
					.append(GLOBAL_VIEWS_TEMP).append(ensemble.getName())
					.addFileExtension("sad");

			try {
				File file = outpath.toFile();
				if (!file.exists()) {
					IPath viewPath = diagramFile.getLocation()
							.removeLastSegments(1).append(GLOBAL_VIEWS_TEMP);
					viewPath.toFile().mkdirs();
					file.createNewFile();
				}

				FileOutputStream outputStream = new FileOutputStream(file);
				Resource resource = model.eResource();
				resource.save(outputStream, null);
				outputStream.close();
				System.out.println("wrote " + file.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	private static void checkAndInitCounter(
			Map<IPair<IEnsemble, IEnsemble>, Integer> ensembleDependencies,
			IPair<IEnsemble, IEnsemble> element) {
		if (!ensembleDependencies.containsKey(element))
			ensembleDependencies.put(element, 0);
	}

	private static void incCounter(
			Map<IPair<IEnsemble, IEnsemble>, Integer> ensembleDependencies,
			IPair<IEnsemble, IEnsemble> element) {
		checkAndInitCounter(ensembleDependencies, element);
		ensembleDependencies
				.put(element, ensembleDependencies.get(element) + 1);
	}

	private Set<IPair<IEnsemble, IEnsemble>> createRelationMapping(
			Collection<Tuple3<IEnsemble, IEnsemble, Object>> dependencies) {

		Set<IPair<IEnsemble, IEnsemble>> result = new HashSet<IPair<IEnsemble, IEnsemble>>();

		Map<IPair<IEnsemble, IEnsemble>, Integer> ensembleDependencies = new HashMap<IPair<IEnsemble, IEnsemble>, Integer>();

		Map<IPair<IEnsemble, IEnsemble>, Set<IPair<IEnsemble, IEnsemble>>> subsumedBy = new HashMap<IPair<IEnsemble,IEnsemble>, Set<IPair<IEnsemble,IEnsemble>>>();

		for (Tuple3<IEnsemble, IEnsemble, Object> tuple3 : dependencies) {
			IEnsemble sourceEnsemble = tuple3._1();
			IEnsemble targetEnsemble = tuple3._2();
			if (sourceEnsemble.equals(targetEnsemble))
				continue;
			List<IEnsemble> sources = ModelUtils.getParentList(sourceEnsemble);
			sources.add(0, sourceEnsemble);
			List<IEnsemble> targets = ModelUtils.getParentList(targetEnsemble);
			targets.add(0, targetEnsemble);

			List<IPair<IEnsemble, IEnsemble>> combinations = new LinkedList<IPair<IEnsemble, IEnsemble>>();
			// add all combinations including parents parent combinations.
			for (IEnsemble source : sources) {
				for (IEnsemble target : targets) {
					if(sources.contains(target) || targets.contains(source))
						continue;
					combinations.add(ModelUtils.Pair(source, target));
					result.add(ModelUtils.Pair(source, target));
					incCounter(ensembleDependencies, ModelUtils.Pair(source, target));
				}
			}

			for (IPair<IEnsemble, IEnsemble> combination : combinations) {
				Set<IPair<IEnsemble,IEnsemble>> directSubsumingEdges = getDirectSubsumingEdges(combination, combinations);
				if(!subsumedBy.containsKey(combination))
					subsumedBy.put(combination, new HashSet<IPair<IEnsemble,IEnsemble>>());
				subsumedBy.get(combination).addAll(directSubsumingEdges);
			}
			
		}

		Set<IPair<IEnsemble, IEnsemble>> allDependencies = ensembleDependencies
				.keySet();

		for (IPair<IEnsemble, IEnsemble> dependency : allDependencies) {
			Set<IPair<IEnsemble, IEnsemble>> superEdges = getAllSubsumingEdges(
					dependency, subsumedBy);
			if (superEdges.size() == 0)
				continue;
			boolean hasSubsumingEdge = false;
			int count = ensembleDependencies.get(dependency);
			for (IPair<IEnsemble, IEnsemble> superEdge : superEdges) {
				// all super edges that have the same count do not subsume more than the current edge and can be removed
				if (ensembleDependencies.get(superEdge) == count)
					result.remove(superEdge);
				else
					hasSubsumingEdge = true;
			}
			if (hasSubsumingEdge)
				result.remove(dependency);
		}

		return result;
	}

	private static Set<IPair<IEnsemble, IEnsemble>> getDirectSubsumingEdges(
			IPair<IEnsemble, IEnsemble> in,
			List<IPair<IEnsemble, IEnsemble>> edges) {

		Set<IPair<IEnsemble, IEnsemble>> result = new HashSet<IPair<IEnsemble,IEnsemble>>();

		for (IPair<IEnsemble, IEnsemble> edge : edges) {
			if ( 
					(
							edge.getFirst().equals(in.getFirst().getParent()) && 
							edge.getSecond().equals(in.getSecond())
					) || 
					(
							edge.getFirst().equals(in.getFirst()) &&
							edge.getSecond().equals(in.getSecond().getParent())
					)
				)
				result.add(edge);
		}

		return result;
	}

	private static Set<IPair<IEnsemble, IEnsemble>> getAllSubsumingEdges(
			IPair<IEnsemble, IEnsemble> edge,
			Map<IPair<IEnsemble, IEnsemble>, Set<IPair<IEnsemble, IEnsemble>>> edgeMapping) {
		Set<IPair<IEnsemble, IEnsemble>> result = new HashSet<IPair<IEnsemble,IEnsemble>>();

		Set<IPair<IEnsemble, IEnsemble>> superEdges = edgeMapping.get(edge);
		result.addAll(superEdges);
		for (IPair<IEnsemble, IEnsemble> superEdge : superEdges) {
			result.addAll(getAllSubsumingEdges(superEdge, edgeMapping));
		}
		return result;
	}

	private static void removeParentChildDuplicates(
			Set<IPair<IEnsemble, IEnsemble>> dependencies) {
		Set<IPair<IEnsemble, IEnsemble>> removals = new HashSet<IPair<IEnsemble, IEnsemble>>();
		for (IPair<IEnsemble, IEnsemble> dependency : dependencies) {
			if (dependency.getFirst().getParent() != null)
				if (dependencies.contains(new EnsemblePair(dependency
						.getFirst().getParent(), dependency.getSecond())))
					removals.add(new EnsemblePair(dependency.getFirst()
							.getParent(), dependency.getSecond()));
			if (dependency.getSecond().getParent() != null)
				if (dependencies.contains(new EnsemblePair(dependency
						.getFirst(), dependency.getSecond().getParent())))
					removals.add(new EnsemblePair(dependency.getFirst(),
							dependency.getSecond().getParent()));
		}
		dependencies.removeAll(removals);
	}

	// adds an ensemble to the mapping of parents to ensembles
	// if the parent has more parents than the parent is added transitively to
	// the list of it's parents
	private static void addTransitiveParents(
			Map<IEnsemble, Set<IEnsemble>> parentChildRelation,
			IEnsemble ensemble) {
		IEnsemble parent = ensemble.getParent();
		if (parent == null)
			return;
		if (!parentChildRelation.containsKey(parent))
			parentChildRelation.put(parent, new HashSet<IEnsemble>());
		parentChildRelation.get(parent).add(ensemble);
		addTransitiveParents(parentChildRelation, parent);
	}

	@Override
	public Class<?> resultClass() {
		return null;
	}

	private static Diagram getDiagram(ShapesDiagram model) {
		for (Iterator allContents = model.eResource().getAllContents(); allContents
				.hasNext();) {
			EObject elem = (EObject) allContents.next();
			if (elem instanceof Diagram)
				return (Diagram) elem;
		}
		return null;
	}

	private static Shape getModelEnsemble(IEnsemble ensemble,
			ShapesDiagram model) {
		EList<Shape> ensembles = model.getShapes();

		Shape shape = getModelEnsemble(ensemble, ensembles);
		if (shape != null)
			return shape;
		throw new IllegalArgumentException(
				"All ensembles must be present in the diagram");
	}

	private static Shape getModelEnsemble(IEnsemble ensemble,
			EList<Shape> ensembles) {
		for (Shape shape : ensembles) {
			if (shape.getName() != null
					&& shape.getName().equals(ensemble.getName()))
				return shape;
			if (shape instanceof Ensemble) {
				Shape innerEnsemble = getModelEnsemble(ensemble,
						((Ensemble) shape).getShapes());
				if (innerEnsemble != null)
					return innerEnsemble;
			}
		}
		return null;
	}

	private static boolean haveCommonAncestors(IEnsemble e1, IEnsemble e2) {
		if (e1.equals(e2))
			return true;
		if (e1.getParent() == null)
			return false;
		if (e2.getParent() == null)
			return false;

		if (e1.getParent().equals(e2.getParent()))
			return true;

		return haveCommonAncestors(e1.getParent(), e2.getParent())
				|| haveCommonAncestors(e1.getParent(), e2)
				|| haveCommonAncestors(e1, e2.getParent());
	}

	private static EList<Shape> getAllChildren(Shape ensemble) {
		if (!(ensemble instanceof Ensemble))
			return ECollections.<Shape> emptyEList();
		EList<Shape> result = ECollections.<Shape> emptyEList();

		Ensemble e = (Ensemble) ensemble;
		EList<Shape> children = e.getShapes();
		for (Shape child : children) {
			result.add(child);
			result.addAll(getAllChildren(child));
		}

		return result;
	}

	private static void renameAllChildren(Shape ensemble, String prefix) {
		if (prefix != null && !prefix.equals(""))
			ensemble.setName(prefix + "." + ensemble.getName());

		if (!(ensemble instanceof Ensemble))
			return;
		Ensemble e = (Ensemble) ensemble;
		EList<Shape> children = e.getShapes();
		for (Shape child : children) {
			renameAllChildren(child, ensemble.getName());
		}

	}
}
