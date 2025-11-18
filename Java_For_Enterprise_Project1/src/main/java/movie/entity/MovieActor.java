package movie.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "movie_actor")
public class MovieActor {

    @EmbeddedId
    private MovieActorId id;

    @ManyToOne
    @MapsId("movieId")   // uses movieId from embedded id
    @JoinColumn(
        name = "movie_id",
        foreignKey = @ForeignKey(name = "fk_movie_actor_movie")
    )
    private Movie movie;

    @ManyToOne
    @MapsId("personId")  // uses personId from embedded id
    @JoinColumn(
        name = "person_id",
        foreignKey = @ForeignKey(name = "fk_movie_actor_person")
    )
    private Person person;

    @Column(name = "billing_order")
    private Integer billingOrder;

    // --- Constructors ---

    public MovieActor() {}

    public MovieActor(Movie movie, Person person, Integer billingOrder) {
        this.movie = movie;
        this.person = person;
        this.billingOrder = billingOrder;
        this.id = new MovieActorId(
            movie != null ? movie.getId() : null,
            person != null ? person.getId() : null
        );
    }

    // --- Getters & setters ---

    public MovieActorId getId() {
        return id;
    }

    public void setId(MovieActorId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getBillingOrder() {
        return billingOrder;
    }

    public void setBillingOrder(Integer billingOrder) {
        this.billingOrder = billingOrder;
    }
}
