package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Graph<Author, DefaultEdge> grafo;
	private PortoDAO dao;
	private Map<Integer,Author> authors;
	
	public Model() {
		grafo = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		dao = new PortoDAO();
		
		authors = new HashMap<>();
		List<Author> authorsList = dao.getAuthor();
		for(Author a : authorsList) {
			authors.put(a.getId(), a);
		}
	}
	
	public Set<Author> cercaCoAutori(Author a) {
		creaGrafo();
		return grafo.vertexSet();
	}
	
	public void creaGrafo() {
		
		Graphs.addAllVertices(this.grafo, authors.values());
		
		/*for(Paper p : paper) {
			System.out.println(p.getEprintid());
			List<Author> coauthor = dao.getCoAuthor(p.getEprintid());	
			for(Author a : coauthor) {
				for(Author a1 : coauthor) {
					if(!a1.equals(a)) {
						if(!this.grafo.containsEdge(a, a1) && !this.grafo.containsEdge(a1, a)) {
							this.grafo.addEdge(a, a1);
						}
					}
				}
			}

		}	*/
		
		List<CoAuthor> coauthor = dao.getCoAuthor();
		for(CoAuthor ca : coauthor) {
			Author a1 = authors.get(ca.getIdAuthor1());
			Author a2= authors.get(ca.getIdAuthor2());
			grafo.addEdge(a1, a2);
		}
	}

	public List<Author> getAuthor() {
		return dao.getAuthor();
	}
	
	public Graph<Author, DefaultEdge> getGrafo(){
		return this.grafo;
	}
	
	public List<Author> getCoAuthor(Author author){
		return Graphs.neighborListOf(this.grafo, author);
	}
	
	public List<Author> nonCoAuthor(Author author){
		List<Author> coAuthor = Graphs.neighborListOf(this.grafo, author);
		List<Author> nonCoAuthor = new ArrayList<Author>();
		for(Author a : grafo.vertexSet()) {
			if(!coAuthor.contains(a)){
				nonCoAuthor.add(a);
			}
		}
		return nonCoAuthor;
	}

}
