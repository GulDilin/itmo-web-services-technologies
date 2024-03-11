package guldilin.repository.interfaces;

import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.entity.AbstractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

/**
 * Generic Interface for Entity Repository.
 *
 * @param <T> Entity class
 */
public interface CrudRepository<T extends AbstractEntity> {
    /**
     * Find all Elements by CriteriaQuery.
     *
     * @param criteriaQuery CriteriaQuery
     * @param pagination information about pagination properties
     * @return Result list
     */
    List<T> findByCriteria(CriteriaQuery<T> criteriaQuery, PaginationRequestDTO pagination);

    /**
     * Count elements in database by criteria.
     *
     * @param criteriaQuery CriteriaQuery
     * @return Number of elements
     */
    Long countByCriteria(CriteriaQuery<Long> criteriaQuery);

    /**
     * Find item by id.
     *
     * @param id Item id
     * @return Optional Entity
     */
    Optional<T> findById(Integer id);

    /**
     * Get item by id.
     *
     * @param id Item id
     * @return Found item
     * @throws Exception If item with specified id does not exist
     */
    T getById(Integer id) throws Exception;

    /**
     * Creates new item.
     *
     * @param entry Item to create
     * @return Created item
     */
    T create(@Valid T entry);

    /**
     * Update item.
     *
     * @param entry Item with updated fields.
     * @return Updated item
     * @throws Exception If item with specified id does not exist
     */
    T update(@Valid T entry) throws Exception;

    /**
     * Delete item by id.
     *
     * @param id Item id
     * @throws Exception If item with specified id does not exist
     */
    void deleteById(Integer id) throws Exception;

    /**
     * Creates Entity Manager.
     *
     * @return EntityManager
     */
    EntityManager createEntityManager();

    /**
     * Create CriteriaQuery by filter arguments.
     *
     * @param filterArguments list of filter fields arguments
     * @return Generated CriteriaQuery
     * @throws Exception if some filter fields are incorrect
     */
    CriteriaQuery<T> createFilterQuery(List<FilterArgumentDTO> filterArguments) throws Exception;

    /**
     * Create CriteriaQuery for count elements.
     *
     * @param filterArguments List of filters
     * @return CriteriaQuery
     * @throws Exception if some filter fields are incorrect
     */
    CriteriaQuery<Long> createCounterQuery(List<FilterArgumentDTO> filterArguments) throws Exception;

    /**
     * Opens Session.
     *
     * @return Session
     */
    Session openSession();
}
