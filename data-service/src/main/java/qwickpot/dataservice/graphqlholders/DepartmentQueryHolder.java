package qwickpot.dataservice.graphqlholders;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Deparment;
import qwickpot.dataservice.services.DeparmentService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentQueryHolder implements GraphQLQueryResolver {

  @Autowired
  DeparmentService deparmentService;

  public List<Deparment> getDepartments() {
    return deparmentService.getListOfDepartments();
  }
}
