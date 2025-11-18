package movie.web;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

import movie.Service.MovieService;
import movie.entity.Movie;
import movie.entity.Person;

@Named("movieBean")
@ViewScoped
public class MovieBean implements Serializable {

    @Inject
    private MovieService movieService;

    // ---- Add movie form ----
    private Movie newMovie = new Movie();
    private Long selectedDirectorId;          // chosen director from dropdown

    // ---- Search criteria ----
    private String searchTitle;
    private Integer searchReleaseYear;
    private String searchGenre;
    private String searchDirectorName;
    private String searchActorName;

    // ---- Search results ----
    private List<Movie> searchResults;

    // ---- Dropdown data ----
    private List<Person> allPeople;           // directors/actors from person table

    @PostConstruct
    public void init() {
        // Load people for the director dropdown
        allPeople = movieService.findAllPersons();
    }

    // ========= Actions =========

    // Add a new movie
    public String saveMovie() {
        if (selectedDirectorId != null) {
            Person director = movieService.findPersonById(selectedDirectorId);
            newMovie.setDirector(director);
        }

        movieService.addMovie(newMovie);

        // Reset form
        newMovie = new Movie();
        selectedDirectorId = null;

        // optional: refresh results if user already searched
        search();

        return null;   // stay on same page
    }

    // Delete a movie
    public void removeMovie(Movie movie) {
        if (movie != null && movie.getId() != null) {
            movieService.deleteMovie(movie.getId());
            search(); // refresh table
        }
    }

    // Search using any combination of criteria
    public void search() {
        searchResults = movieService.searchMovies(
                searchTitle,
                searchReleaseYear,
                searchGenre,
                searchDirectorName,
                searchActorName
        );
    }

    // ========= Getters & setters =========

    public Movie getNewMovie() {
        return newMovie;
    }

    public void setNewMovie(Movie newMovie) {
        this.newMovie = newMovie;
    }

    public Long getSelectedDirectorId() {
        return selectedDirectorId;
    }

    public void setSelectedDirectorId(Long selectedDirectorId) {
        this.selectedDirectorId = selectedDirectorId;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public Integer getSearchReleaseYear() {
        return searchReleaseYear;
    }

    public void setSearchReleaseYear(Integer searchReleaseYear) {
        this.searchReleaseYear = searchReleaseYear;
    }

    public String getSearchGenre() {
        return searchGenre;
    }

    public void setSearchGenre(String searchGenre) {
        this.searchGenre = searchGenre;
    }

    public String getSearchDirectorName() {
        return searchDirectorName;
    }

    public void setSearchDirectorName(String searchDirectorName) {
        this.searchDirectorName = searchDirectorName;
    }

    public String getSearchActorName() {
        return searchActorName;
    }

    public void setSearchActorName(String searchActorName) {
        this.searchActorName = searchActorName;
    }

    public List<Movie> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Movie> searchResults) {
        this.searchResults = searchResults;
    }

    public List<Person> getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(List<Person> allPeople) {
        this.allPeople = allPeople;
    }
}
