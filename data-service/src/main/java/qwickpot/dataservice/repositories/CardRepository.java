package qwickpot.dataservice.repositories;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.Card;

public interface CardRepository extends CrudRepository<Card, UUID> {

}