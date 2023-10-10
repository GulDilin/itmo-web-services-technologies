package guldilin.repository.impl;

import guldilin.entity.AbstractEntity;
import guldilin.repository.interfaces.CrudRepository;
import guldilin.repository.interfaces.SessionFactoryBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

public class CrudRepositoryImpl<T extends AbstractEntity> implements CrudRepository<T> {

    private final SessionFactoryBuilder sessionFactoryBuilder;
    private final Class<T> tClass;

    public CrudRepositoryImpl(Class<T> tClass, SessionFactoryBuilder sessionFactoryBuilder) {
        this.sessionFactoryBuilder = sessionFactoryBuilder;
        this.tClass = tClass;
    }

    @Override
    public List<T> findByCriteria(CriteriaQuery<T> criteriaQuery) {
        try (EntityManager em = this.createEntityManager()) {
            return em.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public Optional<T> findById(Integer id) {
        try (EntityManager em = this.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(tClass);
            Root<T> root = query.from(tClass);
            query.select(root);
            query.where(cb.equal(root.get("id"), id));
            try {
                return Optional.ofNullable(em.createQuery(query).getSingleResult());
            } catch (Exception exception) {
                return Optional.empty();
            }
        }
    }

    @Override
    public Long countByCriteria(CriteriaQuery<Long> criteriaQuery) {
        try (EntityManager em = this.createEntityManager()) {
            return em.createQuery(criteriaQuery).getSingleResult();
        }
    }

    @Override
    public EntityManager createEntityManager() {
        return sessionFactoryBuilder.getSessionFactory().createEntityManager();
    }

    public String test(String a, String b, String c, String d, String e, String f, String g) {
        return null;
    }
    ;

    @Override
    public Session openSession() {
        return sessionFactoryBuilder.getSessionFactory().openSession();
    }
}
