package qwickpot.dataservice.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import qwickpot.dataservice.domain.Theme;

public interface ThemeRepository extends CrudRepository<Theme, Long> {

  Optional<Theme> getThemeByName(String name);
}
