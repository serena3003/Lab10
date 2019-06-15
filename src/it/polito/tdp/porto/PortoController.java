package it.polito.tdp.porto;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	Author author = boxPrimo.getValue();
    	
    	//cerca coAutori
    	model.cercaCoAutori(author);
    	txtResult.appendText("I coautori di "+ author.getFirstname() + author.getLastname() + " sono: \n");
    	List<Author> coAuthor = model.getCoAuthor(author);
    	for(Author a : coAuthor) {
    		txtResult.appendText(a.toString() + "\n");
    	}
    	
    	// riempi ed abilita la seconda tendina
    	boxSecondo.getItems().clear();
    	boxSecondo.getItems().addAll(model.nonCoAuthor(author)) ;
    	boxSecondo.setDisable(false);
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        
        boxSecondo.setDisable(true);
    }

	public void setmodel(Model model) {
		this.model = model;
		List<Author> authors = this.model.getAuthor();
		Collections.sort(authors);
		
		boxPrimo.getItems().addAll(authors);
		
	}
}
