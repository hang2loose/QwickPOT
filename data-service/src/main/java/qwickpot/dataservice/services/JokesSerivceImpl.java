package qwickpot.dataservice.services;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;
import org.springframework.stereotype.Service;

@Service
public class JokesSerivceImpl implements JokesService {

  private final ChuckNorrisQuotes chuckNorrisQuotes;

  public JokesSerivceImpl(ChuckNorrisQuotes chuckNorrisQuotes) {
    this.chuckNorrisQuotes = chuckNorrisQuotes;
  }

  @Override
  public String getJoke() {
    return chuckNorrisQuotes.getRandomQuote();
  }

}