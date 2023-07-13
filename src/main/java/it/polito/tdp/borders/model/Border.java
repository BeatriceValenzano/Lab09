package it.polito.tdp.borders.model;

import java.util.Objects;

public class Border {

	private Country state1;
	private Country state2;
	
	public Border(Country state1, Country state2) {
		super();
		this.state1 = state1;
		this.state2 = state2;
	}

	public Country getState1() {
		return state1;
	}

	public void setState1(Country state1) {
		this.state1 = state1;
	}

	public Country getState2() {
		return state2;
	}

	public void setState2(Country state2) {
		this.state2 = state2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(state1, state2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		return Objects.equals(state1, other.state1) && Objects.equals(state2, other.state2);
	}

	@Override
	public String toString() {
		return "Border [state1=" + state1 + ", state2=" + state2 + "]";
	}
	
}
