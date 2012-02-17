package de.tud.cs.st.vespucci.view.currentdiagrams.model;


import de.tud.cs.st.vespucci.interfaces.IPair;


public class Pair<A,B> implements IPair<A, B> {

	private A first;
	private B seconde;
	
	public Pair(A first, B seconde){
		this.first = first;
		this.seconde = seconde;
	}
	
	
	@Override
	public A getFirst() {
		return first;
	}

	@Override
	public B getSecond() {
		return seconde;
	}

}
