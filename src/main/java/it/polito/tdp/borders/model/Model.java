package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	SimpleGraph<Country, DefaultEdge> grafo;
	Map<Integer, Country> idMap; 
	BordersDAO dao;
	
	public Model() {
		
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		dao = new BordersDAO();
//		INIZIALIZZARE IDMAP
		idMap = new HashMap<>();
		dao.loadAllCountries(idMap);
		
	}

	public void creaGrafo(int anno) {
		
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);

//		AGGIUNGI I VERTICI
		Graphs.addAllVertices(grafo, dao.getCountryPairs(anno, idMap));
		System.out.println("Numero vertici: " + grafo.vertexSet().size());
//		AGGIUNGO GLI ARCHI
		for(Border b : dao.getEdges(anno, idMap)) {
			grafo.addEdge(b.getState1(), b.getState2());
		}
		System.out.println("Numero archi: " + grafo.edgeSet().size());
	}
	
	public List<CountryAndNumber> getElenco() {
	
		List<CountryAndNumber> confinanti = new LinkedList<>();

		for (Country c : this.grafo.vertexSet()) {
			confinanti.add(new CountryAndNumber(c, this.grafo.degreeOf(c)));  //ritorna un grado di uno specifico vertice
		}

		Collections.sort(confinanti);
		return confinanti;
	}
	
	public List<Country> getCountry() {
		return new LinkedList<Country>(this.grafo.vertexSet());
	}
	
	public Integer compConnesse() {
		
		ConnectivityInspector<Country, DefaultEdge> inspector = new ConnectivityInspector<>(grafo);
		List<Set<Country>> nConnesse = inspector.connectedSets();
		return nConnesse.size();
	}
	
	public String ricercaCompConnessa(Country partenza) {
		
		BreadthFirstIterator<Country, DefaultEdge> iterator =
				new BreadthFirstIterator<Country, DefaultEdge>(grafo, partenza); //iteratore sul grafo partendo da partenza
		List<Country> raggiungibili = new LinkedList<Country>();
		String s = "";
		
		while(iterator.hasNext()) {
			Country c1 = iterator.next();
			raggiungibili.add(c1);
		}
		raggiungibili.remove(partenza);
		for(Country c : raggiungibili) {
			s += c.toString() + "\n";
		}
		return s;
	}
	
	public String ricercaCompConnessa2(Country partenza) {
		
		String s = "";
		ConnectivityInspector<Country, DefaultEdge> ispector = new ConnectivityInspector<>(grafo);
		for(Country c : ispector.connectedSetOf(partenza)) {
			s += c.toString() + "\n";
		}
		return s;
	}
}
