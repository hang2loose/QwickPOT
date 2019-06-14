package qwickpot.dataservice.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

  // TODO Mapping ManyToMany
  /*
 private Map<Theme, Integer> themesCalled;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "themes_called_by_departments",
      joinColumns = @JoinColumn(name = "department_id"))
  @MapKeyJoinColumn(name = "theme_id")
  @Column(name = "count")
  private Map<Long, Integer> themesCalled;

  public void incrementThemeStat(Theme theme) {
    themesCalled.merge(theme.getId(), 1, Integer::sum);
  }*/
}
