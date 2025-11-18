package movie.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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

    public List<Person> findAll() {
        return em.createQuery(
                "SELECT p FROM Person p ORDER BY p.surname, p.name",
                Person.class)
                 .getResultList();
    }

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
}
