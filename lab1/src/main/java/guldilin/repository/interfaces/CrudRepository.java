package guldilin.repository.interfaces;

import guldilin.entity.Mappable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;

public interface CrudRepository<T extends Mappable> {
    List<T> findByCriteria(CriteriaQuery<T> criteriaQuery);

    Long countByCriteria(CriteriaQuery<Long> criteriaQuery);

    Optional<T> findById(Integer id);

    EntityManager createEntityManager();

    Session openSession();
}
