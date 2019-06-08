package qwickpot.dataservice.repositories.dummys;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.dummys.DummyCard;

public interface DummyCardRepository extends CrudRepository<DummyCard, UUID> {

}
