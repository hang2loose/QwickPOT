package qwickpot.dataservice.dtos;

import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.dtos.builder.CardDtoBuilder;
import qwickpot.dataservice.dtos.builder.ThemeDtoBuilder;

@Setter
@Getter
public class CardDto implements Serializable, CsvReadable {

  private UUID id;
  private String name;
  private String description;

  private ThemeDto theme;

  public static CardDto convertToDtoFromEntity(Card card) {
    return new CardDtoBuilder()
        .withId(card.getId())
        .withName(card.getName())
        .withDescription(card.getDescription())
        .withTheme(new ThemeDtoBuilder()
            .withId(card.getTheme().getId())
            .build())
        .build();
  }

  @Override
  public CardDto convertFromCsvLine(String[] stringArray) {
    return stringArray.length <= 4 ? null : new CardDtoBuilder()
        .withId(UUID.fromString(stringArray[0]))
        .withName(stringArray[1])
        .withDescription(stringArray[2])
        .build();
  }
}

// TODO build or ModelMapper