package qwickpot.dataservice.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Card {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID ID;
  private String name;
  private String description;

}
