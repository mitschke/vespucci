package de.tud.cs.st.vespucci.ensembleview.table.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.codeelementfinder.CodeElementFinder;
import de.tud.cs.st.vespucci.codeelementfinder.ICodeElementFoundProcessor;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class DataManager<A extends IDataModel> implements IDataViewObserver<IPair<IEnsemble, ICodeElement>>, IDataManagerObservable {

	private IEnsembleElementList incommingData;
	private IProject project;
	private Set<IDataManagerObserver> observer = new HashSet<IDataManagerObserver>();
	private HashMap<IPair<IEnsemble, ICodeElement>, Triple<IEnsemble, ICodeElement, IMember>> elements = new HashMap<IPair<IEnsemble, ICodeElement>, Triple<IEnsemble, ICodeElement, IMember>>();
	private A dataModel;

	public static Triple<IEnsemble, ICodeElement, IMember> transfer(Object obj){
		if (obj instanceof Triple<?,?,?>){
			Triple<?,?,?> temp = (Triple<?, ?, ?>) obj;
			if ((temp.getFirst() instanceof IEnsemble)&&(temp.getSecond() instanceof ICodeElement)&&(temp.getThird() instanceof IMember)){
				IEnsemble ensemble = (IEnsemble) temp.getFirst();
				ICodeElement codeElement = (ICodeElement) temp.getSecond();
				IMember member = (IMember) temp.getThird();
				return new Triple<IEnsemble, ICodeElement, IMember>(ensemble, codeElement, member);
			}
		}
		return null;
	}

	public DataManager(IEnsembleElementList incommingData, IProject project, A dataModel){
		this.project = project;
		this.incommingData = incommingData;
		this.incommingData.register(this);
		this.dataModel = dataModel;
		initialDataImport(incommingData);
	}

	public A getDataModel(){
		return dataModel;
	}

	private void initialDataImport(IEnsembleElementList incommingData) {
		for (Iterator<IPair<IEnsemble, ICodeElement>> i = incommingData.iterator(); i.hasNext();){
			added(i.next());
		}
	}

	@Override
	public void added(IPair<IEnsemble, ICodeElement> element) {
//		Debug.printICodeElement(element.getSecond());
		CodeElementFinder.startSearch(element.getSecond(), project, new CodeFinder(element));
	}
	
	class CodeFinder implements ICodeElementFoundProcessor{

		private IPair<IEnsemble, ICodeElement> element;
		
		public CodeFinder(IPair<IEnsemble, ICodeElement> element){
			this.element = element;
		}

		@Override
		public void processFoundCodeElement(IMember member) {
//			System.out.println("Found");
//			Debug.printICodeElement(element.getSecond());
			
			Triple<IEnsemble, ICodeElement, IMember> data = new Triple<IEnsemble, ICodeElement, IMember>(element.getFirst(), element.getSecond(), member);
			elements.put(element, data);
			dataModel.added(data);
			notifyObserver();
		}

		@Override
		public void processFoundCodeElement(IMember member, int lineNr) {
			//unused in this ResultProcessor
		}

		@Override
		public void noMatchFound(ICodeElement codeElement) {
			//unused in this ResultProcessor
		}
		
	}

	@Override
	public void deleted(IPair<IEnsemble, ICodeElement> element) {
		
		// given IPair is not the same (equal) instance as when the pair was added
		for (IPair<IEnsemble, ICodeElement> key : elements.keySet()) {
			// Equals of IType doesn
//			if (key.equals(element)){
//				System.out.println("Übereinstimmung gefunden");
//				Triple<IEnsemble, ICodeElement, IMember> data = elements.get(key);
//				elements.remove(key);
//				dataModel.deleted(data);
//				notifyObserver();
//				break;
//			}
			if (elements.get(key).getSecond().equals(element.getSecond())){
				System.out.println("Übereinstimmung gefunden");
				Triple<IEnsemble, ICodeElement, IMember> data = elements.get(key);
				elements.remove(key);
				dataModel.deleted(data);
				notifyObserver();
				break;
			}
		}

	}

	@Override
	public void updated(IPair<IEnsemble, ICodeElement> oldValue, IPair<IEnsemble, ICodeElement> newValue) {
		deleted(oldValue);
		added(newValue);
	}

	public void dispose(){
		incommingData.unregister(this);
		incommingData.dispose();
	}


	@Override
	public void register(IDataManagerObserver observer) {
		this.observer.add(observer);
	}


	@Override
	public void unregister(IDataManagerObserver observer) {
		this.observer.remove(observer);
	}

	@Override
	public void notifyObserver() {
		for (IDataManagerObserver observer : this.observer) {
			observer.update();
		}
	}

}
