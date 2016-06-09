package application;

import javafx.beans.binding.StringExpression;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class PlayerCharacterController {

    @FXML
    private StackPane weapon;

    @FXML
    private Text weponDamage;

    @FXML
    private Text weaponDuribility;

    @FXML
    private Text manaTotal;

    @FXML
    private StackPane heroTarget;

    @FXML
    private Text life;

    @FXML
    private Circle heroPower;

    @FXML
    private Text heroPowerCost;
    
    private PlayerCharacter model;
        
    public void bindModel() { 	
    	StringExpression manaText = model.getParent().getResourceProperty().asString()
    			.concat("/")
    			.concat(model.getParent().getMaxResourcePropety().asString());
		manaTotal.textProperty().bind(manaText);
		life.textProperty().bind(model.getLifeProperty().asString());
    	heroPowerCost.setText("2");
    }
    
    public void setModel(PlayerCharacter newModel) {
		model = newModel;
		bindModel();
		
	}
}

