package de.tud.cs.st.vespucci.ensembleview;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.tud.cs.st.vespucci.ensembleview.model.TreeElement;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementView;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsembleSourceProject implements IDataViewObserver<IPair<IEnsemble, ICodeElement>> {

	private IEnsembleElementView ensembleElementList;
	private List<IPair<IEnsemble, ICodeElement>> pairs;
	
	public EnsembleSourceProject(IEnsembleElementView ensembleElementList){
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
	
	public List<TreeElement<IEnsemble>> getElements(){
		
		List<TreeElement<IEnsemble>> result = new LinkedList<TreeElement<IEnsemble>>();
		
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
		
		for (IEnsemble ensemble : elements.keySet()) {
			
			TreeElement<IEnsemble> layer1 = new TreeElement<IEnsemble>(null, ensemble);
			result.add(layer1);
			List<ICodeElement> codeElements = elements.get(ensemble);
			
			Map<String, List<ICodeElement>> elements2 = new HashMap<String, List<ICodeElement>>();
	
			for (ICodeElement iCodeElement : codeElements) {
				if (elements2.containsKey(iCodeElement.getPackageIdentifier())){
					elements2.get(iCodeElement.getPackageIdentifier()).add(iCodeElement);
				}else{
					List<ICodeElement> temp = new LinkedList<ICodeElement>();
					temp.add(iCodeElement);
					elements2.put(iCodeElement.getPackageIdentifier(), temp);
				}
			}
			
			for (String packageIdentifier : elements2.keySet()) {
				
				TreeElement<String> layer2 = new TreeElement<String>(layer1, packageIdentifier);
				layer1.addChild(layer2);
				List<ICodeElement> codeElements2 = new LinkedList<ICodeElement>();
				codeElements2 = elements.get(ensemble);
				
				Map<String, List<ICodeElement>> elements3 = new HashMap<String, List<ICodeElement>>();
		
				for (ICodeElement iCodeElement : codeElements2) {
					if (elements3.containsKey(iCodeElement.getSimpleClassName())){
						elements3.get(iCodeElement.getSimpleClassName()).add(iCodeElement);
					}else{
						List<ICodeElement> temp = new LinkedList<ICodeElement>();
						temp.add(iCodeElement);
						elements3.put(iCodeElement.getSimpleClassName(), temp);
					}
				}
				
				for (String iCodeElement : elements3.keySet()) {
					
					// Hier break
					
					TreeElement<String> layer3 = new TreeElement<String>(layer2, iCodeElement);
					layer2.addChild(layer3);
				}
			
			}
		}
		
		return result;		
	}

	
	public void dispose(){
		ensembleElementList.dispose();
		dispose();
	}
}

