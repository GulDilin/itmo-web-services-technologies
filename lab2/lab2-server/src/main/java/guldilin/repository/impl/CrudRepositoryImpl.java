package guldilin.repository.impl;

import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.entity.AbstractEntity;
import guldilin.exceptions.EntryNotFound;
import guldilin.exceptions.FieldIsNotFilterable;
import guldilin.repository.interfaces.CrudRepository;
import guldilin.utils.FilterObjectConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CrudRepositoryImpl<T extends AbstractEntity> implements CrudRepository<T> {

    private final SessionFactory sessionFactory;
    private final Class<T> tClass;

    /**
     * {@inheritDoc}
     */
    public CrudRepositoryImpl(final Class<T> tClass, final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.tClass = tClass;
    }

    /**
     * {@inheritDoc}
     */
    public Predicate getFilterPredicate(
            final CriteriaBuilder cb, final FilterArgumentDTO filterArgument, final Root<?> root)
            throws FieldIsNotFilterable {
        String field = filterArgument.getField();
        FilterObjectConverter.checkIfFieldFilterable(tClass, field);
        return cb.equal(root.get(field), filterArgument.getValue());
    }

    /**
     * {@inheritDoc}
     */
    public List<Predicate> parsePredicates(
            final CriteriaBuilder cb, final Root<T> root, final List<FilterArgumentDTO> filters)
            throws FieldIsNotFilterable {
        List<Predicate> predicates = new ArrayList<>();
        if (filters != null) {
            for (FilterArgumentDTO filterArgument : filters) {
                predicates.add(this.getFilterPredicate(cb, filterArgument, root));
            }
        }
        return predicates;
    }

    /**
     * {@inheritDoc}
     */
    public void applyPredicates(
            final CriteriaBuilder cb, final CriteriaQuery<?> criteriaQuery, final List<Predicate> predicates) {
        if (!predicates.isEmpty()) criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
    }

    /**
     * {@inheritDoc}
     */
    public void applyFilters(
            final CriteriaBuilder cb,
            final Root<T> root,
            final CriteriaQuery<?> criteriaQuery,
            final List<FilterArgumentDTO> filters)
            throws FieldIsNotFilterable {
        List<Predicate> predicates = parsePredicates(cb, root, filters);
        applyPredicates(cb, criteriaQuery, predicates);
    }

    /**
     * {@inheritDoc}
     */
    public CriteriaQuery<T> createFilterQuery(final List<FilterArgumentDTO> filters) throws FieldIsNotFilterable {
        try (EntityManager em = this.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = cb.createQuery(tClass);

            Root<T> root = criteriaQuery.from(tClass);
            criteriaQuery.select(root);
            applyFilters(cb, root, criteriaQuery, filters);

            return criteriaQuery;
        }
    }

    /**
     * {@inheritDoc}
     */
    public CriteriaQuery<Long> createCounterQuery(final List<FilterArgumentDTO> filters) throws FieldIsNotFilterable {
        try (EntityManager em = this.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

            Root<T> root = criteriaQuery.from(tClass);
            criteriaQuery.select(cb.count(root));
            applyFilters(cb, root, criteriaQuery, filters);

            return criteriaQuery;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findByCriteria(final CriteriaQuery<T> criteriaQuery, final PaginationRequestDTO pagination) {
        try (EntityManager em = this.createEntityManager()) {
            return em.createQuery(criteriaQuery)
                    .setFirstResult(pagination.getOffset())
                    .setMaxResults(pagination.getLimit())
                    .getResultList();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> findById(final Integer id) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public T getById(final Integer id) throws EntryNotFound {
        return this.findById(id).orElseThrow(EntryNotFound::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countByCriteria(final CriteriaQuery<Long> criteriaQuery) {
        try (EntityManager em = this.createEntityManager()) {
            return em.createQuery(criteriaQuery).getSingleResult();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(@Valid final T entry) {
        try (EntityManager em = this.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(entry);
            tx.commit();
            return entry;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(@Valid final T entry) throws EntryNotFound {
        try (EntityManager em = this.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            this.getById(entry.getId());
            em.merge(entry);
            tx.commit();
            return entry;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final Integer id) throws EntryNotFound {
        try (EntityManager em = this.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(this.getById(id));
            tx.commit();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityManager createEntityManager() {
        return sessionFactory.createEntityManager();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Session openSession() {
        return sessionFactory.openSession();
    }
}
