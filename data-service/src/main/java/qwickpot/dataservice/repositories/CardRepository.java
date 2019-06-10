package qwickpot.dataservice.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.Card;

public interface CardRepository extends CrudRepository<Card, Long> {

  Optional<Card> getCardByName(String name);
}
