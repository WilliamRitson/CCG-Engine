package application;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class CardController {
	
	public static final DataFormat cardFormat =  new  DataFormat("card");
	
	@FXML 
	private StackPane root;

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
    
    @FXML
    private void initialize() {
    	root.setOnDragDetected(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent event) {
    	    	System.out.println("dragPlay " + event);
    	    	
    	    	/* drag was detected, start a drag-and-drop gesture*/
    	        /* allow any transfer mode */
    	        Dragboard db = root.startDragAndDrop(TransferMode.ANY);
    	            	        
    	        /* Put a string on a dragboard */
    	        ClipboardContent content = new ClipboardContent();
    	        content.putString(model.getName());
    	        db.setContent(content);
    	        
    	        
    	        /* Put a string on a dragboard */
    	        db.setContent(content);
    	        
    	        event.consume();;
    	    }
    	});
    }
    
    private void update() {
    	name.setText(model.getName());
    	cost.setText(Integer.toString(model.getCost()));
    	ruleText.setText(model.getDisplayText());
    	if (model instanceof PersistantCard) {
        	life.setText(Integer.toString(((PersistantCard) model).getLife()));
        	damage.setText(Integer.toString(((PersistantCard) model).getEffectiveDamage()));
    	} else {
    		life.setVisible(false);
    		damage.setVisible(false);
    	}
    };
    
    @FXML
    public void dragPlay(MouseEvent event) {
    	System.out.println("dragPlay " + event);
    	
    	/* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
        Dragboard db = root.startDragAndDrop(TransferMode.ANY);
        
        Map<DataFormat, Object> content = new HashMap<DataFormat,  Object>(1);
        content.put(cardFormat, model);
        
        
        /* Put a string on a dragboard */
        db.setContent(content);
        
        event.consume();
    }

	public Card getModel() {
		return model;
	}

	public void setModel(Card model) {
		this.model = model;
		update();
	}

}
