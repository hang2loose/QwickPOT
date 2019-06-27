package qwickpot.dataservice.controllers;

import qwickpot.dataservice.services.StatService;

public class StatController {

  private StatService statService;

  public StatController(StatService statService) {
    this.statService = statService;
  }

}
