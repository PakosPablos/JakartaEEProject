package movie.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // person.id

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    // Movies this person directed
    @OneToMany(mappedBy = "director")
    private List<Movie> directedMovies;

    // Acting roles (join rows) for this person
    @OneToMany(mappedBy = "person")
    private List<MovieActor> actingRoles;

    // --- Getters & setters ---

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
