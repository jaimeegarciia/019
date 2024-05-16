package com.example.act2605;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField releaseDateField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField actorsField;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Movie> movieTable;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> authorColumn;

    @FXML
    private TableColumn<Movie, String> releaseDateColumn;

    @FXML
    private TableColumn<Movie, String> genreColumn;

    @FXML
    private TableColumn<Movie, String> actorsColumn;

    private static final String CONNECTION_STRING = "mongodb+srv://lector:lector@cluster0.ro73oyu.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "pelicula";
    private static final String COLLECTION_NAME = "ejemplos";

    private MongoDatabase database;

    @FXML
    protected void initialize() {
        // Initialize table columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        releaseDateColumn.setCellValueFactory(cellData -> cellData.getValue().releaseDateProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        actorsColumn.setCellValueFactory(cellData -> cellData.getValue().actorsProperty());

        // Connect to MongoDB
        MongoClient mongoClient = MongoClients.create(new ConnectionString(CONNECTION_STRING));
        database = mongoClient.getDatabase(DATABASE_NAME);

        // Retrieve movies from the database and populate the table
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document movieDocument = cursor.next();
            Movie movie = new Movie(
                    movieDocument.getString("title"),
                    movieDocument.getString("author"),
                    movieDocument.getString("releaseDate"),
                    movieDocument.getString("genre"),
                    movieDocument.getString("actors")
            );
            movieTable.getItems().add(movie);
        }
    }

    @FXML
    protected void onSaveButtonClick() {
        // Get input values
        String title = titleField.getText();
        String author = authorField.getText();
        String releaseDate = releaseDateField.getText();
        String genre = genreField.getText();
        String actors = actorsField.getText();

        // Create a document to store in the database
        Document movieDocument = new Document("title", title)
                .append("author", author)
                .append("releaseDate", releaseDate)
                .append("genre", genre)
                .append("actors", actors);

        // Get the collection from the database
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        // Insert the document into the collection
        collection.insertOne(movieDocument);

        // Add movie to the table
        Movie newMovie = new Movie(title, author, releaseDate, genre, actors);
        movieTable.getItems().add(newMovie);

        // Clear input fields
        clearInputFields();
    }

    private void clearInputFields() {
        titleField.clear();
        authorField.clear();
        releaseDateField.clear();
        genreField.clear();
        actorsField.clear();
    }
}