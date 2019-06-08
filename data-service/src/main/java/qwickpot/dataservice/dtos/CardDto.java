package qwickpot.dataservice.dtos;

import lombok.Getter;
import lombok.Setter;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.domain.Theme;

@Setter
@Getter
public class CardDto {

  private String name;
  private String description;

  private Theme theme;

  public CardDto(Card card) {
    this.name = card.getName();
    this.description = card.getDescription();
  }

}

// TODO build or ModelMapper