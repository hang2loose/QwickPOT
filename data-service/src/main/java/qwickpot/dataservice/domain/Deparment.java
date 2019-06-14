package qwickpot.dataservice.domain;

import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Deparment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public Deparment(String name) {
    this.name = name;
  }

  @ElementCollection
  @CollectionTable(name = "themes_called_by_departments",
      joinColumns = @JoinColumn(name = "department_id"))
  @MapKeyJoinColumn(name = "theme_id")
  @Column(name = "count")
  private Map<Theme, Integer> themesCalled;

  public void incrementThemeStat(Theme theme) {
    themesCalled.merge(theme, 1, Integer::sum);
  }
}
