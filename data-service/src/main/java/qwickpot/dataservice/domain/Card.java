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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  private final String name;
  private final String description;

}
