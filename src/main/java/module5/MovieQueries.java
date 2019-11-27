package module5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieQueries {
	private static final String URL = "jdbc:derby:movies;create=true";

	private Connection connection;
	private PreparedStatement selectAllMovies;
	private PreparedStatement selectMovieByName;
	private PreparedStatement insertNewMovie;
	private PreparedStatement updateMovie;
	private PreparedStatement deleteMovie;

	public MovieQueries() {
		try {
			System.out.println("Connecting to database URL: " + URL);
			connection = DriverManager.getConnection(URL);
			createTable();
//			dropTable();

			selectAllMovies = connection.prepareStatement("SELECT * FROM movies ORDER BY name");
			selectMovieByName = connection.prepareStatement("SELECT * FROM movies WHERE name LIKE ? " +
															"ORDER BY name");
			insertNewMovie = connection.prepareStatement("INSERT INTO movies " +
														 "(name, description, rating) " +
														 "VALUES (?, ?, ?)");
			updateMovie = connection.prepareStatement("UPDATE movies " +
													  "SET name = ?, description = ?, rating = ? " +
													  "WHERE id = ?");
			deleteMovie = connection.prepareStatement("DELETE FROM movies WHERE id = ?");

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public List<Movie> getAllMovies() {
		try(ResultSet resultSet = selectAllMovies.executeQuery()) {
			List<Movie> results = new ArrayList<>();
			
			while(resultSet.next()) {
				Movie movie = new Movie(
						resultSet.getString("name"),
						resultSet.getString("description"),
						resultSet.getInt("rating"));
				movie.setId(resultSet.getInt("id"));
				results.add(movie);
			}
			return results;
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}
	
	public List<Movie> getMovieByName(String name){
		try {
			selectMovieByName.setString(1, name.trim());
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
			return null;
		}
		
		try(ResultSet resultSet = selectMovieByName.executeQuery()) {
			List<Movie> results = new ArrayList<>();
			
			while(resultSet.next()) {
				results.add(new Movie(
						resultSet.getString("name"),
						resultSet.getString("description"),
						resultSet.getInt("rating")));
			}
			return results;
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}
	
	public int addMovie(Movie newMovie) {
		try {
			insertNewMovie.setString(1, newMovie.getName());
			insertNewMovie.setString(2, newMovie.getDescription());;
			insertNewMovie.setInt(3, newMovie.getRating());
			
			int result = insertNewMovie.executeUpdate();
			
			PreparedStatement idQuery = connection.prepareStatement("SELECT MAX(id) AS newid FROM movies");
			
			try(ResultSet resultSet = idQuery.executeQuery()) {
				resultSet.next();
				newMovie.setId(resultSet.getInt("newid"));
			} catch(SQLException sqlException) {
				sqlException.printStackTrace();
			}
			
			return result;
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
			return 0;
		}
	}
	
	public int updateMovie(Movie updatedMovie) {
		try {
			updateMovie.setString(1, updatedMovie.getName());
			updateMovie.setString(2, updatedMovie.getDescription());;
			updateMovie.setInt(3, updatedMovie.getRating());
			updateMovie.setInt(4, updatedMovie.getId());
			
			return updateMovie.executeUpdate();
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
			return 0;
		}
	}
	
	public int removeMovie(int id) {
		try {
			deleteMovie.setInt(1, id);
			return deleteMovie.executeUpdate();
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
			return 0;
		}
	}

	public void createTable() {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.execute("CREATE TABLE movies (" + 
						 "id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " + 
						 "name VARCHAR(255) NOT NULL, " + 
						 "description VARCHAR(255), " +
						 "rating INTEGER)");
			System.out.println("Table created");
		} catch(SQLException sqlException) {
//			sqlException.printStackTrace();
			System.out.println("Table already exists");
		}
	}
	
	public void dropTable() {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.execute("DROP TABLE movies");
			System.out.println("Table dropped");
		} catch(SQLException sqlException) {
			System.out.println("Table doesn't exists");
		}
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException sqlExeption) {
			sqlExeption.printStackTrace();
		}
	}
}
