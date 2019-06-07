package qwickpot.dataservice.repositories;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.DummyCard;

public interface DummyCardRepository extends CrudRepository<DummyCard, UUID> {

}
