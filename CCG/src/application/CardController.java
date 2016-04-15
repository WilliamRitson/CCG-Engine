package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CardController {

    @FXML
    private ImageView backgroundImage; 

    @FXML
    private Text name;

    @FXML
    private Text cost;

    @FXML
    private ImageView image;

    @FXML
    private Text ruleText;

    @FXML
    private Text life;

    @FXML
    private Text damage;
    
    private Card model;
    
    private void update() {
    	name.setText(model.getName());
    	cost.setText(Integer.toString(model.getCost()));
    	System.out.println("dt " + model.getDisplayText());
    	ruleText.setText(model.getDisplayText());
    	if (model instanceof PersistantCard) {
        	life.setText(Integer.toString(((PersistantCard) model).getLife()));
        	damage.setText(Integer.toString(((PersistantCard) model).getEffectiveDamage()));
    	} else {
    		life.setVisible(false);
    		damage.setVisible(false);
    	}
    };

	public Card getModel() {
		return model;
	}

	public void setModel(Card model) {
		this.model = model;
		update();
	}

}
