package com.example.act2605;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movie {
    private final StringProperty title;
    private final StringProperty author;
    private final StringProperty releaseDate;
    private final StringProperty genre;
    private final StringProperty actors;

    public Movie(String title, String author, String releaseDate, String genre, String actors) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.releaseDate = new SimpleStringProperty(releaseDate);
        this.genre = new SimpleStringProperty(genre);
        this.actors = new SimpleStringProperty(actors);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public StringProperty releaseDateProperty() {
        return releaseDate;
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public StringProperty actorsProperty() {
        return actors;
    }
}
