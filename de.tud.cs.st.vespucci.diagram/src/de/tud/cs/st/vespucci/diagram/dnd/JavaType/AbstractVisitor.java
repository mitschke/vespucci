package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.util.ISourceAttribute;

import de.tud.cs.st.vespucci.errors.VespucciIllegalArgumentException;

public class AbstractVisitor implements IEclipseObjectVisitor {
	
	@Override
	public Object visit(IProject project) {
		throw getIllegalArgumentException(project);
	}

	@Override
	public Object visit(IPackageFragment packageFragment) {
		throw getIllegalArgumentException(packageFragment);
	}

	@Override
	public Object visit(IPackageFragmentRoot packageFragmentRoot) {
		throw getIllegalArgumentException(packageFragmentRoot);
	}

	@Override
	public Object visit(ICompilationUnit compilationUnit) {
		throw getIllegalArgumentException(compilationUnit);
	}

	@Override
	public Object visit(IType type) {
		throw getIllegalArgumentException(type);
	}

	@Override
	public Object visit(IField field) {
		throw getIllegalArgumentException(field);
	}

	@Override
	public Object visit(IMethod method) {
		throw getIllegalArgumentException(method);
	}

	@Override
	public Object visit(ISourceAttribute sourceAttribute) {
		throw getIllegalArgumentException(sourceAttribute);
	}

	@Override
	public Object visit(IClassFile classFile) {
		throw getIllegalArgumentException(classFile);
	}

	@Override
	public Object visit(IFile file) {
		throw getIllegalArgumentException(file);
	}

	@Override
	public Object visit(IFolder folder) {
		throw getIllegalArgumentException(folder);
	}
	
	private RuntimeException getIllegalArgumentException(Object argument) {
		return new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", argument));
	}
}
