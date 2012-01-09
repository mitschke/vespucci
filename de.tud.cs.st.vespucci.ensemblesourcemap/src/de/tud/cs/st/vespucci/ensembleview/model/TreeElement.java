package de.tud.cs.st.vespucci.ensembleview.model;

import java.util.LinkedList;
import java.util.List;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class TreeElement<A> {

	private TreeElement parent;
	private List<TreeElement> children;
	private A reference;

	public TreeElement(TreeElement parent, A reference) {
		this.parent = parent;
		this.reference = reference;
		this.children = new LinkedList<TreeElement>();
	}

	public A getReference() {

		return this.reference;
	}

	public TreeElement getParent() {
		return parent;
	}

	public void setParent(TreeElement newParent) {

		this.parent = newParent;
	}

	public int getNumberOfChildren() {
		return this.children.size();
	}

	public boolean hasParent() {

		if (this.parent != null) {
			return true;
		}
		return false;
	}

	public boolean hasChildren() {

		return !this.children.isEmpty();
	}

	public List<TreeElement> getChildren() {
		return this.children;
	}

	public void addChild(TreeElement child) {

		this.children.add(child);
	}

	public void addChildren(List<TreeElement> newChildren) {

		this.children.addAll(newChildren);
	}

	public boolean removeChild(TreeElement child) {

		return this.children.remove(child);
	}
	
	public void print(String prefix){
		
		
		
		System.out.println(prefix + "--------------------------" + reference.getClass() +"----------------------------");
		if (this.reference instanceof IEnsemble){
			System.out.println(prefix + ((IEnsemble)this.reference).getName());
		}else if (this.reference instanceof ICodeElement){
			System.out.println(prefix + ((ICodeElement)this.reference).getPackageIdentifier());
			System.out.println(prefix + ((ICodeElement)this.reference).getSimpleClassName());	
		}else if (this.reference instanceof String){
			System.out.println(prefix + (String) this.reference);
		}
		
		for (TreeElement child : this.children) {
			child.print(prefix + "\t");
		}
		
		System.out.println(prefix + "---------------------------------------------------------------------------------");
		
		
	}
}
