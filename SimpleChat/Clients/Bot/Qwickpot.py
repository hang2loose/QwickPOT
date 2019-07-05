import requests


class RestHandler:

    def __init__(self, service_address: str, endpoint: str):
        self.__service_address = service_address
        self.__endpoint = endpoint

    def call_endpoint(self, params: dict = None):
        return requests.get(
            self.__service_address + "/{}".format(self.__endpoint), params)


class ModeUtil:

    def event_builder(self, type: str, id, load: str):
        return {
            "ID": id,
            "event_type": type,
            "load": load
        }

    def _bot_event(self, id, msg):
        load = {"username": "🤖 QwickPOT+-", "question": msg}
        return self.event_builder("bot_message", id, load)

    def _emit_error_event(self, id, error_message: str):
        return self.event_builder("error", id, error_message)

    def _handle_event(self, event_handler: dict, event: dict):
        return event_handler[event["event_type"]].__call__(event)

    def _check_event_type(self, subscribed_events: tuple, event_handler: dict, event: dict):
        if event["event_type"] in subscribed_events:
            return self._handle_event(event_handler, event)
        return self._emit_error_event("event not subscribed")


class DummyMode(ModeUtil):
    def __init__(self, service_address: str, port):
        self.__servie_address = "http://{}:{}".format(service_address, port)
        self.__connected_restpoints = {
            "chucky": RestHandler(self.__servie_address, "chucky")
        }
        self.__subscribed_events = ("error", "new_user")
        self.__event_handler = {
            "error": self._emit_error_event(id, "error"),
            "new_user": self.__on_chucky
        }

    def __on_chucky(self, event: dict):
        joke = self.__connected_restpoints["chucky"].call_endpoint()
        if joke.status_code is 200:
            return self._bot_event(joke.text)
        return self._emit_error_event(event["ID"], "bad Request")

    def get_bot_answer(self, event: dict):
        return self._check_event_type(self.__subscribed_events, self.__event_handler, event)


class QuestionsMode(ModeUtil):

    def __init__(self, service_address: str, port):
        self.__servie_address = "http://{}:{}".format(service_address, port)
        self.__connected_restpoints = {
            "theme_name": RestHandler(self.__servie_address, "getThemeByName"),
            "theme_id": RestHandler(self.__servie_address, "getThemeById"),
            "card_id": RestHandler(self.__servie_address, "getCardById"),
        }
        self.__subscribed_events = ("new_user", "error", "question", "user_disconnected")
        self.__event_handler = {
            "new_user": self.__on_new_user,
            "question": self.__on_question,
            "error": self._emit_error_event(id, "error"),
            "user_disconnected": self.__on_user_disconnected
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

    def __get_card_by_id(self, card_id: str, department_id=None):
        request = self.__connected_restpoints["card_id"].call_endpoint({"CardId": card_id, "Department Id": department_id})
        if request.status_code is 200:
            return request.json()
        print("Request Error")

    def __create_new_user(self, id, load: dict):
        theme_request = self.__get_theme_by_name("rootTheme")
        new_user = {"id": id,
                    "name": load["username"],
                    "currentNodeId": theme_request["id"],
                    "currentNode": theme_request["name"],
                    "ParentThemeId": theme_request["id"],
                    "sub_themes": self.__convert_sub_nodes(theme_request["subThemes"]),
                    "cards": self.__convert_sub_nodes(theme_request["cards"]),
                    "options": None,
                    "numeration_to_sub_node": {},
                    "sub_node_to_numeration": {}
                    }
        if "departmentId" in load:
            new_user["department_id"] = load["departmentId"]
        return new_user

    def __load_theme(self, id, theme_id):
        user = self._users[id]
        theme_request = self.__get_theme_by_id(theme_id)
        user["ParentThemeId"] = user["currentNodeId"]
        user["currentNodeId"] = theme_request["id"]
        user["currentNode"] = theme_request["name"]
        user["sub_themes"] = self.__convert_sub_nodes(theme_request["subThemes"])
        user["cards"] = self.__convert_sub_nodes(theme_request["cards"])
        user["numeration_to_sub_node"] = {}
        user["sub_node_to_numeration"] = {}

    def __load_card(self, id, card_id):
        user = self._users[id]
        if "department_id" in user:
            card = self.__get_card_by_id(card_id, int(user["department_id"]))
        else:
            card = self.__get_card_by_id(card_id)
        user["ParentThemeId"] = user["currentNodeId"]
        user["currentNodeId"] = card["id"]
        user["currentNode"] = card["name"]
        user["sub_themes"] = None
        user["cards"] = None
        user["options"] = {self.OPT_CARD_HELPED: 'Ja', self.OPT_CARD_NOT_HELPED: 'Nein'}
        user["numeration_to_sub_node"] = {}
        user["sub_node_to_numeration"] = {}
        return card

    def __convert_sub_nodes(self, sub_nodes: dict):
        tmp_sub_nodes = {}
        for sub_node in sub_nodes:
            tmp_sub_nodes[sub_node["id"]] = sub_node["name"]
        return tmp_sub_nodes

    def __show_curr_theme(self, id):
        user = self._users[id]
        result = user["currentNode"]
        return result

    def __show_sub_themes(self, id):
        return self.__show_sub_nodes(id, "sub_themes")

    def __show_cards(self, id):
        return self.__show_sub_nodes(id, "cards")

    def __show_options(self, id):
        return self.__show_sub_nodes(id, "options")

    def __show_sub_nodes(self, id, sub_node_type):
        result = ""
        user = self._users[id]
        tmp_sub_nodes = user[sub_node_type]
        if tmp_sub_nodes is not None:
            sub_node_to_numeration = user["sub_node_to_numeration"]
            for sub_node in tmp_sub_nodes.values():
                result += "{}. {}\n".format(sub_node_to_numeration[sub_node], sub_node)
        return result

    def __numerate_all_sub_nodes(self, id):
        self.__numerate_sub_nodes(id, "cards")
        self.__numerate_sub_nodes(id, "sub_themes")
        self.__numerate_sub_nodes(id, "options")

    def __numerate_sub_nodes(self, id, sub_node_type):
        user = self._users[id]
        tmp_sub_nodes = user[sub_node_type]
        if tmp_sub_nodes is not None:
            sub_node_to_numeration = user["sub_node_to_numeration"]
            numeration_to_sub_node = user["numeration_to_sub_node"]
            sub_node_count = len(numeration_to_sub_node)
            for sub_node in tmp_sub_nodes.values():
                sub_node_count += 1
                numeration_to_sub_node[sub_node_count] = sub_node
                sub_node_to_numeration[sub_node] = sub_node_count

    def __numeration_to_sub_node(self, id, num: str):
        user = self._users[id]
        numeration_to_sub_node = user["numeration_to_sub_node"]
        tmp_num = int(num)
        if (tmp_num > 0) and (tmp_num <= len(numeration_to_sub_node)):
            return numeration_to_sub_node[int(tmp_num)]
        return num

    def __get_sub_node_id(self, sub_node_name: str, sub_nodes: dict):
        if sub_nodes is not None:
            for key, value in sub_nodes.items():
                if value.lower() == sub_node_name.lower():
                    return key
        return None

    def __show_card(self, id, card):
        self.__numerate_all_sub_nodes(id)
        result = "{}:\n{}\n\n".format(card["name"], card["description"])
        result += "Ist diese Infromation hilfreich?\n"
        result += self.__show_options(id)
        return result

    def __ask_for_action(self, id):
        self.__numerate_all_sub_nodes(id)
        result = "Was möchten Sie über \"" + self.__show_curr_theme(id) + "\" wissen?\n"
        result += self.__show_cards(id)
        result += self.__show_sub_themes(id)
        return result

    def __ask_for_retry(self, id):
        user = self._users[id]
        user["numeration_to_sub_node"] = {}
        user["sub_node_to_numeration"] = {}
        user["options"] = {self.OPT_RETRY: 'Ja', self.OPT_NO_RETRY: 'Nein'}
        self.__numerate_all_sub_nodes(id)
        result = "Möchten Sie noch etwas wissen?\n"
        result += self.__show_options(id)
        return result

    def __show_contact(self, id):
        result = "Falls Sie Hilfe brauchen oder Feedback geben möchten wenden Sie sich bitte an die " \
                 "Abteilung Projektmanagment. \nAnsprechpartner: Thomas Mann \nTel.: 01234 666666 " \
                 "\nE-mail: Mann@projectX.com \n\n"
        result += self.__ask_for_retry(id)
        return result

    def __restart(self, id):
        root_theme_id = 1
        self.__load_theme(id, root_theme_id)
        return self._bot_event(id, self.__ask_for_action(id))

    def __say_thank_you(self, id):
        self._users[id]["options"] = {}
        return "Dankeschön, dass Sie Quickpot+- verwendet haben. \nViel Spaß mit Ihrem Projekt!"

    def __is_user_registerd(self, id):
        return self._users[id] is not None

    OPT_CARD_HELPED = "card_helped"
    OPT_CARD_NOT_HELPED = "card_not_helped"
    OPT_RETRY = "retry"
    OPT_NO_RETRY = "no_retry"

    def __handle_option(self, id, option):
        if self.OPT_CARD_HELPED == option:
            return self.__ask_for_retry(id)
        if self.OPT_CARD_NOT_HELPED == option:
            return self.__show_contact(id)
        if self.OPT_RETRY == option:
            return self.__restart(id)
        if self.OPT_NO_RETRY == option:
            return self.__say_thank_you(id)

    def __get_response(self, id, question: str):
        user = self._users[id]
        if question.isnumeric():
            question = self.__numeration_to_sub_node(id, question)
        sub_theme_id = self.__get_sub_node_id(question, user["sub_themes"])
        if sub_theme_id is not None:
            self.__load_theme(id, sub_theme_id)
            return self._bot_event(id, self.__ask_for_action(id))
        card_id = self.__get_sub_node_id(question, user["cards"])
        if card_id is not None:
            card = self.__load_card(id, card_id)
            return self._bot_event(id, self.__show_card(id, card))
        option = self.__get_sub_node_id(question, user["options"])
        if option is not None:
            return self._bot_event(id, self.__handle_option(id, option))
        return self._bot_event(id, "Entschuldigung, ich habe Sie nicht verstanden.")

    # Events
    def __on_question(self, event: dict):
        load = self.get_load(event)
        id = event["ID"]
        if self.__is_user_registerd(id):
            return self.__get_response(id, load["question"])
        return self._emit_error_event(id, "Benutzer: \"" + load["username"] + "\" ist nicht angemeldet!")

    def __on_new_user(self, event: dict):
        load = self.get_load(event)
        event_id = event["ID"]
        self._users[event_id] = self.__create_new_user(event_id, load)
        response_message = self.__ask_for_action(event["ID"])
        return self._bot_event(event_id, "Hallo {}, ich bin Quickpot+-\n{}".format(load["username"], response_message))

    def __on_user_disconnected(self, event: dict):
        if self.__is_user_registerd(event["ID"]):
            del self._users[event["ID"]]

    def get_load(self, event):
        return event["load"]

    def get_bot_answer(self, event: dict):
        return self._check_event_type(self.__subscribed_events, self.__event_handler, event)


class StatsMode:

    def __init__(self, service_address: str, port):
        self.servie_address = "http://{}:{}".format(service_address, port)
        self.connected_restpoints = {}

    def get_bot_answer(self, msg):
        return "Hello from Stats Mode"


class Qwickpot:

    def __init__(self, start_mode, config):
        self.__modes = {
            "d": DummyMode(config["config"]["address"], config["config"]["port"]),
            "q": QuestionsMode(config["config"]["address"], config["config"]["port"]),
            "s": StatsMode(config["config"]["address"], config["config"]["port"])
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
        return self.__modes[self.__active_mode].get_bot_answer(event)

    def trigger_bot(self, event: dict):
        if event["event_type"] == "change_mode":
            return self.__change_mode(event["load"])
        return self.__trigger_mode(event)
