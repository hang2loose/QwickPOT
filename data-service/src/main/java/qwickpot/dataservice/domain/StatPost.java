package qwickpot.dataservice.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
public class StatPost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long deparmentId;

  private Long themeId;

  @CreationTimestamp
  private LocalDateTime creationDate;

  public StatPost() {
  }

  public StatPost(Deparment deparment, Card card) {
    this.deparmentId = deparment.getId();
    this.themeId = card.getTheme().getId();
  }
}
