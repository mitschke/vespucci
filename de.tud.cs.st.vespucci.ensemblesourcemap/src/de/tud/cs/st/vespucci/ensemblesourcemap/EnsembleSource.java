package de.tud.cs.st.vespucci.ensemblesourcemap;

import java.util.List;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsembleSource {
	
	private IEnsemble ensemble;
	private List<ICodeElement> codeElements;

	public EnsembleSource(IEnsemble ensemble, List<ICodeElement> codeElements){
		this.ensemble = ensemble;
		this.codeElements = codeElements;
	}
	
	public IEnsemble getEnsemble(){
		return ensemble;
	}
	
	public List<ICodeElement> getCodeElements(){
		return codeElements;
	}
	
}