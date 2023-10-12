package guldilin.repository.interfaces;

import guldilin.entity.City;

import javax.ejb.EJB;
import javax.ejb.Local;


@Local
public interface CityRepository extends CrudRepository<City> {}
