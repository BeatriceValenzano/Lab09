
package it.polito.tdp.borders;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryAndNumber;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    
    @FXML
    private ComboBox<Country> boxStato;

    @FXML
    private Button btnStatiRaggiungibili;


    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	txtResult.clear();
    	boxStato.getItems().clear();
    	try {
    		int anno = Integer.parseInt(txtAnno.getText());
    		
    		if(anno<1816 || anno>2006) 
    			throw new NumberFormatException();    		
    		
    		model.creaGrafo(anno);
    		for(CountryAndNumber cn : model.getElenco()) {
    			txtResult.appendText(cn.toString() + "\n");
    		}
    		
    		txtResult.appendText("Il numero di componenti connesse è: " + model.compConnesse());
    		this.boxStato.getItems().addAll(model.getCountry());
    		Collections.sort(this.boxStato.getItems()); //per ordinare la box
    		
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un numero tra 1816 e 2006!");
    		return; //IMPORTANTE
    	}
    }
    
    @FXML
    void doStatiRaggiungibili(ActionEvent event) {

    	txtResult.clear();
    	Country partenza = this.boxStato.getValue();
    	if(partenza == null) {
    		txtResult.setText("Selezionare una nazione di partenza!");
    	} else {
    		txtResult.appendText("Lo stato " + partenza.getStateName() + " è connesso con:\n");
    		txtResult.appendText(this.model.ricercaCompConnessa(partenza));
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStatiRaggiungibili != null : "fx:id=\"btnStatiRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
