package guldilin.repository.impl;

import guldilin.entity.AbstractEntity;
import guldilin.repository.interfaces.CrudRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CrudRepositoryImpl<T extends AbstractEntity> implements CrudRepository<T> {

    private final SessionFactory sessionFactory;
    private final Class<T> tClass;

    public CrudRepositoryImpl(Class<T> tClass, SessionFactory sessionFactoryBuilder) {
        this.sessionFactory = sessionFactoryBuilder;
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
        return sessionFactory.createEntityManager();
    }

    @Override
    public Session openSession() {
        return sessionFactory.openSession();
    }
}
