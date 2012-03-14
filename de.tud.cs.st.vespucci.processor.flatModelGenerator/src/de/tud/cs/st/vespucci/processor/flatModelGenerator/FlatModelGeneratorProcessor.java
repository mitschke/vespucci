package de.tud.cs.st.vespucci.processor.flatModelGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;

import sae.MaterializedView;
import scala.Tuple3;
import scala.collection.JavaConversions;
import unisson.model.UnissonDatabase;
import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.Util;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.GlobalIncoming;
import de.tud.cs.st.vespucci.vespucci_model.GlobalOutgoing;
import de.tud.cs.st.vespucci.vespucci_model.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelFactory;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

public class FlatModelGeneratorProcessor implements IModelProcessor {

	@Override
	public Object processModel(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);

		ShapesDiagram model = Util.adapt(diagramModel, ShapesDiagram.class);

		IProject project = diagramFile.getProject();

		UnissonDatabase database = ArchitectureDatabaseProvider.getInstance()
				.getArchitectureDatabase(project);

		MaterializedView<Tuple3<IEnsemble, IEnsemble, Object>> ensembleDependencies = database
				.ensembleDependencies();

		Collection<Tuple3<IEnsemble, IEnsemble, Object>> collection = JavaConversions
				.asJavaCollection(ensembleDependencies.asList());

		Vespucci_modelFactory modelFactory = Vespucci_modelPackage.eINSTANCE
				.getVespucci_modelFactory();

		for (Tuple3<IEnsemble, IEnsemble, Object> tuple3 : collection) {
			IEnsemble source = tuple3._1();
			IEnsemble target = tuple3._2();
			if(source.equals(target))
				continue;
			
			System.out.println(tuple3);
			Shape sourceEnsemble = getModelEnsemble(source, model);
			Shape targetEnsemble = getModelEnsemble(target, model);
			if (haveCommonAncestors(source, target)) {
				InAndOut inAndOut = modelFactory.createInAndOut();
				inAndOut.setName("all");
				inAndOut.setSource(sourceEnsemble);
				inAndOut.setTarget(targetEnsemble);
				sourceEnsemble.getTargetConnections().add(inAndOut);
				// targetEnsemble.getSourceConnections().add(inAndOut);
			} else {
				GlobalIncoming incoming = modelFactory.createGlobalIncoming();
				incoming.setName("all");
				incoming.setSource(sourceEnsemble);
				incoming.setTarget(targetEnsemble);
				sourceEnsemble.getTargetConnections().add(incoming);
				// targetEnsemble.getSourceConnections().add(incoming);

				GlobalOutgoing outgoing = modelFactory.createGlobalOutgoing();
				outgoing.setName("all");
				outgoing.setSource(sourceEnsemble);
				outgoing.setTarget(targetEnsemble);
				sourceEnsemble.getTargetConnections().add(outgoing);
				// targetEnsemble.getSourceConnections().add(outgoing);

			}
		}

		EList<Shape> ensembles = model.getShapes();
		for (Shape shape : ensembles) {
			renameAllChildren(shape, "");
		}

		try {
			IPath outpath = diagramFile.getLocation().removeLastSegments(1)
					.append("flat-model").addFileExtension("sad");
			File file = outpath.toFile();
			if (!file.exists())
				file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			Resource resource = model.eResource();
			resource.save(outputStream, null);
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Class<?> resultClass() {
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
