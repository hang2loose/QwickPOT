package qwickpot.dataservice.dtos;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class StatDto {

  private LocalDateTime creationDate;
  Map<String, Integer> statMap;

  public StatDto(LocalDateTime creationDate,
      Map<String, Integer> statMap) {
    this.creationDate = creationDate;
    this.statMap = statMap;
    log.info(creationDate.toString());
  }
}
