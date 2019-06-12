import requests


class RestHandler:

    def __init__(self, service_address: str, endpoint: str):
        self.__service_address = service_address
        self.__endpoint = endpoint

    def call_endpoint(self, params: dict = None):
        return requests.get(
            self.__service_address + "/{}".format(self.__endpoint), params)


class ModeUtil:

    def event_builder(self, type: str, load: str):
        return {
            "event_type": type,
            "load": load
        }

    def _bot_event(self, load):
        return self.event_builder("bot_message", load)

    def _emit_error_event(self, error_message: str):
        return self.event_builder("error", error_message)

    def _handle_event(self, event_handler: dict, event: dict):
        return event_handler[event["event_type"]].__call__(event)

    def _check_event_type(self, subscribed_events: tuple, event_handler: dict, event: dict):
        if event["event_type"] in subscribed_events:
            return self._handle_event(event_handler, event)
        return self._emit_error_event("event not subscribed")


class DummyMode(ModeUtil):
    def __init__(self, service_address: str):
        self.__servie_address = "http://{}:9090".format(service_address)
        self.__connected_restpoints = {
            "chucky": RestHandler(self.__servie_address, "chucky")
        }
        self.__subscribed_events = ("error", "new_user")
        self.__event_handler = {
            "error": self._emit_error_event("error"),
            "new_user": self.__on_chucky
        }

    def __on_chucky(self, event: dict):
        joke = self.__connected_restpoints["chucky"].call_endpoint()
        if joke.status_code is 200:
            return self._bot_event(joke.text)
        return self._emit_error_event("bad Request")

    def get_bot_answer(self, event: dict):
        return self._check_event_type(self.__subscribed_events, self.__event_handler, event)


class QuestionsMode(ModeUtil):

    def __init__(self, service_address: str):
        self.__servie_address = "http://{}:9090".format(service_address)
        self.__connected_restpoints = {
            "theme_id": RestHandler(self.__servie_address, "getThemeByName"),
            "theme_name": RestHandler(self.__servie_address, "getThemeByID"),
        }
        self.__subscribed_events = ("new_user", "error")
        self.__event_handler = {
            "new_user": self.__on_new_user,
            "error": self._emit_error_event("error")
        }

    def __on_new_user(self, event: dict):
        return self._bot_event("Hi, {} wie kann ich behilflich sein ??".format(event["load"]))

    def __check_event_type(self, event: dict):
        if event["event_type"] in self.__subscribed_events:
            return self._handle_event(self.__event_handler, event)
        return self._emit_error_event("event not subscribed")

    def get_bot_answer(self, event: dict):
        return self.__check_event_type(event)


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
            return self.__create_event("mode_changend", self.__active_mode)
        return self.__create_event("error", "mode not in list")

    def __create_event(self, event_type: str, load: str):
        return {
            "event": event_type,
            "load": load
        }

    def __trigger_mode(self, event: dict):
        mode_message = self.__modes[self.__active_mode].get_bot_answer(event)
        return self.__create_event("bot_answer", mode_message)

    def trigger_bot(self, event: dict):
        if event["event_type"] == "change_mode":
            return self.__change_mode(event["load"])
        return self.__trigger_mode(event)

