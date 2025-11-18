package movie.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import movie.entity.Movie;
import movie.entity.Person;
import movie.entity.MovieActor;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class MovieService {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;

    public void addMovie(Movie movie, Long directorPersonId) {
        if (directorPersonId != null) {
            Person director = em.find(Person.class, directorPersonId);
            movie.setDirector(director);
        }
        em.persist(movie);
    }

    public void addMovie(Movie movie) {
        em.persist(movie);
    }

    public List<Movie> findAllMovies() {
        return em.createQuery(
                "SELECT m FROM Movie m ORDER BY m.title",
                Movie.class)
                 .getResultList();
    }


    public List<Person> findAllPersons() {
        return em.createQuery(
                "SELECT p FROM Person p ORDER BY p.surname, p.name",
                Person.class)
                 .getResultList();
    }



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

  public void addActorToMovie(Long movieId, Long personId, Integer billingOrder) {
    if (movieId == null || personId == null) {
        throw new IllegalArgumentException("Movie and person must be selected");
    }

    Movie movie = em.find(Movie.class, movieId);
    Person person = em.find(Person.class, personId);

    if (movie == null || person == null) {
        throw new IllegalArgumentException("Invalid movie or person id");
    }

    MovieActor assignment = new MovieActor();
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
