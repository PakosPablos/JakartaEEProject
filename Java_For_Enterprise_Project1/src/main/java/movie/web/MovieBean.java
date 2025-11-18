package movie.web;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import movie.entity.Movie;
import movie.entity.Person;
import movie.service.MovieService;

import java.io.Serializable;
import java.util.List;

@Named("movieBean")
@ViewScoped
public class MovieBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private MovieService movieService;

    // --- Add movie ---

    private Movie newMovie = new Movie();
    private Long selectedDirectorId;
    private List<Person> allPeople;
    private List<Movie> allMovies;

    // --- Search movie ---

    private String searchTitle;
    private Integer searchReleaseYear;
    private String searchGenre;
    private String searchDirectorName;
    private String searchActorName;    // currently not used in query
    private List<Movie> searchResults;

    @PostConstruct
    public void init() {
        reloadReferenceData();
    }

    private void reloadReferenceData() {
        allPeople = movieService.findAllPersons();
        allMovies = movieService.findAllMovies();
    }

    // ----- Actions for addMovie page -----

    public String saveMovie() {
        movieService.addMovie(newMovie, selectedDirectorId);
        resetNewMovie();
        reloadReferenceData();
        return null; // stay on same page
    }

    public void resetNewMovie() {
        newMovie = new Movie();
        selectedDirectorId = null;
    }

    // ----- Actions for searchMovie page -----

    public void search() {
        searchResults = movieService.searchMovies(
                searchTitle,
                searchReleaseYear,
                searchGenre,
                searchDirectorName,
                searchActorName
        );
    }

    public void resetSearch() {
        searchTitle = null;
        searchReleaseYear = null;
        searchGenre = null;
        searchDirectorName = null;
        searchActorName = null;
        searchResults = null;
    }

    // ----- Getters / setters -----

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

    public List<Person> getAllPeople() {
        return allPeople;
    }

    public List<Movie> getAllMovies() {
        return allMovies;
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
}
