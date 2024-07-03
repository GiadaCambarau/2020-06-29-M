package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private ImdbDAO dao;
	private Map<Integer, Director> mappa;
	private Graph<Director, DefaultWeightedEdge> grafo;
	private List<Director> vertici;
	private List<Director> best;
	private int max =0;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.mappa = new HashMap<>();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici = new ArrayList<>();
		
	}
	
	public void creaGrafo(int anno) {
		for (Director d: dao.listAllDirectors()) {
			mappa.put(d.id, d);
		}
		Graphs.addAllVertices(this.grafo, dao.getDirectors(anno, mappa));
		List<Arco> archi = dao.getArchi(anno, mappa);
		for (Arco a : archi) {
			Graphs.addEdgeWithVertices(this.grafo, a.getD1(), a.getD2(), a.getPeso());
			
		}
	}
	public int getV() {
		return this.grafo.vertexSet().size();
	}
	public int getA() {
		return this.grafo.edgeSet().size();
	}
	public Set<Director> getVertici(){
		return this.grafo.vertexSet();
	}
	public List<Arco> getAdiacenti(Director d){
		List<Director> vicini = Graphs.neighborListOf(this.grafo, d);
		List<Arco> lista = new ArrayList<>();
		for (Director s: vicini) {
			DefaultWeightedEdge e = this.grafo.getEdge(d, s);
			int peso = (int) this.grafo.getEdgeWeight(e);
			lista.add(new Arco(d,s,peso));
		}
		return lista;
		
	}
	
	public List<Director> trovaPercorso(int c, Director d ){
		this.best= new ArrayList<>();
		this.max = 0;
		List<Director> parziale = new ArrayList<>();
		parziale.add(d);
		ricorsione(parziale,d,c);
		return this.best;
		
	}

	private void ricorsione(List<Director> parziale, Director d, int c) {
		Director corrente = parziale.get(parziale.size()-1);
		List<Director> nuoviVicini = Graphs.neighborListOf(this.grafo, corrente);
		//condizione di uscita
		if (parziale.size()>= max) {
			this.max = parziale.size();
			this.best = new ArrayList<>(parziale);
		}
		
		//caso normale
		for (Director d1: nuoviVicini) {
			if (!parziale.contains(d1)) {
				DefaultWeightedEdge e = this.grafo.getEdge(corrente, d1);
				double peso = this.grafo.getEdgeWeight(e);
				if ((peso+calcolaPeso(parziale))<= c) {
					parziale.add(d1);
					ricorsione(parziale, d1, c);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	}

	public  double calcolaPeso(List<Director> parziale) {
		int peso =0;
		if (parziale.size()<=1) {
			return peso;
		}
		
		for (int i =0; i<parziale.size()-1; i++) {
			DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(i), parziale.get(i+1));
			peso+= this.grafo.getEdgeWeight(e);
		}
		return peso;
	}
	
}
