package qwickpot.dataservice.graphqlholders;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qwickpot.dataservice.domain.Card;
import qwickpot.dataservice.services.CardService;

@Service
@RequiredArgsConstructor
public class CardQueryHolder implements GraphQLQueryResolver {

  @Autowired
  private CardService cardService;

  public Card getCard(Integer id) {
    return cardService.getCardFromRepo(Long.valueOf(id));
  }
}
