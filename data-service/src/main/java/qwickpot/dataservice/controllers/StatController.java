package qwickpot.dataservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qwickpot.dataservice.services.StatService;

public class StatController {

  private StatService statService;

  public StatController(StatService statService) {
    this.statService = statService;
  }

  @PostMapping("/incrementThemeCount")
  public void incrementThemeCount(
      @RequestParam(name = "Department Id") Long departmentId,
      @RequestParam(name = "Theme Id") Long themeId) {
    statService.addStatWithTheme(departmentId, themeId);
  }

}
