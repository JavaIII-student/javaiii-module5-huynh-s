package module5;
/**
 * Controller for add/edit panel
 * @author Susan Huynh
 * 
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddEditController implements Initializable{
	
    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea descTextArea;

    @FXML
    private Spinner<Integer> ratingSpinner;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    boolean isAdd = true;
    Movie selectedMovie = null;
    
    public void setIsAdd(boolean isAdd) {
    	this.isAdd = isAdd;
    }
    
    public void setSelectedMovie(Movie selectedMovie) {
    	this.selectedMovie = selectedMovie;
    	
    	nameTextField.setText(this.selectedMovie.getName());
    	descTextArea.setText(this.selectedMovie.getDescription());
    	ratingSpinner.getValueFactory().setValue(this.selectedMovie.getRating());
    }
    
    /**
     * Closes window without edits
     * @param event cancel button pressed
     */
    @FXML
    void cancelButtonPressed(ActionEvent event) {
    	MoviesController.newStage.close();
    }

    /**
     * Adds new movie to database
     * @param event ok button pressed
     */
    @FXML
    void okButtonPressed(ActionEvent event) {
    	String name = nameTextField.getText().trim();
    	String desc = descTextArea.getText().trim();
    	int rating = ratingSpinner.getValue().intValue();
    	
    	Movie movie = new Movie(name, desc, rating);
    	MovieQueries query = new MovieQueries();
    	
    	int result = query.addMovie(movie);
        if(result == 1) {
        	MoviesController.data.add(movie);
        	System.out.println("Movie added");
        } else {
        	System.out.println("Unable to add movie");
        }

    	MoviesController.newStage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
