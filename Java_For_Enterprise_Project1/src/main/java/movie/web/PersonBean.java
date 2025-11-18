package movie.web;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import movie.entity.Person;
import movie.service.PersonService;

import java.io.Serializable;
import java.util.List;

@Named("personBean")
@ViewScoped
public class PersonBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PersonService personService;

    // --- Add person (used by "Add People" page) ---

    private Person newPerson = new Person();
    private List<Person> people;

    // --- Search for actors (only those in MovieActor) ---

    private String actorSearchName;
    private List<Person> actorResults;

    // --- Search for directors (only those in Movie.director) ---

    private String directorSearchName;
    private List<Person> directorResults;

    @PostConstruct
    public void init() {
        people = personService.findAll();
    }

    // ----- Add person -----

    public String savePerson() {
        personService.save(newPerson);
        newPerson = new Person();
        people = personService.findAll();
        return null;
    }

    public void resetNewPerson() {
        newPerson = new Person();
    }

    // ----- Search actors -----

    public void searchActors() {
        actorResults = personService.searchActorsByName(actorSearchName);
    }

    public void resetActorSearch() {
        actorSearchName = null;
        actorResults = null;
    }

    // ----- Search directors -----

    public void searchDirectors() {
        directorResults = personService.searchDirectorsByName(directorSearchName);
    }

    public void resetDirectorSearch() {
        directorSearchName = null;
        directorResults = null;
    }

    // ----- Getters / setters -----

    public Person getNewPerson() {
        return newPerson;
    }

    public void setNewPerson(Person newPerson) {
        this.newPerson = newPerson;
    }

    public List<Person> getPeople() {
        return people;
    }

    public String getActorSearchName() {
        return actorSearchName;
    }

    public void setActorSearchName(String actorSearchName) {
        this.actorSearchName = actorSearchName;
    }

    public List<Person> getActorResults() {
        return actorResults;
    }

    public void setActorResults(List<Person> actorResults) {
        this.actorResults = actorResults;
    }

    public String getDirectorSearchName() {
        return directorSearchName;
    }

    public void setDirectorSearchName(String directorSearchName) {
        this.directorSearchName = directorSearchName;
    }

    public List<Person> getDirectorResults() {
        return directorResults;
    }

    public void setDirectorResults(List<Person> directorResults) {
        this.directorResults = directorResults;
    }
}
