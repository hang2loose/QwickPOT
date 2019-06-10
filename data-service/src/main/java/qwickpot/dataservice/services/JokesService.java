package qwickpot.dataservice.services;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;
import org.springframework.stereotype.Service;

@Service
public class JokesService {

  private final ChuckNorrisQuotes chuckNorrisQuotes;

  public JokesService(ChuckNorrisQuotes chuckNorrisQuotes) {
    this.chuckNorrisQuotes = chuckNorrisQuotes;
  }

  public String getJoke() {
    return chuckNorrisQuotes.getRandomQuote();
  }

}