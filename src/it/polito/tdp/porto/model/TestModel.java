package it.polito.tdp.porto.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		model.creaGrafo();
		
		System.out.println("Grafo creato. Vertici: " + model.getGrafo().vertexSet().size() + " archi: " + model.getGrafo().edgeSet().size());
	}

}
