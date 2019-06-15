package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private Graph<Author, DefaultEdge> grafo;
	private PortoDAO dao;
	private Map<Integer, Author> authors;
	private Set<CoAuthor> coAuthors;

	public Model() {
		grafo = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		dao = new PortoDAO();
		coAuthors = dao.getCoAuthor();

		authors = new HashMap<>();
		List<Author> authorsList = dao.getAuthor();
		for (Author a : authorsList) {
			authors.put(a.getId(), a);
		}
	}

	public Set<Author> cercaCoAutori(Author a) {
		creaGrafo();
		return grafo.vertexSet();
	}

	public void creaGrafo() {

		Graphs.addAllVertices(this.grafo, authors.values());

		for (CoAuthor ca : coAuthors) {
			Author a1 = authors.get(ca.getIdAuthor1());
			Author a2 = authors.get(ca.getIdAuthor2());
			grafo.addEdge(a1, a2);
		}
	}

	public List<Author> getAuthor() {
		return dao.getAuthor();
	}

	public Graph<Author, DefaultEdge> getGrafo() {
		return this.grafo;
	}

	public List<Author> getCoAuthor(Author author) {
		return Graphs.neighborListOf(this.grafo, author);
	}

	public List<Author> nonCoAuthor(Author author) {
		List<Author> coAuthor = Graphs.neighborListOf(this.grafo, author);
		coAuthor.add(author);
		List<Author> nonCoAuthor = new ArrayList<Author>();
		for (Author a : grafo.vertexSet()) {
			if (!coAuthor.contains(a)) {
				nonCoAuthor.add(a);
			}
		}
		Collections.sort(nonCoAuthor);
		return nonCoAuthor;
	}

	public List<Paper> trovaSequenza(Author author1, Author author2) {

		List<Author> resultBF = new ArrayList<Author>();
		List<Paper> sequenzaPaper = new ArrayList<Paper>();

		//cammino minimo
		ShortestPathAlgorithm<Author, DefaultEdge> dijkstra = 
				new DijkstraShortestPath<Author, DefaultEdge>(this.grafo);
		
		GraphPath<Author,DefaultEdge> gp = dijkstra.getPath(author1, author2);

		// esploro il cammino minimo		
		for(DefaultEdge e : gp.getEdgeList()) {
			Author a1 = grafo.getEdgeSource(e);
			Author a2 = grafo.getEdgeTarget(e);
			sequenzaPaper.addAll(dao.getPaperByAuthors(a1, a2));
		}

		return sequenzaPaper;
	}

}
