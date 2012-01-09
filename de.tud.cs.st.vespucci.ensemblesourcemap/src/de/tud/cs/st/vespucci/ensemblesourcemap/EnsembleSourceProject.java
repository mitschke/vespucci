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
		pairs = new LinkedList<IPair<IEnsemble,ICodeElement>>();
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
		
		//Debug
		print();
	}

	@Override
	public void deleted(IPair<IEnsemble, ICodeElement> element) {
		pairs.remove(element);

		//Debug
		print();
	}

	@Override
	public void updated(IPair<IEnsemble, ICodeElement> oldValue,
			IPair<IEnsemble, ICodeElement> newValue) {
		pairs.remove(oldValue);
		pairs.add(newValue);
		
		//Debug
		print();
	}
	
	public LinkedList<EnsembleSource> getElements(){
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
	
	public void print(){
		LinkedList<EnsembleSource> temp = getElements();
		
		for (EnsembleSource ensembleSource : temp) {
			System.out.println("------- IEnsemble: " + ensembleSource.getEnsemble().getName() + " -------");
			for (ICodeElement codeElement : ensembleSource.getCodeElements()) {
				System.out.println("\t - " + codeElement.getPackageIdentifier() + "; " + codeElement.getSimpleClassName());
			}
			System.out.println("-------  -------");
		}
	}
	
	public void dispose(){
		ensembleElementList.dispose();
		dispose();
	}
}

