package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.interfaces.IViolationSummary;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class ViolationSummary implements IViolationSummary {

	private String path;

	public ViolationSummary(String diagramFilePath){
		this.path = diagramFilePath;
	}
	
	@Override
	public String getDiagramFile() {
		return path;
	}

	@Override
	public IEnsemble getSourceEnsemble() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEnsemble getTargetEnsemble() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConstraint getConstraint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numberOfViolations() {
		// TODO Auto-generated method stub
		return 0;
	}

}
