package qwickpot.dataservice.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import qwickpot.dataservice.domain.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

  Optional<Card> getCardByName(String name);
}
