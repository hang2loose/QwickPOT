package qwickpot.dataservice.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.Theme;

public interface ThemeRepository extends CrudRepository<Theme, UUID> {

  Optional<Theme> getThemeByName(String name);
}
