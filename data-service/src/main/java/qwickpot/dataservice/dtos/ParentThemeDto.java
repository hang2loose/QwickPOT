package qwickpot.dataservice.dtos;

import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.dtos.builder.ThemeDtoBuilder;

public class ParentThemeDto {

  private Long id;
  private String name;

  private ParentThemeDto() {
  }

  public static ThemeDto convertEntityToDto(Theme theme) {
    if (theme == null) {
      return new ThemeDtoBuilder()
          .withId(null)
          .build();
    }
    return new ThemeDtoBuilder()
        .withId(theme.getId())
        .withName(theme.getName())
        .build();
  }
}
