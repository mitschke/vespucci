package de.tud.cs.st.vespucci.diagram.outputIModel;

import java.util.LinkedList;

public class Shape implements IShape {

	private String description;
	private String name;
	private String query;
	private LinkedList<IConnection> sourceConnections;
	private LinkedList<IConnection> targetConnections;

	public Shape() {
		super();
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public String getQuery() {

		return this.query;
	}

	@Override
	public LinkedList<IConnection> getSourceConnections() {

		return this.sourceConnections;
	}

	@Override
	public LinkedList<IConnection> getTargetConnections() {

		return this.targetConnections;
	}

	@Override
	public void setName(String name) {

		this.name = name;

	}

	@Override
	public void setDescription(String desc) {

		this.description = desc;
	}

	@Override
	public void setQuery(String Query) {
		this.query = name;

	}

}
