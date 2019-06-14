package qwickpot.dataservice.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.Deparment;

public interface DepartmentRepository extends CrudRepository<Deparment, Long> {


  Optional<Deparment> getDeparmentByName(String name);

  boolean existsByName(String name);
}
