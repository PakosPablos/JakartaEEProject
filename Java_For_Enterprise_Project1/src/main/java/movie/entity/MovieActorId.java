package movie.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieActorId implements Serializable {

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "person_id")
    private Long personId;

    public MovieActorId() {
    }

    public MovieActorId(Long movieId, Long personId) {
        this.movieId = movieId;
        this.personId = personId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieActorId)) return false;
        MovieActorId that = (MovieActorId) o;
        return Objects.equals(movieId, that.movieId)
            && Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, personId);
    }
}
