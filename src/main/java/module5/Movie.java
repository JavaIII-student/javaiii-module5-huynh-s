package module5;

/**
 * Movie class that consists of a name, description, rating, and ID
 * @author Susan Huynh
 *
 */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie {
	private StringProperty name = new SimpleStringProperty("");
	private StringProperty description = new SimpleStringProperty("");
	private IntegerProperty rating = new SimpleIntegerProperty(1);
	private IntegerProperty id = new SimpleIntegerProperty(-1);
	
	/**
	 * Default Movie constructor
	 */
	public Movie() {
	}
	
	/**
	 * Parameterized constructor that sets name, description, and rating
	 * @param name movie name to set
	 * @param description movie description to set
	 * @param rating integer 1-10 to set as rating
	 */
	public Movie(String name, String description, int rating) {
		this.name.set(name);
		this.description.set(description);
		this.rating.set(rating);;
	}
	
	/**
	 * Gets the movie ID
	 * @return move ID
	 */
	public int getId() {
		return id.get();
	}
	
	/**
	 * Sets the movie ID
	 * @param id movie ID to set
	 */
	public void setId(int id) {
		this.id.set(id);
	}
	
	/**
	 * Gets the movie rating
	 * @return movie rating
	 */
	public int getRating() {
		return rating.get();
	}
	
	/**
	 * Sets the movie rating
	 * @param rating movie rating to set
	 */
	public void setRating(int rating) {
		this.rating.set(rating);
	}
	
	/**
	 * Gets the movie name
	 * @return movie name
	 */
	public String getName() {
		return name.get();
	}
	
	/**
	 * Sets movie name
	 * @param name movie name to set
	 */
	public void setName(String name) {
		this.name.set(name);
	}
	
	/**
	 * Gets the movie description
	 * @return movie description
	 */
	public  String getDescription() {
		return description.get();
	}
	
	/**
	 * Sets the movie description
	 * @param description movie description to set
	 */
	public void setDescription(String description) {
		this.description.set(description);
	}
}
