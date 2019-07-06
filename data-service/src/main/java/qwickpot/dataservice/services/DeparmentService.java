package qwickpot.dataservice.services;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Deparment;
import qwickpot.dataservice.exceptions.DepartmentNotFoundException;
import qwickpot.dataservice.repositories.DepartmentRepository;

@Slf4j
@Service
public class DeparmentService {

  private static final String COULD_NOT_FIND_MESSAGE = "Could not find: ";

  private DepartmentRepository departmentRepository;

  public DeparmentService(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  public Deparment getDepartmentFromRepo(Long id) {
    Optional<Deparment> card = departmentRepository.findById(id);
    return card
        .orElseThrow(
            () -> new DepartmentNotFoundException(
                COULD_NOT_FIND_MESSAGE + "department with id : " + id.toString()));
  }

  public boolean addNewDepartment(String name) {
    if (departmentRepository.existsByName(name)) {
      return false;
    }
    departmentRepository.save(new Deparment(name));
    return true;
  }

  public void updateDepartment(Deparment deparment) {
    departmentRepository.save(deparment);
  }

  public List<Deparment> getListOfDepartments() {
    return departmentRepository.findAll();
  }
}
