package qwickpot.dataservice.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.dtos.ThemeDto;
import qwickpot.dataservice.repositories.ThemeRepository;

@Service
public class ThemeService {

  private ThemeRepository themeRepository;

  public ThemeService(ThemeRepository themeRepository) {
    this.themeRepository = themeRepository;
  }

  public ThemeDto getThemeFromRepoByName(String name) {
    Optional<Theme> optionalTheme = themeRepository.getThemeByName(name);
    return optionalTheme.map(ThemeDto::convertToDtoFromEntity).orElse(null);
  }
}
