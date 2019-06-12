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


class DummyMode(ModeUtil):
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

    def get_bot_answer(self, event: dict):
        load = event["load"]
        if load in self.__tmp_dic:
            if callable(self.__tmp_dic[load]):
                return self.__bot_event(self.__tmp_dic[load].__call__())
            else:
                return self.__tmp_dic[load]


class QuestionsMode(ModeUtil):

    def __init__(self, service_address: str):
        self.__servie_address = "http://{}:9090".format(service_address)

        self.__connected_restpoints = {
            "theme_id": RestHandler(self.__servie_address, "getThemeByName"),
            "theme_name": RestHandler(self.__servie_address, "getThemeByID"),
        }

        self.__subscribed_events = ("new_user", "theme")

        self.__event_handler = {
            "new_user": self.__on_new_user
        }

    def __on_new_user(self, event: dict):
        return super()._bot_event("Hi, {} wie kann ich behilflich sein ??".format(event["load"]))

    def __handle_event(self, event: dict):
        return self.__event_handler[event["event_type"]].__call__(event)

    def __check_event_type(self, event: dict):
        if event["event_type"] in self.__subscribed_events:
            return self.__handle_event(event)
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


bot = Qwickpot("q")


def testBot():
    msg = {
        "event_type": "new_user",
        "load": "babo"
    }
    return bot.trigger_bot(msg)


print(testBot())

# while input("again?: ") is not "n":
#     msg = {
#         "event_type": input("event: "),
#         "load": input("load: ")
#     }
#     print(bot.trigger_bot(msg))
