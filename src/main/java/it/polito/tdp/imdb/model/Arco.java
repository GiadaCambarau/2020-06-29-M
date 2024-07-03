package it.polito.tdp.imdb.model;

import java.util.Objects;

public class Arco implements Comparable<Arco>{
	private Director d1;
	private Director d2;
	private int peso;
	public Arco(Director d1, Director d2, int peso) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.peso = peso;
	}
	public Director getD1() {
		return d1;
	}
	public void setD1(Director d1) {
		this.d1 = d1;
	}
	public Director getD2() {
		return d2;
	}
	public void setD2(Director d2) {
		this.d2 = d2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(d1, d2, peso);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		return Objects.equals(d1, other.d1) && Objects.equals(d2, other.d2) && peso == other.peso;
	}
	@Override
	public int compareTo(Arco o) {
		return o.peso-this.peso;
	}
	@Override
	public String toString() {
		return d2 + "    " + peso ;
	}
	
	

}
