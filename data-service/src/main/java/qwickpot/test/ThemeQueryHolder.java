package qwickpot.test;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.services.ThemeService;

@Service
@RequiredArgsConstructor
public class ThemeQueryHolder implements GraphQLQueryResolver {

  @Autowired
  private final ThemeService themeService;

  public Theme getTheme(Long id) {
    return themeService.getThemeFromRepo(id);
  }
}
