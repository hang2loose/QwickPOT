package qwickpot.dataservice.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.repositories.ThemeRepository;
import qwickpot.dataservice.util.CsvObject;

@Slf4j
@Service
public class ThemeService {

  public static final String COULD_NOT_FIND_MESSAGE = "Could not find: ";
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
  public Theme getThemeFromRepo(UUID uuid) {
    Optional<Theme> optionalTheme = themeRepository.findById(uuid);
    return optionalTheme
        .orElseThrow(() -> new IllegalArgumentException(COULD_NOT_FIND_MESSAGE + uuid));
  }


  public void importCsvObject(CsvObject csvObject) {
    csvObject.getLineiterator()
        .forEachRemaining(line -> themeRepository.save(convertFromCsv(line)));
  }

  private Theme convertFromCsv(List<String> csvLine) {
    String name = csvLine.get(1);
    UUID id = getUuid(csvLine.get(0));
    Theme parentTheme = getParentTheme(csvLine.get(2));
    Theme theme = id == null ? new Theme() : getThemeFromRepo(id);

    theme.setName(name);
    theme.setParentTheme(parentTheme);
    return theme;
  }

  private UUID getUuid(String idString) {
    return idString == null ? null : UUID.fromString(idString);
  }

  private Theme getParentTheme(String csvLine) {
    return themeRepository.getThemeByName(csvLine).orElse(null);
  }
}