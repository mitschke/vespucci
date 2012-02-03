package de.tud.cs.st.vespucci.ensembleview.table.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMember;

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

	@SuppressWarnings("unchecked")
	public static IPair<IEnsemble, ICodeElement> transfer(Object obj){
		if (obj instanceof IPair<?,?>){
			IPair<?,?> temp = (IPair<?,?>) obj;
			if ((temp.getFirst() instanceof IEnsemble)&&(temp.getSecond() instanceof ICodeElement)){
				return (IPair<IEnsemble, ICodeElement>) temp;
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

	static int start = 0;
	
	@Override
	public void added(IPair<IEnsemble, ICodeElement> element) {
		dataModel.added(element);
		notifyObserver();
	}

	@Override
	public void deleted(IPair<IEnsemble, ICodeElement> element) {
		if (elements.containsKey(element)){
			Triple<IEnsemble, ICodeElement, IMember> data = elements.get(element);
			elements.remove(element);
			dataModel.deleted(data);
			notifyObserver();
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

	public IProject getProject() {
		return project;
	}

}
