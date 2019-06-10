package qwickpot.dataservice.domain;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Theme {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private String name;


  @ManyToOne
  private Theme parentTheme;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parentTheme")
  private List<Theme> subThemes;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Card> cards;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Card> getCards() {
    return cards;
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  public Theme getParentTheme() {
    return parentTheme;
  }

  public void setParentTheme(Theme parentTheme) {
    this.parentTheme = parentTheme;
  }

  public List<Theme> getSubThemes() {
    return subThemes;
  }

  public void setSubThemes(List<Theme> subThemes) {
    this.subThemes = subThemes;
  }
}
