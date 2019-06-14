package qwickpot.dataservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Deparment;
import qwickpot.dataservice.domain.Theme;

@Slf4j
@Service
public class StatService {

  private ThemeService themeService;
  private DeparmentService deparmentService;

  public StatService(ThemeService themeService,
      DeparmentService deparmentService) {
    this.themeService = themeService;
    this.deparmentService = deparmentService;
  }

  public void addStat(Long departmentId, Long themeId) {
    log.info("adding stat....");
    Deparment deparment = deparmentService.getDepartmentFromRepo(departmentId);
    Theme theme = themeService.getThemeFromRepo(themeId);
    deparment.incrementThemeStat(theme);
    deparmentService.updateDepartment(deparment);
  }
}
