package module5;
/**
 * Main controller containing movie database table
 * @author Susan Huynh
 *
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MoviesController implements Initializable {
    public static final ObservableList<Movie> data = FXCollections.observableArrayList();
    public static Stage newStage = new Stage();
    private final MovieQueries movieQueries = new MovieQueries();
    
    @FXML
    private TableView<Movie> movieDBTable;

    @FXML
    private TableColumn<Movie, String> idCol;

    @FXML
    private TableColumn<Movie, String> nameCol;

    @FXML
    private TableColumn<Movie, String> descCol;

    @FXML
    private TableColumn<Movie, String> ratingCol;

    @FXML
    private TextField searchBar;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;
    
    /**
     * Creates add/edit panel when Add button is pressed
     * @param event add button pressed
     */
    @FXML
    void addButtonPressed(ActionEvent event) {
    	showAddStage();
    }

    /**
     * Removes entry from table/database
     * @param event delete button pressed
     */
    @FXML
    void deleteButtonPressed(ActionEvent event) {
    	Movie removie = movieDBTable.getSelectionModel().getSelectedItem();
    	int movieId = removie.getId();
    	int result = movieQueries.removeMovie(movieId);
    	if(result == 1) {
    		data.remove(removie);
    		System.out.println("Movie deleted");
    	} else {
    		System.out.println("Unable to delete movie: " + removie.getName() + " (" + result + ")");
    	}
    }

    @FXML
    void editButtonPressed(ActionEvent event) {
    	showEditStage();
    }

    @FXML
    void searchStringChanged(KeyEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //editButton.disableProperty().bind(Bindings.isEmpty(movieDBTable.getSelectionModel().getSelectedItems()));
        deleteButton.disableProperty().bind(Bindings.isEmpty(movieDBTable.getSelectionModel().getSelectedItems()));
        
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        movieQueries.createTable();
        
        movieDBTable.setItems(data);
        getAllEntries();
        
    }
    
    /**
     * Sets all data in table
     */
    private void getAllEntries() {
    	data.setAll(movieQueries.getAllMovies());
    }
    
    /**
     * Generates add panel
     */
    public void showAddStage() {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("addEdit.fxml"));
			
			Scene scene = new Scene(root);
	    	newStage.setScene(scene);
	    	newStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Generates edit panel
     */
    public void showEditStage() {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("addEdit.fxml"));
			
			Scene scene = new Scene(root);
	    	newStage.setScene(scene);
	    	newStage.show();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
}
