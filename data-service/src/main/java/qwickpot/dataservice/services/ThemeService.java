package qwickpot.dataservice.services;

import java.util.Optional;
import java.util.UUID;
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

  public ThemeDto getThemeFromRepo(String name) {
    Optional<Theme> optionalTheme = themeRepository.getThemeByName(name);
    return optionalTheme.map(ThemeDto::convertToDtoFromEntity).orElse(null);
  }

  public ThemeDto getThemeFromRepo(UUID uuid) {
    Optional<Theme> optionalTheme = themeRepository.findById(uuid);
    return optionalTheme.map(ThemeDto::convertToDtoFromEntity).orElse(null);
  }
}