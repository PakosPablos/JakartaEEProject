package movie.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import movie.entity.Movie;
import movie.entity.Person;

@Stateless
public class MovieService {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;


    // ---------- Movie operations ----------

    // Add a new movie
    public void addMovie(Movie movie) {
        em.persist(movie);
    }

    // Delete movie by id
    public void deleteMovie(Long id) {
        Movie m = em.find(Movie.class, id);
        if (m != null) {
            em.remove(m);
        }
    }

    // Flexible search by multiple criteria
    public List<Movie> searchMovies(
            String title,
            Integer releaseYear,
            String genre,
            String directorName,
            String actorName) {

        String jpql =
            "SELECT DISTINCT m FROM Movie m " +
            "LEFT JOIN m.director d " +
            "LEFT JOIN m.cast c " +
            "LEFT JOIN c.person a " +
            "WHERE 1 = 1";

        if (title != null && !title.isBlank()) {
            jpql += " AND LOWER(m.title) LIKE LOWER(:title)";
        }
        if (releaseYear != null) {
            jpql += " AND m.releaseYear = :releaseYear";
        }
        if (genre != null && !genre.isBlank()) {
            jpql += " AND LOWER(m.genre) LIKE LOWER(:genre)";
        }
        if (directorName != null && !directorName.isBlank()) {
            jpql += " AND LOWER(d.fullName) LIKE LOWER(:directorName)";
        }
        if (actorName != null && !actorName.isBlank()) {
            jpql += " AND LOWER(a.fullName) LIKE LOWER(:actorName)";
        }

        var query = em.createQuery(jpql, Movie.class);

        if (title != null && !title.isBlank()) {
            query.setParameter("title", "%" + title + "%");
        }
        if (releaseYear != null) {
            query.setParameter("releaseYear", releaseYear);
        }
        if (genre != null && !genre.isBlank()) {
            query.setParameter("genre", "%" + genre + "%");
        }
        if (directorName != null && !directorName.isBlank()) {
            query.setParameter("directorName", "%" + directorName + "%");
        }
        if (actorName != null && !actorName.isBlank()) {
            query.setParameter("actorName", "%" + actorName + "%");
        }

        return query.getResultList();
    }

    // ---------- Person operations ----------

    // List all persons (for director dropdown etc.)
   public List<Person> findAllPersons() {
    return em.createQuery(
            "SELECT p FROM Person p ORDER BY p.surname, p.name",
            Person.class)
        .getResultList();
}


    // Find one person by id
    public Person findPersonById(Long id) {
        return em.find(Person.class, id);
    }
}
