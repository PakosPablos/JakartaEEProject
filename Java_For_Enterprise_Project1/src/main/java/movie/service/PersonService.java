package movie.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import movie.entity.Movie;
import movie.entity.MovieActor;
import movie.entity.Person;

import java.util.List;

@Stateless
public class PersonService {

    @PersistenceContext(unitName = "moviesPU")
    private EntityManager em;

    // ---- Create / Update ----

    public void save(Person person) {
        if (person.getId() == null) {
            em.persist(person);
        } else {
            em.merge(person);
        }
    }

    // ---- Read ----

    /** All persons (for dropdowns etc.) */
    public List<Person> findAll() {
        return em.createQuery(
                "SELECT p FROM Person p ORDER BY p.surname, p.name",
                Person.class)
                 .getResultList();
    }

    /** Generic name search on all persons */
    public List<Person> searchByName(String namePattern) {
        if (namePattern == null || namePattern.isBlank()) {
            return findAll();
        }

        String pattern = "%" + namePattern.toLowerCase() + "%";

        TypedQuery<Person> query = em.createQuery(
                "SELECT p FROM Person p " +
                "WHERE LOWER(p.surname) LIKE :pattern " +
                "   OR LOWER(p.name)    LIKE :pattern " +
                "ORDER BY p.surname, p.name",
                Person.class);

        query.setParameter("pattern", pattern);
        return query.getResultList();
    }

    // ---- Only real DIRECTORS (people referenced in Movie.director) ----

    public List<Person> searchDirectorsByName(String namePattern) {
        StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT d FROM Movie m JOIN m.director d");

        boolean hasFilter = namePattern != null && !namePattern.isBlank();
        if (hasFilter) {
            jpql.append(" WHERE LOWER(d.surname) LIKE :pattern " +
                        "   OR LOWER(d.name)    LIKE :pattern");
        }

        jpql.append(" ORDER BY d.surname, d.name");

        TypedQuery<Person> query =
                em.createQuery(jpql.toString(), Person.class);

        if (hasFilter) {
            String pattern = "%" + namePattern.toLowerCase() + "%";
            query.setParameter("pattern", pattern);
        }

        return query.getResultList();
    }

    // ---- Only real ACTORS (people referenced in MovieActor) ----

    public List<Person> searchActorsByName(String namePattern) {
        StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT a.person FROM MovieActor a");

        boolean hasFilter = namePattern != null && !namePattern.isBlank();
        if (hasFilter) {
            jpql.append(" WHERE LOWER(a.person.surname) LIKE :pattern " +
                        "   OR LOWER(a.person.name)    LIKE :pattern");
        }

        jpql.append(" ORDER BY a.person.surname, a.person.name");

        TypedQuery<Person> query =
                em.createQuery(jpql.toString(), Person.class);

        if (hasFilter) {
            String pattern = "%" + namePattern.toLowerCase() + "%";
            query.setParameter("pattern", pattern);
        }

        return query.getResultList();
    }
}
