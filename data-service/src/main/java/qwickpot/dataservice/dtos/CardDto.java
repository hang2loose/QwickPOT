package qwickpot.dataservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.dtos.builder.CardDtoBuilder;
import qwickpot.dataservice.dtos.builder.ThemeDtoBuilder;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class CardDto implements Serializable {

  private Long id;
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
}