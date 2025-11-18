package movie.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import movie.entity.Movie;
import movie.entity.Person;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class MovieService {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;

    // ---- Create ----

    /**
     * Add a movie and (optionally) set its director from a Person id.
     */
    public void addMovie(Movie movie, Long directorPersonId) {
        if (directorPersonId != null) {
            Person director = em.find(Person.class, directorPersonId);
            movie.setDirector(director);
        }
        em.persist(movie);
    }

    /**
     * Old-style method kept for compatibility (in case something else still calls it).
     * It just persists the movie as-is.
     */
    public void addMovie(Movie movie) {
        em.persist(movie);
    }

    // ---- Read: lists ----

    /**
     * Return all movies ordered by title.
     */
    public List<Movie> findAllMovies() {
        return em.createQuery(
                "SELECT m FROM Movie m ORDER BY m.title",
                Movie.class)
                 .getResultList();
    }

    /**
     * Return all persons ordered by surname + name.
     */
    public List<Person> findAllPersons() {
        return em.createQuery(
                "SELECT p FROM Person p ORDER BY p.surname, p.name",
                Person.class)
                 .getResultList();
    }

    // ---- Search movies ----
    // title / year / genre / directorName are used.
    // actorName is accepted but not yet used (extension point).

    public List<Movie> searchMovies(String title,
                                    Integer releaseYear,
                                    String genre,
                                    String directorName,
                                    String actorName) {

        StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT m FROM Movie m LEFT JOIN m.director d WHERE 1=1");

        if (title != null && !title.isBlank()) {
            jpql.append(" AND LOWER(m.title) LIKE LOWER(:title)");
        }
        if (releaseYear != null) {
            jpql.append(" AND m.releaseYear = :year");
        }
        if (genre != null && !genre.isBlank()) {
            jpql.append(" AND LOWER(m.genre) LIKE LOWER(:genre)");
        }
        if (directorName != null && !directorName.isBlank()) {
            jpql.append(" AND (LOWER(d.surname) LIKE LOWER(:dname) " +
                        "OR LOWER(d.name) LIKE LOWER(:dname))");
        }

        // (actorName could be used later with a join to MovieActor)

        TypedQuery<Movie> query =
                em.createQuery(jpql.toString(), Movie.class);

        if (title != null && !title.isBlank()) {
            query.setParameter("title", "%" + title + "%");
        }
        if (releaseYear != null) {
            query.setParameter("year", releaseYear);
        }
        if (genre != null && !genre.isBlank()) {
            query.setParameter("genre", "%" + genre + "%");
        }
        if (directorName != null && !directorName.isBlank()) {
            String pattern = "%" + directorName + "%";
            query.setParameter("dname", pattern);
        }

        return query.getResultList();
    }

        // ---- Actor assignments (MovieActor) ----

    public void addActorToMovie(Long movieId, Long personId, Integer billingOrder) {
        if (movieId == null || personId == null) {
            throw new IllegalArgumentException("Movie and person must be selected");
        }

        Movie movie = em.find(Movie.class, movieId);
        Person person = em.find(Person.class, personId);

        if (movie == null || person == null) {
            throw new IllegalArgumentException("Invalid movie or person id");
        }

        movie.entity.MovieActor assignment = new movie.entity.MovieActor();
        assignment.setMovie(movie);
        assignment.setPerson(person);
        assignment.setBillingOrder(billingOrder);

        em.persist(assignment);
    }

    public List<MovieActor> findAllActorAssignments() {
    return em.createQuery(
            "SELECT a FROM MovieActor a " +
            "JOIN FETCH a.movie " +
            "JOIN FETCH a.person " +
            "ORDER BY a.movie.title, a.billingOrder",
            MovieActor.class)
            .getResultList();
}


}
