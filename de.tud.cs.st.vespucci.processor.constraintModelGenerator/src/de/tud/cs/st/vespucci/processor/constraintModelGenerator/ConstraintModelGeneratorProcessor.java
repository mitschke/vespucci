package de.tud.cs.st.vespucci.processor.constraintModelGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	private Map<IPair<IEnsemble, IEnsemble>, Integer> parentRelations = new HashMap<IPair<IEnsemble, IEnsemble>, Integer>();

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

		for (Tuple3<IEnsemble, IEnsemble, Object> tuple3 : dependencies) {
			System.out.println(tuple3);
			IEnsemble source = tuple3._1();
			IEnsemble target = tuple3._2();
			if (source.equals(target))
				continue;

			IPair<IEnsemble, IEnsemble> pair = null;
			if (!haveCommonAncestors(source, target)) {
				pair = new EnsemblePair(
						ModelUtils.getOuterMostEnclosingEnsemble(source),
						ModelUtils.getOuterMostEnclosingEnsemble(target));
			} else {
				pair = new EnsemblePair(source, target);
			}

			if (!parentRelations.containsKey(pair))
				parentRelations.put(pair, Integer.valueOf(1));
			else
				parentRelations.put(pair, Integer.valueOf(parentRelations.get(
						pair).intValue() + 1));

		}

		Set<Entry<IPair<IEnsemble,IEnsemble>,Integer>> set = parentRelations.entrySet();
		for (Entry<IPair<IEnsemble, IEnsemble>, Integer> entry : set) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
		
		
		List<Shape> ensembles = new ArrayList<Shape>(globalModel.getShapes());
		for (Shape ensemble : ensembles) {
			ShapesDiagram model = Util.adapt(diagramModel, ShapesDiagram.class);
			Map<IPair<IEnsemble, IEnsemble>, Integer> relations = new HashMap<IPair<IEnsemble,IEnsemble>, Integer>(parentRelations);
			for (Tuple3<IEnsemble, IEnsemble, Object> tuple3 : dependencies) {

				IEnsemble source = tuple3._1();
				IEnsemble target = tuple3._2();
				if (source.equals(target))
					continue;
				IEnsemble outerMostEnclosingSource = ModelUtils
						.getOuterMostEnclosingEnsemble(source);
				IEnsemble outerMostEnclosingTarget = ModelUtils
						.getOuterMostEnclosingEnsemble(target);
				if (!(outerMostEnclosingSource.getName().equals(
						ensemble.getName()) || outerMostEnclosingTarget
						.getName().equals(ensemble.getName())))
					continue;
				IPair<IEnsemble, IEnsemble> pair = null;
				if (!haveCommonAncestors(source, target)) {
					pair = new EnsemblePair(
							ModelUtils.getOuterMostEnclosingEnsemble(source),
							ModelUtils.getOuterMostEnclosingEnsemble(target));
				} else {
					pair = new EnsemblePair(source, target);
				}

				if (!relations.containsKey(pair))
					continue; // we have already processed this as a single
								// relation
				System.out.println(pair);
				int numParentRelations = relations.get(pair);
				relations.remove(pair);
				
				Shape sourceEnsemble = null;
				Shape targetEnsemble = null;

				if (numParentRelations == 0) {
					throw new IllegalStateException(
							"Parents should have at least one dependency");
				}
				if (numParentRelations == 1) {
					sourceEnsemble = getModelEnsemble(source, model);
					targetEnsemble = getModelEnsemble(target, model);
				} else {
					sourceEnsemble = getModelEnsemble(
							ModelUtils.getOuterMostEnclosingEnsemble(source),
							model);
					targetEnsemble = getModelEnsemble(
							ModelUtils.getOuterMostEnclosingEnsemble(target),
							model);
				}

				if (haveCommonAncestors(source, target)) {
					InAndOut inAndOut = modelFactory.createInAndOut();
					inAndOut.setName("all");
					inAndOut.setSource(sourceEnsemble);
					inAndOut.setTarget(targetEnsemble);
					sourceEnsemble.getTargetConnections().add(inAndOut);
				} else {
					if (ModelUtils.getOuterMostEnclosingEnsemble(target).getName().equals(ensemble.getName())) {
						GlobalIncoming incoming = modelFactory
								.createGlobalIncoming();
						incoming.setName("all");
						incoming.setSource(sourceEnsemble);
						incoming.setTarget(targetEnsemble);
						sourceEnsemble.getTargetConnections().add(incoming);
					}
					if (ModelUtils.getOuterMostEnclosingEnsemble(source).getName().equals(ensemble.getName())) {
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
					.append("global-views").append(ensemble.getName())
					.addFileExtension("sad");

			try {
				File file = outpath.toFile();
				if (!file.exists()) {
					IPath viewPath = diagramFile.getLocation()
							.removeLastSegments(1).append("global-views");
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

		// try {
		//
		// List<Shape> ensembles = new
		// ArrayList<Shape>(globalModel.getShapes());
		//
		//
		// for (Shape ensemble : ensembles) {
		// EList<Connection> targetConnections =
		// ensemble.getTargetConnections();
		// List<Shape> targets = new LinkedList<Shape>();
		// for (Connection connection : targetConnections) {
		// targets.add(connection.getTarget());
		// }
		// List<Shape> removals = new ArrayList<Shape>(globalModel.getShapes());
		// removals.remove(ensemble);
		// for (Shape retained : targets) {
		// removals.remove(retained);
		// }
		// globalModel.getShapes().removeAll(removals);
		//
		// Diagram diagram = getDiagram(globalModel);
		//
		// List<Node> diagramRemovals = new LinkedList<Node>();
		// List persistedChildren = new
		// ArrayList<Object>(diagram.getPersistedChildren());
		// for (Object child : persistedChildren) {
		// Node node = (Node) child;
		// if(removals.contains(node.getElement())){
		// diagram.removeChild(node);
		// diagramRemovals.add(node);
		// }
		// }
		//
		// IPath outpath =
		// diagramFile.getLocation().removeLastSegments(1).append("global-views")
		// .append(ensemble.getName()).addFileExtension("sad");
		//
		// File file = outpath.toFile();
		// if (!file.exists()){
		// IPath viewPath =
		// diagramFile.getLocation().removeLastSegments(1).append("global-views");
		// viewPath.toFile().mkdirs();
		// file.createNewFile();
		// }
		//
		// FileOutputStream outputStream = new FileOutputStream(file);
		// Resource resource = globalModel.eResource();
		// resource.save(outputStream, null);
		// outputStream.close();
		// System.out.println("wrote " + file.getName());
		// globalModel.getShapes().addAll(removals);
		//
		// for (Node node : diagramRemovals) {
		// diagram.insertChild(node);
		// }
		// }
		//
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return null;
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

	private static class EnsemblePair implements IPair<IEnsemble, IEnsemble> {

		private IEnsemble first;

		private IEnsemble second;

		public EnsemblePair(IEnsemble first, IEnsemble second) {
			super();
			this.first = first;
			this.second = second;
		}

		/**
		 * @return the first
		 */
		public IEnsemble getFirst() {
			return first;
		}

		/**
		 * @return the second
		 */
		public IEnsemble getSecond() {
			return second;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((first == null) ? 0 : first.hashCode());
			result = prime * result
					+ ((second == null) ? 0 : second.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EnsemblePair other = (EnsemblePair) obj;
			if (first == null) {
				if (other.first != null)
					return false;
			} else if (!first.equals(other.first))
				return false;
			if (second == null) {
				if (other.second != null)
					return false;
			} else if (!second.equals(other.second))
				return false;
			return true;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "EnsemblePair [first=" + first + ", second=" + second + "]";
		}
	}
}
