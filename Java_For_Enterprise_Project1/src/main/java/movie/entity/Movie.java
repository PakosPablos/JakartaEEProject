package movie.entity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(
    name = "movie",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "Unique_title_year",
            columnNames = {"title", "release_year"}
        )
    }
)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(length = 100)
    private String genre;

    @Column(name = "runtime_minutes")
    private Integer runtimeMinutes;

    @Column(length = 2000)
    private String plot;

    @Column(name = "poster_url", length = 800)
    private String posterUrl;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "director_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "movie_director")
    )
    private Person director;

    // Actors for this movie, via movie_actor table
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("billingOrder ASC")
    private List<MovieActor> cast;

    // --- Getters & setters ---

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public List<MovieActor> getCast() {
        return cast;
    }

    public void setCast(List<MovieActor> cast) {
        this.cast = cast;
    }
}
