package application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CardEditorTabController {

    @FXML
    private TextField cost;

    @FXML
    private ChoiceBox<?> cardType;

    @FXML
    private TextField attack;

    @FXML
    private TextField life;
    
    private File cardFile;
    
    private Card model;

    @FXML
    private TextArea ruleText;
    
	void getDataFromView() {
		model.setCost(Integer.parseInt(cost.getText()));
		model.setRuleText(ruleText.getText());

		if (model instanceof  Minion) {
			((Minion) model).setLife(Integer.parseInt(life.getText()));
			((Minion) model).setDamage(Integer.parseInt(attack.getText()));
		}
	}
	
	void pushDataToView() {
		cost.setText(String.valueOf(model.getCost()));
		ruleText.setText(model.getRuleText());
		
		if (model instanceof  Minion) {
			life.setText(String.valueOf(((Minion) model).getLife()));
			attack.setText(String.valueOf(((Minion) model).getDamage()));
		}
	}

	public void setFile(File cardFile) {
		this.cardFile = cardFile;
		model = Minion.loadFromXML(cardFile);
		pushDataToView();
	}
	
	/**
	 * Saves a card to a file as xml
	 * 
	 * @param card
	 *            - the card to save
	 * @param file
	 *            - the file to save it to
	 */
	public void save() {
		getDataFromView();
		if (model instanceof Minion) {
			Minion.saveToXML((Minion) model, cardFile);
		}
	}


}
