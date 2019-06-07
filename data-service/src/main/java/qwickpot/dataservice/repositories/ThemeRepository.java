package qwickpot.dataservice.repositories;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.Theme;

public interface ThemeRepository extends CrudRepository<Theme, UUID> {

}
