package qwickpot.dataservice.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qwickpot.dataservice.domain.Deparment;
import qwickpot.dataservice.services.DeparmentService;

@RestController
public class DepartmentController {

  private DeparmentService deparmentService;

  public DepartmentController(DeparmentService deparmentService) {
    this.deparmentService = deparmentService;
  }

  @PostMapping("/addDepartment")
  public boolean addDepartment(@RequestParam(name = "Department Name") String name) {
    return deparmentService.addNewDepartment(name);
  }

  @CrossOrigin
  @GetMapping("/getAllDepartmentNames")
  public List<Deparment> getAllDepartmentsNames() {
    return deparmentService.getListOfDepartmentsWithIds();
  }
}
