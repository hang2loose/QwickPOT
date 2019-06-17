package qwickpot.dataservice.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import qwickpot.dataservice.domain.Deparment;

public interface DepartmentRepository extends JpaRepository<Deparment, Long> {


  Optional<Deparment> getDeparmentByName(String name);

  boolean existsByName(String name);
}
