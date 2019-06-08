package qwickpot.dataservice.dtos.builder;

import java.util.List;
import java.util.UUID;
import qwickpot.dataservice.dtos.CardDto;
import qwickpot.dataservice.dtos.ThemeDto;

public class ThemeDtoBuilder {

  private ThemeDto themeDtoUnderConstruktion;

  public ThemeDtoBuilder() {
    this.themeDtoUnderConstruktion = new ThemeDto();
  }

  public ThemeDtoBuilder withId(UUID id) {
    themeDtoUnderConstruktion.setId(id);
    return this;
  }

  public ThemeDtoBuilder withName(String name) {
    themeDtoUnderConstruktion.setName(name);
    return this;
  }

  public ThemeDtoBuilder withParentTheme(ThemeDto theme) {
    themeDtoUnderConstruktion.setParentTheme(theme);
    return this;
  }

  public ThemeDtoBuilder withSubThemes(List<ThemeDto> subThemes) {
    themeDtoUnderConstruktion.setSubThemes(subThemes);
    return this;
  }

  public ThemeDtoBuilder withCards(List<CardDto> cards) {
    themeDtoUnderConstruktion.setCards(cards);
    return this;
  }

  public ThemeDto build() {
    return themeDtoUnderConstruktion;
  }

}
