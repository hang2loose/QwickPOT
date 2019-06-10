package qwickpot.dataservice.services;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.repositories.ThemeRepository;
import qwickpot.dataservice.util.CsvObject;

@Slf4j
@Service
public class ThemeService {

  private static final String COULD_NOT_FIND_MESSAGE = "Could not find: ";
  private ThemeRepository themeRepository;

  public ThemeService(ThemeRepository themeRepository) {
    this.themeRepository = themeRepository;
  }

  // TODO Error Handling Logging
  public Theme getThemeFromRepo(String name) {
    Optional<Theme> optionalTheme = themeRepository.getThemeByName(name);
    return optionalTheme
        .orElseThrow(() -> new IllegalArgumentException(COULD_NOT_FIND_MESSAGE + name));
  }

  // TODO Error Handling Logging
  public Theme getThemeFromRepo(Long id) {
    Optional<Theme> optionalTheme = themeRepository.findById(id);
    return optionalTheme
        .orElseThrow(() -> new IllegalArgumentException(COULD_NOT_FIND_MESSAGE + id));
  }


  public void importCsvObject(CsvObject csvObject) {
    csvObject.getLineIterator()
        .forEachRemaining(line -> themeRepository.save(convertFromCsv(line)));
  }

  private Theme convertFromCsv(List<String> csvLine) {
    Long id = Long.getLong(csvLine.get(0));
    Theme theme = id == null ? new Theme() : getThemeFromRepo(id);

    theme.setName(csvLine.get(1));
    theme.setParentTheme(getParentTheme(csvLine.get(2)));
    return theme;
  }

  private Theme getParentTheme(String csvLine) {
    return themeRepository.getThemeByName(csvLine).orElse(null);
  }
}