import requests


class RestHandler:

  def __init__(self, service_address: str, endpoint: str):
    self.__service_address = service_address
    self.__endpoint = endpoint

  def call_endpoint(self, params: dict = None):
    return requests.get(self.__service_address + "/{}".format(self.__endpoint),
                        params)


class DummyMode:
  def __init__(self, service_address: str):
    self.servie_address = "http://{}:9090".format(service_address)

    self.connected_restpoints = {
      "chucky": RestHandler(self.servie_address, "chucky")
    }
    self.__tmp_dic = {
      "hello": "Hello World",
      "test": "123",
      "chucky": self.__get_chucky
    }

  def __get_chucky(self):
    return self.connected_restpoints["chucky"].call_endpoint().text

  def get_bot_answer(self, msg):
    if msg in self.__tmp_dic:
      if callable(self.__tmp_dic[msg]):
        return self.__tmp_dic[msg].__call__()
      else:
        return self.__tmp_dic[msg]


class QuestionsMode:

  def __init__(self, service_address: str):
    self.servie_address = "http://{}:9090".format(service_address)
    self.connected_restpoints = {
      "theme_id": RestHandler(self.servie_address, "getThemeByName"),
      "theme_name": RestHandler(self.servie_address, "getThemeByID"),
    }

  def get_bot_answer(self, msg):
    return "Hello from question Mode"


class StatsMode:

  def __init__(self, service_address: str):
    self.servie_address = "http://{}:9090".format(service_address)
    self.connected_restpoints = {}

  def get_bot_answer(self, msg):
    return "Hello from Stats Mode"


class Qwickpot:

  def __init__(self, start_mode):
    self.__modes = {
      "d": DummyMode("localhost"),
      "q": QuestionsMode("localhost"),
      "s": "stats"
    }
    self.__active_mode = "d"
    if start_mode in self.__modes:
      self.__change_mode(start_mode)

  def __change_mode(self, new_mode):
    if new_mode in self.__modes:
      self.__active_mode = new_mode

  def __emit_mode_changed(self, mode: str):
    return self.__create_event("mode_changend", self.__active_mode)

  def __create_event(self, event_type: str, load: str):
    return {
      "event": event_type,
      "load": load
    }

  def __trigger_mode(self, event):
    mode_message = self.__modes[self.__active_mode].get_bot_answer(
        event["load"])
    return self.__create_event("bot_answer", mode_message)

  def trigger_bot(self, event: dict):
    if event["event_type"] == "change_mode":
      self.__change_mode(event["load"])
      return self.__emit_mode_changed(self.__active_mode)
    return self.__trigger_mode(event)