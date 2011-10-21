package de.tud.cs.st.vespucci.diagram.outputIModel;

import java.util.LinkedList;

public class Connection implements IConnection {

	private String name;
	private boolean temp;

	private LinkedList<IShape> originalSource;
	private LinkedList<IShape> originalTarget;
	private IShape source;
	private IShape target;

	public Connection() {
		super();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public LinkedList<IShape> getOriginalSource() {
		return this.originalSource;
	}

	@Override
	public LinkedList<IShape> getOriginalTarget() {
		return this.originalTarget;
	}

	@Override
	public IShape getSource() {
		return this.source;
	}

	@Override
	public IShape getTarget() {
		return this.target;
	}

	@Override
	public boolean isTemp() {
		return this.temp;
	}

	@Override
	public void setSource(IShape source) {
		this.source = source;

	}

	@Override
	public void setTarget(IShape target) {
		this.target = target;
	}

	@Override
	public void setTemp(boolean temp) {
		this.temp = temp;

	}

	@Override
	public void setName(String name) {

		this.name = name;

	}

}
