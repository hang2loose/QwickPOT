package qwickpot.dataservice.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.repositories.CardRepository;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private final CardRepository cardRepository;

  public DevBootstrap(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (cardRepository.count() == 0L) {
      initCards();
    }
  }

  private void initCards() {

    cardRepository.save(new Card("dummy", "1st dummy Card"));
    cardRepository.save(new Card("tes", "1st Test Card"));
    cardRepository.save(new Card("dummy", "2st dummy Card"));
    cardRepository.save(new Card("bubu", "ProjectsLead"));
    cardRepository.save(new Card("Jules", "Atanua4Life"));
  }
}

