package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
private List<Author> autori ;
	
	private SimpleGraph<Author, DefaultEdge> graph ;
	
	public Model() {
		
	}

	public List<Author> trovaCoAutori(Author a) {
	
		if(this.graph==null)
			creaGrafo() ;
		
		List<Author> coautori = Graphs.neighborListOf(this.graph, a) ;
		return coautori ;
	}
	
	public void creaGrafo() {
		PortoDAO dao = new PortoDAO() ;
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;
		
		Graphs.addAllVertices(this.graph, getAutori()) ;
		
		for(Author a: graph.vertexSet()) {
			List<Author> coautori = dao.getCoAutori(a) ;
			for(Author a2: coautori) {
				if(this.graph.containsVertex(a2) && this.graph.containsVertex(a))
					this.graph.addEdge(a, a2) ;
			}
		}
		
		System.out.println("Vertici: " + graph.vertexSet().size());
		System.out.println("Archi: " + graph.edgeSet().size());
	}

	public List<Author> getAutori() {
		
		if(this.autori==null) {
			PortoDAO dao = new PortoDAO() ;
			this.autori = dao.listAutori() ;
			if(this.autori==null) 
				throw new RuntimeException("Errore con il database") ;
		}
		
		return this.autori ;
	}
	
	/**
	 * Trova una sequenza di articoli che legano l'autore {@code a1} all'autore
	 * {@code a2}
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public List<Paper> sequenzaArticoli(Author a1, Author a2) {

		List<Paper> result = new ArrayList<>() ; 
		PortoDAO dao = new PortoDAO();

				
		// trovo un cammino minimo tra a1 ed a2
		ShortestPathAlgorithm<Author, DefaultEdge> dijkstra = 
				new DijkstraShortestPath<Author, DefaultEdge>(this.graph);
		
		GraphPath<Author,DefaultEdge> gp = dijkstra.getPath(a1, a2);

		List<DefaultEdge> edges = gp.getEdgeList();
		
		// ciascun edge corrisponderà ad un paper!
		
		for(DefaultEdge e: edges) {
			// autori che corrispondono all'edge
			Author as = graph.getEdgeSource(e) ;
			Author at = graph.getEdgeTarget(e) ;
			// trovo l'articolo
			Paper p = dao.articoloComune(as, at) ;
			if(p == null)
				throw new InternalError("Paper not found...") ;
			result.add(p) ;
		}
		
		return result ;

	}

}
