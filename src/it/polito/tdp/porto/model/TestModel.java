package it.polito.tdp.porto.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		model.creaGrafo();
		
		System.out.println("Grafo creato. Vertici: " + model.getGrafo().vertexSet().size() + " archi: " + model.getGrafo().edgeSet().size());
		
		Author author1 = new Author(1877,	"Tadei"	,"Roberto");
		Author author2 = new Author(2185,	"Taragna",	"Michele");
		
		List<Paper> papers = model.trovaSequenza(author1, author2);
		if(papers.isEmpty()) {
			System.out.println("\nLista papers è vuota!");
		}
		else 
			for(Paper p : papers) {
			System.out.println(p.getTitle() + "\n");
			}
		
	}

}
