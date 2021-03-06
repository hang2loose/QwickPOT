package qwickpot.dataservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import qwickpot.dataservice.domain.Theme;
import qwickpot.dataservice.dtos.builder.CardDtoBuilder;
import qwickpot.dataservice.dtos.builder.ThemeDtoBuilder;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ThemeDto implements Serializable {

  private Long id;
  private String name;

  private ThemeDto parentTheme;

  private List<ThemeDto> subThemes;
  private List<CardDto> cards;

  public static ThemeDto convertEntityToDto(Theme theme) {
    return new ThemeDtoBuilder()
        .withId(theme.getId())
        .withName(theme.getName())
        .withParentTheme(ParentThemeDto.convertEntityToDto(theme.getParentTheme()))
        .withSubThemes(theme.getSubThemes().stream()
            .map(subTheme -> new ThemeDtoBuilder()
                .withId(subTheme.getId())
                .withName(subTheme.getName())
                .build())
            .collect(Collectors.toList()))
        .withCards(theme.getCards().stream()
            .map(card -> new CardDtoBuilder()
                .withId(card.getId())
                .withName(card.getName())
                .build())
            .collect(Collectors.toList()))
        .build();
  }
}
