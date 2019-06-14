package qwickpot.dataservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Deparment;
import qwickpot.dataservice.domain.Theme;

@Slf4j
@Service
public class StatService {

  private ThemeService themeService;
  private CardService cardService;
  private DeparmentService deparmentService;

  public StatService(ThemeService themeService,
      DeparmentService deparmentService, CardService cardService) {
    this.themeService = themeService;
    this.deparmentService = deparmentService;
    this.cardService = cardService;
  }

  public void addStatWithFromCard(Long departmentId, Long cardId) {
    Long themeId = cardService.getCardFromRepo(cardId).getTheme().getId();
    addStatWithTheme(departmentId, themeId);
  }

  public void addStatWithTheme(Long departmentId, Long themeId) {
    log.info("adding stat....");
    Deparment deparment = deparmentService.getDepartmentFromRepo(departmentId);
    Theme theme = themeService.getThemeFromRepo(themeId);
    deparment.incrementThemeStat(theme);
    deparmentService.updateDepartment(deparment);
  }
}
