package it.polito.tdp.borders.model;

import java.util.Objects;

public class CountryAndNumber implements Comparable<CountryAndNumber>{

	private Country state;
	private int confinanti;
	
	public CountryAndNumber(Country state, int confinanti) {
		super();
		this.state = state;
		this.confinanti = confinanti;
	}
	
	public Country getState() {
		return state;
	}

	public int getConfinanti() {
		return confinanti;
	}

	@Override
	public int hashCode() {
		return Objects.hash(confinanti, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryAndNumber other = (CountryAndNumber) obj;
		return confinanti == other.confinanti && Objects.equals(state, other.state);
	}

	@Override
	public String toString() {
		return state.getStateAbb() + " - " + state.getStateName() + " : " + confinanti;
	}

	@Override
	public int compareTo(CountryAndNumber o) {
		return this.state.getStateName().compareTo(o.state.getStateName());
	}

	
}
