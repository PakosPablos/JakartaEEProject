package movie.web;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import movie.entity.Movie;
import movie.entity.MovieActor;
import movie.entity.Person;
import movie.service.MovieService;
import movie.service.PersonService;

import java.io.Serializable;
import java.util.List;

@Named("actorBean")
@ViewScoped
public class ActorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private MovieService movieService;

    @EJB
    private PersonService personService;

    private Long selectedMovieId;
    private Long selectedPersonId;
    private Integer billingOrder;

    private List<Movie> movies;
    private List<Person> people;
    private List<MovieActor> assignments;

    @PostConstruct
    public void init() {
        movies = movieService.findAllMovies();
        people = personService.findAll();
        assignments = movieService.findAllActorAssignments();
    }

    public String addActorToMovie() {
        movieService.addActorToMovie(selectedMovieId, selectedPersonId, billingOrder);
        selectedMovieId = null;
        selectedPersonId = null;
        billingOrder = null;
        assignments = movieService.findAllActorAssignments();
        return null;
    }
    public Long getSelectedMovieId() {
        return selectedMovieId;
    }

    public void setSelectedMovieId(Long selectedMovieId) {
        this.selectedMovieId = selectedMovieId;
    }

    public Long getSelectedPersonId() {
        return selectedPersonId;
    }

    public void setSelectedPersonId(Long selectedPersonId) {
        this.selectedPersonId = selectedPersonId;
    }

    public Integer getBillingOrder() {
        return billingOrder;
    }

    public void setBillingOrder(Integer billingOrder) {
        this.billingOrder = billingOrder;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Person> getPeople() {
        return people;
    }

    public List<MovieActor> getAssignments() {
        return assignments;
    }
}
