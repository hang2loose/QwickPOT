package qwickpot.dataservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import qwickpot.dataservice.domain.StatPost;

public interface StatPostRepository extends JpaRepository<StatPost, Long> {

}
