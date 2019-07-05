package qwickpot.dataservice.services;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.domain.Deparment;
import qwickpot.dataservice.domain.StatPost;
import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.dtos.StatDto;
import qwickpot.dataservice.repositories.StatPostRepository;

@Slf4j
@Service
public class StatService {

  private ThemeService themeService;
  private CardService cardService;
  private DeparmentService deparmentService;
  private StatPostRepository statPostRepository;

  public StatService(ThemeService themeService,
      DeparmentService deparmentService, CardService cardService,
      StatPostRepository statPostRepository) {
    this.themeService = themeService;
    this.deparmentService = deparmentService;
    this.cardService = cardService;
    this.statPostRepository = statPostRepository;
  }

  public void addStatWithFromCard(Long departmentId, Long cardId) {
    Long themeId = cardService.getCardFromRepo(cardId).getTheme().getId();
    addStatWithTheme(departmentId, themeId);
  }

  public void safeCalltoStatistic(Long departmentId, Long cardId) {
    Card card = cardService.getCardFromRepo(cardId);
    Deparment deparment = deparmentService.getDepartmentFromRepo(departmentId);
    statPostRepository.save(new StatPost(deparment, card));
  }


  public StatDto getStatisticsFromDepartmentId(Long id) {
    Deparment deparment = deparmentService.getDepartmentFromRepo(id);
    HashMap<String, Integer> convertedStatsMap = convertStatsMap(deparment.getThemesCalled());
    return new StatDto(deparment.getCreationDate(), convertedStatsMap);
  }

  private HashMap<String, Integer> convertStatsMap(Map<Long, Integer> statsMapFromDb) {
    HashMap<String, Integer> tmp = new HashMap<>();
    statsMapFromDb.entrySet().iterator()
        .forEachRemaining(a -> tmp.put(themeService.getThemeNameFromId(a.getKey()), a.getValue()));
    return tmp;
  }

  private void addStatWithTheme(Long departmentId, Long themeId) {
    log.info("adding stat....");
    Deparment deparment = deparmentService.getDepartmentFromRepo(departmentId);
    Theme theme = themeService.getThemeFromRepo(themeId);
    deparment.incrementThemeStat(theme);
    deparmentService.updateDepartment(deparment);
  }

}
