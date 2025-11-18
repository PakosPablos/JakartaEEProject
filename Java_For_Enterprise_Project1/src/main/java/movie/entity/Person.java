package movie.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Surname", nullable = false, length = 255)
    private String surname;

    @Column(name = "Name", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "director")
    private List<Movie> directedMovies;

    @OneToMany(mappedBy = "person")
    private List<MovieActor> actingRoles;

    public String getFullName() {
        return surname + " " + name;
    }

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
