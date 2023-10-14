package guldilin.repository.interfaces;

import guldilin.dto.FilterArgumentDTO;
import guldilin.dto.PaginationRequestDTO;
import guldilin.entity.Mappable;
import guldilin.exceptions.FieldIsNotFilterable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

public interface CrudRepository<T extends Mappable> {
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
     * Creates Entity Manager.
     *
     * @return EntityManager
     */
    EntityManager createEntityManager();

    CriteriaQuery<T> createFilterQuery(List<FilterArgumentDTO> filterArguments) throws FieldIsNotFilterable;

    CriteriaQuery<Long> createCounterQuery();

    Session openSession();
}
