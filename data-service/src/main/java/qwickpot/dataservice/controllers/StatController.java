package qwickpot.dataservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import qwickpot.dataservice.dtos.StatDto;
import qwickpot.dataservice.services.StatService;

@RestController
@RequestMapping("/StatsController")
public class StatController {

  private StatService statService;

  public StatController(StatService statService) {
    this.statService = statService;
  }

  @GetMapping("/GetStatsFromDepartment")
  public StatDto getStatsFromDepartment(@RequestParam(name = "departmenId") Long departmentId) {
    return statService.getStatisticsFromDepartmentId(departmentId);
  }

}
