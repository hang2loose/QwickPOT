package qwickpot.dataservice.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Theme {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @ManyToOne
  private Theme parentTheme;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Theme> subThemes;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Card> cards;
}
