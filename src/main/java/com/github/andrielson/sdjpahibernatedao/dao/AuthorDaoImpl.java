package com.github.andrielson.sdjpahibernatedao.dao;

import com.github.andrielson.sdjpahibernatedao.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Author getById(Long id) {
        var em = getEntityManager();
        var author = em.find(Author.class, id);
        em.close();
        return author;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        var em = getEntityManager();
        var query = em.createQuery("SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        var author = query.getSingleResult();
        em.close();
        return author;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        var em = getEntityManager();

        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        var em = getEntityManager();

        try {
            em.joinTransaction();
            em.merge(author);
            em.flush();
            em.clear();
            var saved = em.find(Author.class, author.getId());
            return saved;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAuthorById(Long id) {
        var em = getEntityManager();
        em.getTransaction().begin();
        var author = em.find(Author.class, id);
        em.remove(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        var em = getEntityManager();

        try {
            var query = em.createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :last_name", Author.class);
            query.setParameter("last_name", lastName + "%");
            var authors = query.getResultList();
            return authors;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author> findAll() {
        var em = getEntityManager();

        try {
            var query = em.createNamedQuery("author_find_all", Author.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        var em = getEntityManager();
        try {
            var criteriaBuilder = em.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Author.class);

            var root = criteriaQuery.from(Author.class);

            var firstNameParam = criteriaBuilder.parameter(String.class);
            var lastNameParam = criteriaBuilder.parameter(String.class);

            var firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            var lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            var query = em.createQuery(criteriaQuery);
            query.setParameter(firstNameParam, firstName);
            query.setParameter(lastNameParam, lastName);

            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        var em = getEntityManager();

        try {
            var query = em.createNativeQuery("SELECT * FROM author a WHERE a.first_name = ? AND a.last_name = ?", Author.class);
            query.setParameter(1, firstName);
            query.setParameter(2, lastName);

            return (Author) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
