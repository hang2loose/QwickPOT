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
            "theme_name": RestHandler(self.__servie_address, "getThemeByName"),
            "theme_id": RestHandler(self.__servie_address, "getThemeById"),
            "card_id": RestHandler(self.__servie_address, "getCardById"),
        }
        self.__subscribed_events = ("new_user", "error", "question")
        self.__event_handler = {
            "new_user": self.__on_new_user,
            "question": self.__on_question,
            "error": self._emit_error_event("error")
        }

        self._users = {}

    def __get_theme_by_name(self, name: str):
        request = self.__connected_restpoints["theme_name"].call_endpoint({"ThemeName": name})
        if request.status_code is 200:
            return request.json()
        print("Request Error")

    def __get_theme_by_id(self, theme_id: str):
        request = self.__connected_restpoints["theme_id"].call_endpoint({"ThemeId": theme_id})
        if request.status_code is 200:
            return request.json()
        print("Request Error")

    def __get_card_by_id(self, card_id: str):
        request = self.__connected_restpoints["card_id"].call_endpoint({"CardId": card_id})
        if request.status_code is 200:
            return request.json()
        print("Request Error")

    def __create_new_user(self, id):
        theme_request = self.__get_theme_by_name("rootTheme")
        return {"id": id,
                "currentThemeId": theme_request["id"],
                "currentTheme": theme_request["name"],
                "ParentThemeId": theme_request["id"],
                "sub_themes": self.__convert_sub_nodes(theme_request["subThemes"]),
                "cards": self.__convert_sub_nodes(theme_request["cards"])
                }

    def __load_theme(self, id, theme_id):
        user = self._users[id]
        theme_request = self.__get_theme_by_id(theme_id)
        user["ParentThemeId"] = user["currentThemeId"]
        user["currentThemeId"] = theme_request["id"]
        user["currentTheme"] = theme_request["name"]
        user["sub_themes"] = self.__convert_sub_nodes(theme_request["subThemes"])
        user["cards"] = self.__convert_sub_nodes(theme_request["cards"])

    def __convert_sub_nodes(self, sub_nodes: dict):
        tmp_sub_nodes = {}
        for sub_node in sub_nodes:
            tmp_sub_nodes[sub_node["id"]] = sub_node["name"]
        return tmp_sub_nodes

    def __show_curr_theme(self, id):
        user = self._users[id]
        result = user["currentTheme"]
        return result

    def __show_sub_nodes(self, id, sub_node_type):
        result = ""
        user = self._users[id]
        tmp_sub_nodes = user[sub_node_type]
        for sub_node in tmp_sub_nodes.values():
            result += "- " + sub_node + "\n"
        return result

    def __get_sub_node_id(self, sub_node_name: str, sub_nodes: dict):
        for key, value in sub_nodes.items():
            if value.lower() == sub_node_name.lower():
                return key
        return None

    def __show_card(self, card):
        return "{}:\n{}".format(card["name"], card["description"])

    def __ask_for_action(self, id):
        result = "Was möchten Sie über \"" + self.__show_curr_theme(id) + "\" wissen?\n"
        result += self.__show_sub_nodes(id, "cards")
        result += self.__show_sub_nodes(id, "sub_themes")
        return result

    def __is_user_online(self, id):
        return self._users[id] is not None

    def __get_response(self, id, question: str):
        user = self._users[id]
        sub_theme_id = self.__get_sub_node_id(question, user["sub_themes"])
        if sub_theme_id is not None:
            self.__load_theme(id, sub_theme_id)
            return self._bot_event(self.__ask_for_action(id))
        card_id = self.__get_sub_node_id(question, user["cards"])
        if card_id is not None:
            card = self.__get_card_by_id(card_id)
            return self._bot_event(self.__show_card(card))
        return self._bot_event("Entschuldigung, ich habe Sie nicht verstanden.")

    # Events
    def __on_question(self, event: dict):
        load = self.get_load(event)
        username = load["username"]
        if self.__is_user_online(username):
            return self.__get_response(username, load["question"])
        return self._bot_event("Benutzer: \"" + username + "\" ist nicht angemeldet!")

    def __on_new_user(self, event: dict):
        load = self.get_load(event)
        self._users[load["username"]] = self.__create_new_user(load["username"])
        quest = self.__ask_for_action(load["username"])
        return self._bot_event("Hallo {}, ich bin Quickpot+-\n{}".format(load["username"], quest))

    def get_load(self, event):
        return event["load"]

    def get_bot_answer(self, event: dict):
        return self._check_event_type(self.__subscribed_events, self.__event_handler, event)


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
            "s": StatsMode("localhost")
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


# test dummy:
bot = Qwickpot("q")


def test_bot():
    msg = {
        "event_type": "new_user",
        "load": {"username": "chucky"}
    }
    print(bot.trigger_bot(msg))
    question = ""
    while "kill" != question:
        question = input("-> ")
        msg = {
            "event_type": "question",
            "load": {"username": "chucky", "question": question}
        }
        print(bot.trigger_bot(msg))


test_bot()
