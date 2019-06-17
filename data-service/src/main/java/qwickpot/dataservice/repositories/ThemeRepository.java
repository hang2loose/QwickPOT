package qwickpot.dataservice.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import qwickpot.dataservice.domain.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

  Optional<Theme> getThemeByName(String name);
}
