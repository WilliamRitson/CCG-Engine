package application;

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
    
    
    @FXML
    public void initilize() {
    	update();
    }
    
    public void update() {
    	manaTotal.setText(model.getParent().getResource() + "/" + model.getParent().getMaxResource());
    	life.setText(Integer.toString((model.getLife())));
    	heroPowerCost.setText("2");
    }
    
    public void setModel(PlayerCharacter newModel) {
		model = newModel;
		
	}
}

