package qwickpot.dataservice.services;

import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Deparment;
import qwickpot.dataservice.repositories.DepartmentRepository;

@Service
public class DeparmentService {

  private DepartmentRepository departmentRepository;

  public DeparmentService(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  public boolean addNewDepartment(String name) {
    if (departmentRepository.existsByName(name)) {
      return false;
    }
    departmentRepository.save(new Deparment(name));
    return true;
  }
}
