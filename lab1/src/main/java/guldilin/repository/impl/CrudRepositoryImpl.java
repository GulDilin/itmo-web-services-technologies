package guldilin.repository.impl;

import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.entity.AbstractEntity;
import guldilin.exceptions.FieldIsNotFilterable;
import guldilin.repository.interfaces.CrudRepository;
import guldilin.utils.FilterObjectConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CrudRepositoryImpl<T extends AbstractEntity> implements CrudRepository<T> {

    private final SessionFactory sessionFactory;
    private final Class<T> tClass;

    /**
     * Constructor for CrudRepositoryImpl.
     *
     * @param tClass         Entity class
     * @param sessionFactory SessionFactory instance
     */
    public CrudRepositoryImpl(final Class<T> tClass, final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.tClass = tClass;
    }

    /**
     * Get predicate for Filter argument to use in where method.
     *
     * @param cb             CriteriaBuilder
     * @param filterArgument argument filter
     * @param root           Criteria root object
     * @return Predicate
     * @throws FieldIsNotFilterable if incorrect field placed in argument
     */
    public Predicate getFilterPredicate(
            final CriteriaBuilder cb, final FilterArgumentDTO filterArgument, final Root<?> root)
            throws FieldIsNotFilterable {
        String field = filterArgument.getField();
        FilterObjectConverter.checkIfFieldFilterable(tClass, field);
        return cb.equal(root.get(field), filterArgument.getValue());
    }

    /**
     * Create CriteriaQuery by filter arguments.
     *
     * @param filterArguments list of filter fields arguments
     * @return Generated CriteriaQuery
     * @throws FieldIsNotFilterable if some filter fields are incorrect
     */
    public CriteriaQuery<T> createFilterQuery(final List<FilterArgumentDTO> filterArguments)
            throws FieldIsNotFilterable {
        try (EntityManager em = this.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = cb.createQuery(tClass);
            Root<T> root = criteriaQuery.from(tClass);
            criteriaQuery.select(root);
            List<Predicate> predicates = new ArrayList<>();
            if (filterArguments != null) {
                for (FilterArgumentDTO filterArgument : filterArguments) {
                    predicates.add(this.getFilterPredicate(cb, filterArgument, root));
                }
            }
            if (!predicates.isEmpty()) criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
            return criteriaQuery;
        }
    }

    /**
     * Create CriteriaQuery for count elements.
     *
     * @return CriteriaQuery
     */
    public CriteriaQuery<Long> createCounterQuery() {
        try (EntityManager em = this.createEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
            criteriaQuery.select(cb.count(criteriaQuery.from(tClass)));
            return criteriaQuery;
        }
    }

    /**
     * Find all Elements by CriteriaQuery.
     *
     * @param criteriaQuery CriteriaQuery
     * @param pagination    information about pagination properties
     * @return Result list
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
     * Find item by id.
     *
     * @param id Item id
     * @return Optional Entity
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
     * Count elements in database by criteria.
     *
     * @param criteriaQuery CriteriaQuery
     * @return Number of elements
     */
    @Override
    public Long countByCriteria(final CriteriaQuery<Long> criteriaQuery) {
        try (EntityManager em = this.createEntityManager()) {
            return em.createQuery(criteriaQuery).getSingleResult();
        }
    }

    /**
     * Creates Entity Manager.
     *
     * @return EntityManager
     */
    @Override
    public EntityManager createEntityManager() {
        return sessionFactory.createEntityManager();
    }

    /**
     * Opens Session.
     *
     * @return Session
     */
    @Override
    public Session openSession() {
        return sessionFactory.openSession();
    }
}
