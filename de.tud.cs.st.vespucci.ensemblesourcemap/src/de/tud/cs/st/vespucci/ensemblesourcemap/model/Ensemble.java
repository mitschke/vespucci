package de.tud.cs.st.vespucci.ensemblesourcemap.model;

import java.util.LinkedList;

import de.tud.cs.st.vespucci.model.IEnsemble;

public class Ensemble extends TreeElement{

	private IEnsemble ensemble;
	private LinkedList<Package> incredits = new LinkedList<Package>();
	
	public Ensemble(IEnsemble ensemble, TreeElement parent) {
		super(parent);
		this.ensemble = ensemble;
	}

	@Override
	public Object[] getChildren() {
		return incredits.toArray();
	}

	public void setChildren(LinkedList<Package> packages){
		this.incredits = packages;
	}
	
	public IEnsemble getEnsemble(){
		return ensemble;
	}

	@Override
	public boolean hasChildren() {
		if (incredits.size() > 0){
			return true;
		}
		return false;
	}
	
}