package qwickpot.dataservice.repositories;

import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.Deparment;

public interface DepartmentRepository extends CrudRepository<Deparment, Long> {

}
