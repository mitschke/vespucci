package de.tud.cs.st.vespucci.ensemblesourcemap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsembleSourceProject implements IDataViewObserver<IPair<IEnsemble, ICodeElement>> {

	private IEnsembleElementList ensembleElementList;
	private List<IPair<IEnsemble, ICodeElement>> pairs;
	
	public EnsembleSourceProject(IEnsembleElementList ensembleElementList){
		this.ensembleElementList = ensembleElementList;
		this.ensembleElementList.register(this);
		fillDataInitial();
	}
	
	private void fillDataInitial() {
		for (Iterator<IPair<IEnsemble, ICodeElement>> i = ensembleElementList.iterator(); i.hasNext();){
			pairs.add(i.next());
		}
	}

	@Override
	public void added(IPair<IEnsemble, ICodeElement> element) {
		pairs.add(element);
	}

	@Override
	public void deleted(IPair<IEnsemble, ICodeElement> element) {
		pairs.remove(element);
	}

	@Override
	public void updated(IPair<IEnsemble, ICodeElement> oldValue,
			IPair<IEnsemble, ICodeElement> newValue) {
		pairs.remove(oldValue);
		pairs.add(newValue);		
	}
	
	public List<EnsembleSource> getElements(){
		Map<IEnsemble, List<ICodeElement>> elements = new HashMap<IEnsemble, List<ICodeElement>>();
		
		for (IPair<IEnsemble, ICodeElement> pair : pairs) {
			if (elements.containsKey(pair.getFirst())){
				elements.get(pair.getFirst()).add(pair.getSecond());
			}else{
				List<ICodeElement> temp = new LinkedList<ICodeElement>();
				temp.add(pair.getSecond());
				elements.put(pair.getFirst(), temp);
			}
		}
		
		LinkedList<EnsembleSource> ensembleSource = new LinkedList<EnsembleSource>();
		
		for (IEnsemble ensemble : elements.keySet()) {
			ensembleSource.add(new EnsembleSource(ensemble, elements.get(ensemble)));
		}
		return ensembleSource;	
	}
	
	public void dispose(){
		ensembleElementList.dispose();
		dispose();
	}
}

class EnsembleSource {
	
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

