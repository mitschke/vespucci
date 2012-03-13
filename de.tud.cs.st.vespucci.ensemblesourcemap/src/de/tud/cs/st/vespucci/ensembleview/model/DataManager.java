package de.tud.cs.st.vespucci.ensembleview.model;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.codeelementfinder.CodeElementFinder;
import de.tud.cs.st.vespucci.codeelementfinder.ICodeElementFoundProcessor;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataViewObserver;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementView;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class DataManager implements IDataViewObserver<IPair<IEnsemble, ICodeElement>> {

	private IEnsembleElementView incommingData;
	private IProject project;
	
	
	public DataManager(IEnsembleElementView incommingData, IProject project){
		this.project = project;
		this.incommingData = incommingData;
		this.incommingData.register(this);
		initialDataImport(incommingData);
	}


	private void initialDataImport(IEnsembleElementView incommingData) {
		for (Iterator<IPair<IEnsemble, ICodeElement>> i = incommingData.iterator(); i.hasNext();){
			added(i.next());
		}
	}
	
	
	public void added(IEnsemble ensemble, ICodeElement codeElement, IMember member){
		System.out.println("<<<<<<<<<<------  found something");
	}
	
	@Override
	public void added(IPair<IEnsemble, ICodeElement> element) {
		// Serach for Element
		CodeElementFinder.startSearch(element.getSecond(), project, new CodeFinder(element));
	}
	
	class CodeFinder implements ICodeElementFoundProcessor{

		private IPair<IEnsemble, ICodeElement> element;
		
		public CodeFinder(IPair<IEnsemble, ICodeElement> element){
			this.element = element;
		}
		
		@Override
		public void processFoundCodeElement(IMember member) {
			added(element.getFirst(), element.getSecond(), member);
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
		// TODO Auto-generated method stub
		
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

}
