package qwickpot.dataservice.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

@Entity
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDeparmentId() {
    return deparmentId;
  }

  public void setDeparmentId(Long deparmentId) {
    this.deparmentId = deparmentId;
  }

  public Long getThemeId() {
    return themeId;
  }

  public void setThemeId(Long themeId) {
    this.themeId = themeId;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }
}
