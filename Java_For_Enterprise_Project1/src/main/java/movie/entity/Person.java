package movie.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Maps to column `Surname`
    @Column(name = "Surname", nullable = false, length = 255)
    private String surname;

    // Maps to column `Name`
    @Column(name = "Name", nullable = false, length = 255)
    private String name;

    // Movies this person directed
    @OneToMany(mappedBy = "director")
    private List<Movie> directedMovies;

    // Acting roles for this person
    @OneToMany(mappedBy = "person")
    private List<MovieActor> actingRoles;

    // -------- Convenience property for JSF label --------
    // This is NOT mapped to a DB column, it's just computed.
    public String getFullName() {
        return surname + " " + name;
    }

    // -------- Getters & setters --------

    public Long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(List<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public List<MovieActor> getActingRoles() {
        return actingRoles;
    }

    public void setActingRoles(List<MovieActor> actingRoles) {
        this.actingRoles = actingRoles;
    }
}
