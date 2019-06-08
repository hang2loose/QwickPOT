package qwickpot.dataservice.dtos.builder;

import java.util.UUID;
import qwickpot.dataservice.dtos.CardDto;
import qwickpot.dataservice.dtos.ThemeDto;

public class CardDtoBuilder {

  private CardDto dtoUnderConstruktion;

  public CardDtoBuilder() {
    dtoUnderConstruktion = new CardDto();
  }

  public CardDtoBuilder withId(UUID id) {
    dtoUnderConstruktion.setId(id);
    return this;
  }

  public CardDtoBuilder withName(String name) {
    dtoUnderConstruktion.setName(name);
    return this;
  }

  public CardDtoBuilder withDescription(String description) {
    dtoUnderConstruktion.setDescription(description);
    return this;
  }

  public CardDtoBuilder withTheme(ThemeDto theme) {
    dtoUnderConstruktion.setTheme(theme);
    return this;
  }

  public CardDto build() {
    return dtoUnderConstruktion;
  }
}
